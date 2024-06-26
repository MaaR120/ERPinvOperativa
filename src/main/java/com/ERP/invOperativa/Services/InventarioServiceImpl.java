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

            // Si no hay órdenes de compra en estado "Preparacion", agregar el artículo a la lista
            if (articulo.getStock() <= articulo.getPuntoPedido() && ordenesEnPreparacion.isEmpty()) {
                articulosReponer.add(articulo);
            }
        }

        return articulosReponer;
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
                    double lote = dtoInventario.loteOptimo;
                    dtoInventario.puntoPedido = demandaAnual * tiempoDemora;
                    dtoInventario.stockSeguridad = factorZ * dtoInventario.desviacionDemanda * Math.sqrt(dtoInventario.tiempoDemora);
                    dtoInventario.CGI = costoCompra + costoAlmacenamiento * (lote / 2) + costoPedido * (demandaAnual / lote);
                    dtoInventario.stock = articulo.getStock();
                    dtoInventario.proveedor = articuloProveedor.getProveedor().getNombreProveedor();

                    loteOptimo.add(dtoInventario);

                    articulo.setPuntoPedido(dtoInventario.puntoPedido);
                    articulo.setStockSeguridad(dtoInventario.stockSeguridad);
                }
            }

            articuloRepository.saveAll(articulos);
            return loteOptimo;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }


    @Override
    public List<Double> obtenerCantidadesVendidas(List<DetalleVenta> detallesVenta) {
        List<Double> cantidadesVendidas = new ArrayList<>();
        for (DetalleVenta detalle : detallesVenta) {
            cantidadesVendidas.add((double) detalle.getCantidad());
        }
        return cantidadesVendidas;
    }

}