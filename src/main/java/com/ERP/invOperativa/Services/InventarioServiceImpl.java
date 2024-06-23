package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOInventario;
import com.ERP.invOperativa.DTO.StatisticsUtils;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.OrdenCompra;
import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import com.ERP.invOperativa.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService{
    @Autowired
    private ArticuloServiceImpl articuloService;

    @Autowired
    protected ArticuloRepository articuloRepository;

    @Autowired
    protected ArticuloProveedorRepository articuloProveedorRepository;
    @Autowired
    protected ProveedorRepository proveedorRepository;

    @Autowired
    protected DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    @Override
    public List<Articulo> obtenerArticulosFaltantes() {
        List<Articulo> articulosFaltantes = new ArrayList<>();
        List<Articulo> articulos = articuloRepository.findAll();

        for (Articulo articulo : articulos) {
            if (articulo.getStock() <= articulo.getStockSeguridad()) {
                articulosFaltantes.add(articulo);
            }
        }

        return articulosFaltantes;
    }


    @Override
    public List<Articulo> obtenerArticulosReponer() {
        List<Articulo> articulosReponer = new ArrayList<>();
        List<Articulo> articulos = articuloRepository.findAll();

        for (Articulo articulo : articulos) {
            // Verificar si el artículo tiene una orden de compra en estado "Preparacion"
            List<OrdenCompra> ordenesEnPreparacion = ordenCompraRepository.findByArticuloIdAndEstadoOrdenCompra(
                    articulo.getId(), EstadoOrdenCompra.Preparacion);

            // Si no hay órdenes de compra en estado "Preparacion", agregar el artículo a la lista
            if (articulo.getStock() <= articulo.getPuntoPedido() && ordenesEnPreparacion.isEmpty()) {
                articulosReponer.add(articulo);
            }
        }

        return articulosReponer;
    }

    @Override
    public List<DTOInventario> calcularLoteOptimo() {
        List<DTOInventario> loteOptimo = new ArrayList<>();
        List<Articulo> articulos = articuloRepository.findAll();

        for (Articulo articulo : articulos) {
            List<ArticuloProveedor> articuloProveedores = articulo.getArticuloProveedores();
            for (ArticuloProveedor articuloProveedor : articuloProveedores) {
                DTOInventario dtoInventario = new DTOInventario();
                dtoInventario.idArticulo = articulo.getId();
                dtoInventario.tiempoDemora = articuloProveedor.getTiempoDemora();
                dtoInventario.precioArticuloProveedor = articuloProveedor.getPrecioArticuloProveedor();
                List<DetalleVenta> detalles = detalleVentaRepository.findByArt(articulo.getId());
                List<Double> demandasHistoricas = obtenerCantidadesVendidas(detalles);

//                if (demandasHistoricas.isEmpty()) {
//                    continue; // Si no hay datos de ventas, saltar al siguiente artículo
//                }

                dtoInventario.demandaPromedio = StatisticsUtils.calcularMedia(demandasHistoricas);
                dtoInventario.desviacionDemanda = StatisticsUtils.calcularDesviacionEstandar(demandasHistoricas);


                if (articuloProveedor.getProveedor() != null && articuloProveedor.getProveedor().getCostoPedido() != null) {
                    dtoInventario.costoPedido = articuloProveedor.getProveedor().getCostoPedido();
                } else {
                    dtoInventario.costoPedido = 1000; // Valor por defecto si no hay costo de pedido definido
                }

                dtoInventario.costoAlmacenamiento = articulo.getCostoAlmacenamiento() * DTOInventario.INTERES_ALMACENAMIENTO;

                // Calcular Lote Óptimo (EOQ - Economic Order Quantity)
                double factorZ = 1.64; // Nivel de servicio del 95%

                double demandaAnual = articulo.getStock();
                double costoPedido = dtoInventario.costoPedido;
                double costoAlmacenamiento = (dtoInventario.INTERES_ALMACENAMIENTO * articuloProveedor.getPrecioArticuloProveedor());
                double tiempoDemora = dtoInventario.tiempoDemora;
                double costoCompra = (articuloProveedor.getPrecioArticuloProveedor()* demandaAnual);

                dtoInventario.loteOptimo = Math.sqrt((2 * demandaAnual * costoPedido) / costoAlmacenamiento);

                double lote = dtoInventario.loteOptimo;
                dtoInventario.puntoPedido = demandaAnual*tiempoDemora;
                dtoInventario.stockSeguridad = factorZ * dtoInventario.desviacionDemanda * Math.sqrt(dtoInventario.tiempoDemora);
                dtoInventario.CGI = costoCompra + costoAlmacenamiento * (lote/2) + costoPedido * (demandaAnual/lote) ;
                dtoInventario.stock = articulo.getStock();

                loteOptimo.add(dtoInventario);

                articulo.setPuntoPedido(dtoInventario.puntoPedido);
                articulo.setStockSeguridad(dtoInventario.stockSeguridad);

            }

        }

        articuloRepository.saveAll(articulos);
        return loteOptimo;
    }



    @Override
    public List<Double> obtenerCantidadesVendidas(List<DetalleVenta> detallesVenta) {
        List<Double> cantidadesVendidas = new ArrayList<>();
        for (DetalleVenta detalle : detallesVenta) {
            cantidadesVendidas.add((double) detalle.getCantidad());
        }
        return cantidadesVendidas;
    }

//    @Override
//    public List<DTOInventario> calcularStockSeguridad() {
//        List<DTOInventario> stockSeguridad = new ArrayList<>();
//        List<Articulo> articulos = articuloRepository.findAll();
//
//        for (Articulo articulo : articulos) {
//            List<ArticuloProveedor> proveedores = articulo.getArticuloProveedores();
//
//            if (proveedores == null || proveedores.isEmpty()) {
//                continue; // Si no hay proveedores, saltar al siguiente artículo
//            }
//
//            ArticuloProveedor articuloProveedor = proveedores.get(0);
//
//            DTOInventario dtoInventario = new DTOInventario();
//            dtoInventario.tiempoDemora = articuloProveedor.getTiempoDemora();
//            List<DetalleVenta> detalles = detalleVentaRepository.findByArt(articulo.getId());
//            List<Double> demandasHistoricas = obtenerCantidadesVendidas(detalles);
//
//            if (demandasHistoricas.isEmpty()) {
//                continue; // Si no hay datos de ventas, saltar al siguiente artículo
//            }
//
//            dtoInventario.demandaPromedio = StatisticsUtils.calcularMedia(demandasHistoricas);
//            dtoInventario.desviacionDemanda = StatisticsUtils.calcularDesviacionEstandar(demandasHistoricas);
//
//            double factorZ = 1.64; // Nivel de servicio del 95%
//
//            dtoInventario.stockSeguridad = factorZ * dtoInventario.desviacionDemanda * Math.sqrt(dtoInventario.tiempoDemora);
//
//            stockSeguridad.add(dtoInventario);
//        }
//
//        return stockSeguridad;
//    }



}
//@Override
//    public List<DTOInventario> calcularPuntoPedido() {
//        List<DTOInventario> inventarioCompleto  = new ArrayList<>();
//        List<Articulo> articulos = articuloRepository.findAll();
//
//        for (Articulo articulo : articulos) {
//            ArticuloProveedor articuloProveedor = articulo.getArticuloProveedores().get(0);
//
//            DTOInventario dtoInventario = new DTOInventario();
//            dtoInventario.tiempoDemora = articuloProveedor.getTiempoDemora();
//
//            //COSTO DE PEDIDO DONDE LO GUARDAMOS?
//
//
//            // Calcular Lote Óptimo (EOQ - Economic Order Quantity)
//
//            //VER QUE DEMANDA USO
//            double demandaAnual = articulo.getStock();
//            double tiempoDemora = dtoInventario.tiempoDemora;
//
//            dtoInventario.puntoPedido = demandaAnual*tiempoDemora ;
//
//            inventarioCompleto .add(dtoInventario);
//        }
//        return inventarioCompleto ;
//    }








//@Override
//public List<DTOInventario> calcularLoteOptimo() {
//    List<DTOInventario> loteOptimo = new ArrayList<>();
//    List<Articulo> articulos = articuloRepository.findAll();
//
//    for (Articulo articulo : articulos) {
//        List<ArticuloProveedor> proveedores = articulo.getArticuloProveedores();
//
//        for (ArticuloProveedor articuloProveedor : proveedores) {
//            DTOInventario dtoInventario = new DTOInventario();
//            dtoInventario.tiempoDemora = articuloProveedor.getTiempoDemora();
//            dtoInventario.precioArticuloProveedor = articuloProveedor.getPrecioArticuloProveedor();
//
//            if (articuloProveedor.getProveedor() != null && articuloProveedor.getProveedor().getCostoPedido() != null) {
//                dtoInventario.costoPedido = articuloProveedor.getProveedor().getCostoPedido();
//            } else {
//                dtoInventario.costoPedido = 20; // Valor por defecto si no hay costo de pedido definido
//            }
//
//            dtoInventario.costoAlmacenamiento = articulo.getCostoAlmacenamiento() * DTOInventario.INTERES_ALMACENAMIENTO;
//
//            // Calcular Lote Óptimo (EOQ - Economic Order Quantity)
//            double demandaAnual = articulo.getStock();
//            double costoPedido = dtoInventario.costoPedido;
//            double costoAlmacenamiento = (dtoInventario.INTERES_ALMACENAMIENTO * articuloProveedor.getPrecioArticuloProveedor());
//
//            dtoInventario.loteOptimo = Math.sqrt((2 * demandaAnual * costoPedido) / costoAlmacenamiento);
//
//            loteOptimo.add(dtoInventario);
//
//
//        }
//    }
//
//    return loteOptimo;
//}

