package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOInventario;
import com.ERP.invOperativa.DTO.StatisticsUtils;
import com.ERP.invOperativa.Entities.*;
import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import com.ERP.invOperativa.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ERP.invOperativa.Enum.Modelo;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService {
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
    protected VentaServiceImpl ventaService;

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

            if (!ordenesEnPreparacion.isEmpty()) {
                OrdenCompra ordenEnPreparacion = ordenesEnPreparacion.get(0); //aca se asume que es solo 1, en el caso de que queramos mas hay que cambiar y sumar todo
                int cantidadEnPreparacion = ordenEnPreparacion.getCantidad();

                double cuenta = articulo.getStock() + cantidadEnPreparacion;

                // Si no hay órdenes de compra en estado "Preparacion", agregar el artículo a la lista
                     if (articulo.getStock() <= articulo.getPuntoPedido() && cuenta <= articulo.getPuntoPedido()) {
                    articulosReponer.add(articulo);
                    }

                } else {
                    // Si no hay órdenes de compra en estado "Preparacion", agregar el artículo a la lista
                    if (articulo.getStock() <= articulo.getPuntoPedido()) {
                        articulosReponer.add(articulo);
                    }
                }
            }

        return articulosReponer;
    }

    @Override
    public double calcularLoteOptimoParaArticuloYProveedor(Long articuloId, Long proveedorId) throws Exception {
        ArticuloProveedor articuloProveedor = articuloProveedorRepository.findByArticuloYProveedor(articuloId, proveedorId);

        double demanda = ventaService.obtenerDemandaArt(articuloId, 2024);
        double costoPedido = articuloProveedor.getProveedor().getCostoPedido() != null ? articuloProveedor.getProveedor().getCostoPedido() : 1000;
        double costoAlmacenamiento = articuloProveedor.getArticulo().getCostoAlmacenamiento();
        double precioArticuloProveedor = articuloProveedor.getPrecioArticuloProveedor();

        double factorZ = 1.64; // Nivel de servicio del 95%
        double demandaAnual = demanda;
        double tiempoDemora = articuloProveedor.getTiempoDemora();
        double loteOptimo = Math.sqrt((2 * demandaAnual * costoPedido) / (costoAlmacenamiento * precioArticuloProveedor));

        return loteOptimo;
    }

    @Override
    public List<DTOInventario> calcularLoteOptimo() throws Exception {
        List<DTOInventario> loteOptimo = new ArrayList<>();
        List<Articulo> articulos = articuloRepository.findAll();
        try {
            for (Articulo articulo : articulos) {

                double demanda = ventaService.obtenerDemandaArt(articulo.getId(), 2024);
                List<ArticuloProveedor> articuloProveedores = articulo.getArticuloProveedores();

                for (ArticuloProveedor articuloProveedor : articuloProveedores) {
                    DTOInventario dtoInventario = new DTOInventario();
                    dtoInventario.idArticulo = articulo.getId();
                    dtoInventario.idProveedor = articuloProveedor.getProveedor().getId();
                    dtoInventario.tiempoDemora = articuloProveedor.getTiempoDemora();
                    dtoInventario.precioArticuloProveedor = articuloProveedor.getPrecioArticuloProveedor();
                    List<DetalleVenta> detalles = detalleVentaRepository.findByArt(articulo.getId());
                    List<Double> demandasHistoricas = obtenerCantidadesVendidas(detalles);

                    // Calculando la demanda promedio y desviación estándar
//                    dtoInventario.demandaPromedio = demanda;
                    dtoInventario.desviacionDemanda = StatisticsUtils.calcularDesviacionEstandar(demandasHistoricas);

                    // Configuración de costos y parámetros necesarios
                    if (articuloProveedor.getProveedor() != null && articuloProveedor.getProveedor().getCostoPedido() != null) {
                        dtoInventario.costoPedido = articuloProveedor.getProveedor().getCostoPedido();
                    } else {
                        dtoInventario.costoPedido = 1000; // Valor por defecto si no hay costo de pedido definido
                    }

                    dtoInventario.costoAlmacenamiento = articulo.getCostoAlmacenamiento() * DTOInventario.INTERES_ALMACENAMIENTO;

                    // Calcular Lote Óptimo (EOQ - Economic Order Quantity) según el modelo de familia y modelo
                    double factorZ = 1.64; // Nivel de servicio del 95%
                    double demandaAnual = demanda;
                    double costoPedido = dtoInventario.costoPedido;
                    double costoAlmacenamiento = (dtoInventario.INTERES_ALMACENAMIENTO * articuloProveedor.getPrecioArticuloProveedor());
                    double tiempoDemora = dtoInventario.tiempoDemora;
                    double costoCompra = (articuloProveedor.getPrecioArticuloProveedor() * demandaAnual);

                    FamiliaArticulo familia = articulo.getFamiliaArticulo();
                    Modelo modelo = familia.getModelo();

                    if (modelo != null) {
                        switch (modelo) {
                            case Lote_fijo:
                                // Calcular Lote Óptimo (EOQ - Economic Order Quantity) para Lote Fijo
                                dtoInventario.loteOptimo = Math.sqrt((2 * demandaAnual * costoPedido) / costoAlmacenamiento);
                                dtoInventario.stockSeguridad = (factorZ * dtoInventario.desviacionDemanda * Math.sqrt(tiempoDemora));
                                break;
                            case Lote_intervalo_fijo:
                                // Calcular para Lote Intervalo Fijo (ejemplo de cálculo)
                                double tiempoStock = 20;
                                double kDemandaProduccion = 1.5;
                                dtoInventario.loteOptimo = (2 * demandaAnual * costoPedido) / (costoAlmacenamiento * (1 - (demandaAnual / kDemandaProduccion)));
                                dtoInventario.stockSeguridad = (factorZ * dtoInventario.desviacionDemanda * Math.sqrt(dtoInventario.tiempoDemora + tiempoStock));
                                break;
                            default:
                                throw new IllegalArgumentException("Modelo no soportado: " + modelo);
                        }
                    }

                    // Calcular otros valores necesarios como el punto de pedido, stock de seguridad, etc.
                    double lote = Math.round(dtoInventario.loteOptimo);
                    dtoInventario.puntoPedido = Math.round(demandaAnual * tiempoDemora);
//                    dtoInventario.stockSeguridad = factorZ * dtoInventario.desviacionDemanda * Math.sqrt(dtoInventario.tiempoDemora);
                    dtoInventario.CGI = Math.round(costoCompra + costoAlmacenamiento * (lote / 2) + costoPedido * (demandaAnual / lote));
                    dtoInventario.stock = articulo.getStock();
                    dtoInventario.proveedor = articuloProveedor.getProveedor().getNombreProveedor();

                    loteOptimo.add(dtoInventario);

                    articulo.setLoteOptimo(Math.round(dtoInventario.loteOptimo));
                    articulo.setPuntoPedido(Math.round(dtoInventario.puntoPedido));
                    articulo.setStockSeguridad(Math.round(dtoInventario.stockSeguridad));
                }
            }

            articuloRepository.saveAll(articulos);
            return loteOptimo;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }

//    private double calcularDesviacionEstandar(List<Double> datos) {
//        if (datos.size() <= 1) {
//            return 0.0; // No hay suficiente datos para calcular desviación
//        }
//        double media = datos.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
//        double sumaDesviacionesCuadradas = datos.stream().mapToDouble(d -> Math.pow(d - media, 2)).sum();
//        return Math.sqrt(sumaDesviacionesCuadradas / (datos.size() - 1));
//    }
    @Override
    public List<Double> obtenerCantidadesVendidas(List<DetalleVenta> detallesVenta) {
        List<Double> cantidadesVendidas = new ArrayList<>();
        for (DetalleVenta detalle : detallesVenta) {
            cantidadesVendidas.add((double) detalle.getCantidad());
        }
        return cantidadesVendidas;
    }

}