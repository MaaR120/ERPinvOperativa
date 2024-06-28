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

import static com.ERP.invOperativa.Enum.Modelo.Lote_fijo;

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

            boolean tieneProveedorPredeterminado = false;
            for (ArticuloProveedor articuloProveedor : articulo.getArticuloProveedores()) {
                if (articuloProveedor.isPredeterminado()) {
                    tieneProveedorPredeterminado = true;
                    break;
                }
            }

            if (!tieneProveedorPredeterminado) {
                continue; // Si no tiene proveedor predeterminado, pasar al siguiente artículo
            }

            List<OrdenCompra> ordenesEnPreparacion = ordenCompraRepository.findByArticuloIdAndEstadoOrdenCompra(
                    articulo.getId(), EstadoOrdenCompra.Preparacion);

            if (!ordenesEnPreparacion.isEmpty()) {

                int cantidadTotalEnPreparacion = ordenesEnPreparacion.stream()
                        .mapToInt(OrdenCompra::getCantidad)
                        .sum();

//                OrdenCompra ordenEnPreparacion = ordenesEnPreparacion.get(0); //aca se asume que es solo 1, en el caso de que queramos mas hay que cambiar y sumar
//                int cantidadEnPreparacion = ordenEnPreparacion.getCantidad();

                double cuenta = articulo.getStock() + cantidadTotalEnPreparacion;

                // Si no hay órdenes de compra en estado "Preparacion", agregar el artículo a la lista
                if (articulo.getStock() <= articulo.getStockSeguridad() && cuenta <= articulo.getStockSeguridad()) {
                    articulo.setCantidadPreparacion(cantidadTotalEnPreparacion);
                    articulosFaltantes.add(articulo);
                }

            } else {
                // Si no hay órdenes de compra en estado "Preparacion", agregar el artículo a la lista
                if (articulo.getStock() <= articulo.getStockSeguridad()) {
                    articulosFaltantes.add(articulo);
                    articulo.setCantidadPreparacion(0);
                }
            }
        }

        return articulosFaltantes;
    }


    @Override
    public List<Articulo> obtenerArticulosReponer() {
        List<Articulo> articulosReponer = new ArrayList<>();
        List<Articulo> articulos = articuloRepository.findAll();

        for (Articulo articulo : articulos) {

            boolean tieneProveedorPredeterminado = false;
            for (ArticuloProveedor articuloProveedor : articulo.getArticuloProveedores()) {
                if (articuloProveedor.isPredeterminado()) {
                    tieneProveedorPredeterminado = true;
                    break;
                }
            }

            if (!tieneProveedorPredeterminado) {
                continue; // Si no tiene proveedor predeterminado, pasar al siguiente artículo
            }

            // Verificar si el artículo tiene una orden de compra en estado "Preparacion"
            List<OrdenCompra> ordenesEnPreparacion = ordenCompraRepository.findByArticuloIdAndEstadoOrdenCompra(
                    articulo.getId(), EstadoOrdenCompra.Preparacion);

            if (!ordenesEnPreparacion.isEmpty()) {

                int cantidadTotalEnPreparacion = ordenesEnPreparacion.stream()
                        .mapToInt(OrdenCompra::getCantidad)
                        .sum();

//                OrdenCompra ordenEnPreparacion = ordenesEnPreparacion.get(0); //aca se asume que es solo 1, en el caso de que queramos mas hay que cambiar y sumar
//                int cantidadEnPreparacion = ordenEnPreparacion.getCantidad();

                double cuenta = articulo.getStock() + cantidadTotalEnPreparacion;

                // Si no hay órdenes de compra en estado "Preparacion", agregar el artículo a la lista
                     if (articulo.getStock() <= articulo.getPuntoPedido() && cuenta <= articulo.getPuntoPedido()) {
                         articulo.setCantidadPreparacion(cantidadTotalEnPreparacion);
                    articulosReponer.add(articulo);
                    }

                } else {
                    // Si no hay órdenes de compra en estado "Preparacion", agregar el artículo a la lista
                    if (articulo.getStock() <= articulo.getPuntoPedido()) {
                        articulosReponer.add(articulo);
                        articulo.setCantidadPreparacion(0);
                    }
                }
            }

        return articulosReponer;
    }

    @Override
    public double calcularLoteOptimoParaArticuloYProveedor(Long articuloId, Long proveedorId) throws Exception {
        ArticuloProveedor articuloProveedor = articuloProveedorRepository.findByArticuloYProveedor(articuloId, proveedorId);

        DTOInventario dtoInventario = new DTOInventario();
        double demanda = ventaService.obtenerDemandaArt(articuloId, 2024);
        double costoPedido = articuloProveedor.getProveedor().getCostoPedido() != null ? articuloProveedor.getProveedor().getCostoPedido() : 1000;
        double costoAlmacenamiento = articuloProveedor.getPrecioArticuloProveedor() * 0.20;
        double precioArticuloProveedor = articuloProveedor.getPrecioArticuloProveedor();

        double factorZ = 1.64; // Nivel de servicio del 95%
        double demandaAnual = demanda;
        double tiempoDemora = articuloProveedor.getTiempoDemora();
        FamiliaArticulo familia = articuloProveedor.getArticulo().getFamiliaArticulo();
        Modelo modelo = familia.getModelo();

        double loteOptimo = 0;

        if (modelo == Modelo.Lote_fijo) {
            loteOptimo = Math.sqrt((2 * demandaAnual * costoPedido) / costoAlmacenamiento);
        } else if (modelo == Modelo.Lote_intervalo_fijo) {
            loteOptimo = 0; // Asignar 0 cuando el modelo es de intervalo fijo
        }

        System.out.println(demandaAnual);
//        dtoInventario.setLoteOptimo(loteOptimo);
//        articuloProveedor.setLoteOptimo(loteOptimo);
//        System.out.println("Guardando lote óptimo: " + loteOptimo); // Log de depuración
//        articuloProveedorRepository.save(articuloProveedor);
        return loteOptimo;
    }

    @Override
    public double calcularPuntoPedidoParaArticuloYProveedor(Long articuloId, Long proveedorId) throws Exception {
        ArticuloProveedor articuloProveedor = articuloProveedorRepository.findByArticuloYProveedor(articuloId, proveedorId);

        double demanda = ventaService.obtenerDemandaArt(articuloId, 2024);
        double costoPedido = articuloProveedor.getProveedor().getCostoPedido() != null ? articuloProveedor.getProveedor().getCostoPedido() : 1000;
        double costoAlmacenamiento = articuloProveedor.getPrecioArticuloProveedor() * 0.20;
        double precioArticuloProveedor = articuloProveedor.getPrecioArticuloProveedor();

        double factorZ = 1.64; // Nivel de servicio del 95%
        double demandaAnual = demanda;
        double tiempoDemora = articuloProveedor.getTiempoDemora();
        double puntoPedido = Math.round(demandaAnual * tiempoDemora);
//        articuloProveedor.setPuntoPedidoA(puntoPedido);

//        articuloProveedor.setPuntoPedido(puntoPedido);
//        System.out.println("Guardando lote óptimo: " + puntoPedido); // Log de depuración
//        articuloProveedorRepository.save(articuloProveedor);
        return puntoPedido;
    }

    @Override
    public double calcularStockSeguridadParaArticuloYProveedor(Long articuloId, Long proveedorId) throws Exception {
        ArticuloProveedor articuloProveedor = articuloProveedorRepository.findByArticuloYProveedor(articuloId, proveedorId);

        double demanda = ventaService.obtenerDemandaArt(articuloId, 2024);
        double costoPedido = articuloProveedor.getProveedor().getCostoPedido() != null ? articuloProveedor.getProveedor().getCostoPedido() : 1000;
        double costoAlmacenamiento = articuloProveedor.getPrecioArticuloProveedor() * 0.20;
        double precioArticuloProveedor = articuloProveedor.getPrecioArticuloProveedor();
        double factorZ = 1.64; // Nivel de servicio del 95%
        double demandaAnual = demanda;
        double tiempoDemora = articuloProveedor.getTiempoDemora();
        List<DetalleVenta> detalles = detalleVentaRepository.findByArt(articuloId);
        List<Double> demandasHistoricas = obtenerCantidadesVendidas(detalles);
        double desviacionDemanda = StatisticsUtils.calcularDesviacionEstandar(demandasHistoricas);

        // Corrección en la obtención de la familia del artículo
        FamiliaArticulo familia = articuloProveedor.getArticulo().getFamiliaArticulo();
        Modelo modelo = familia.getModelo();

        double stockSeguridad = 0;

        if (modelo != null) {
            if (modelo == Modelo.Lote_fijo) { // Usar el valor correcto del enum Modelo
                stockSeguridad = factorZ * desviacionDemanda * Math.sqrt(tiempoDemora);
            } else {
                double tiempoStock = 20;
                stockSeguridad = factorZ * desviacionDemanda * Math.sqrt(tiempoDemora + tiempoStock);
            }
        }

        System.out.println(desviacionDemanda);
//        articuloProveedor.setStockSeguridadA(stockSeguridad);


//        articuloProveedor.setStockSeguridad(stockSeguridad);
//        System.out.println("Guardando lote óptimo: " + stockSeguridad); // Log de depuración
//        articuloProveedorRepository.save(articuloProveedor);

        return stockSeguridad;
    }

    @Override
    public double calcularCgiParaArticuloYProveedor(Long articuloId, Long proveedorId) throws Exception {
        ArticuloProveedor articuloProveedor = articuloProveedorRepository.findByArticuloYProveedor(articuloId, proveedorId);

        double demanda = ventaService.obtenerDemandaArt(articuloId, 2024);
        double costoPedido = articuloProveedor.getProveedor().getCostoPedido() != null ? articuloProveedor.getProveedor().getCostoPedido() : 1000;
        double costoAlmacenamiento = articuloProveedor.getPrecioArticuloProveedor() * 0.20;
        double demandaAnual = demanda;
        // Corrección en la obtención de la familia del artículo

        double lote = calcularLoteOptimoParaArticuloYProveedor(articuloId, proveedorId);

        double costoCompra = (articuloProveedor.getPrecioArticuloProveedor()) * lote;


        FamiliaArticulo familia = articuloProveedor.getArticulo().getFamiliaArticulo();
        Modelo modelo = familia.getModelo();

        double cgi = 0;
        if (modelo != null) {
            if (modelo == Modelo.Lote_fijo) { // Usar el valor correcto del enum Modelo
                cgi = Math.round(costoCompra + costoAlmacenamiento * (lote / 2) + costoPedido * (demandaAnual / lote));
            } else {
                double tiempoStock = 20;
                cgi = (0);
            }
        }
//        articuloProveedor.setCgiA(cgi);

//        articuloProveedor.setCgi(cgi);
//        System.out.println("Guardando lote óptimo: " + cgi); // Log de depuración
//        articuloProveedorRepository.save(articuloProveedor);
//        articuloProveedor.setCostoAlmacenamiento(costoAlmacenamiento);
//        articuloProveedorRepository.save(articuloProveedor);
        return cgi;
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

                    dtoInventario.costoAlmacenamiento = articuloProveedor.getPrecioArticuloProveedor() * DTOInventario.INTERES_ALMACENAMIENTO;

                    // Calcular Lote Óptimo (EOQ - Economic Order Quantity) según el modelo de familia y modelo
                    double factorZ = 1.64; // Nivel de servicio del 95%
                    double demandaAnual = demanda;
                    double costoPedido = articuloProveedor.getProveedor().getCostoPedido();
                    double costoAlmacenamiento = (dtoInventario.INTERES_ALMACENAMIENTO * articuloProveedor.getPrecioArticuloProveedor());
                    double tiempoDemora = dtoInventario.tiempoDemora;
                    double costoCompra = (articuloProveedor.getPrecioArticuloProveedor() * demandaAnual);

                    FamiliaArticulo familia = articulo.getFamiliaArticulo();
                    Modelo modelo = familia.getModelo();

                    if (modelo != null) {
                        switch (modelo) {
                            case Lote_fijo:
                                // Calcular Lote Óptimo (EOQ - Economic Order Quantity) para Lote Fijo
                                dtoInventario.loteOptimo = Math.sqrt((2 * demandaAnual * articuloProveedor.getProveedor().getCostoPedido()) / costoAlmacenamiento);
                                dtoInventario.stockSeguridad = (factorZ * dtoInventario.desviacionDemanda * Math.sqrt(articuloProveedor.getTiempoDemora()));
                                break;
                            case Lote_intervalo_fijo:
                                // Calcular para Lote Intervalo Fijo (ejemplo de cálculo)
                                double tiempoStock = 20;
//                                double kDemandaProduccion = 1.5;
                                dtoInventario.loteOptimo = (0);
//                              dtoInventario.loteOptimo = (2 * demandaAnual * costoPedido) / (costoAlmacenamiento * (1 - (demandaAnual / kDemandaProduccion)));
                                dtoInventario.stockSeguridad = (factorZ * StatisticsUtils.calcularDesviacionEstandar(demandasHistoricas) * Math.sqrt(dtoInventario.tiempoDemora + tiempoStock));
                                break;
                            default:
                                throw new IllegalArgumentException("Modelo no soportado: " + modelo);
                        }
                    }

                    // Calcular otros valores necesarios como el punto de pedido, stock de seguridad, etc.
                    double lote = Math.round(dtoInventario.loteOptimo);
                    System.out.println(lote);

                    dtoInventario.puntoPedido = Math.round(demandaAnual * tiempoDemora);
//                    dtoInventario.stockSeguridad = factorZ * dtoInventario.desviacionDemanda * Math.sqrt(dtoInventario.tiempoDemora);
                    dtoInventario.CGI = Math.round(costoCompra + costoAlmacenamiento * (lote / 2) + costoPedido * (demandaAnual / lote));
                    dtoInventario.stock = articulo.getStock();
                    dtoInventario.proveedor = articuloProveedor.getProveedor().getNombreProveedor();

                    dtoInventario.setPuntoPedido(dtoInventario.puntoPedido);
                    dtoInventario.setStockSeguridad(dtoInventario.stockSeguridad);
                    dtoInventario.setIdProveedor(dtoInventario.idProveedor);
                    dtoInventario.setProveedor(dtoInventario.proveedor);
                    if (lote >= 0){
                        articulo.setLoteOptimo(Math.round(lote));
                        dtoInventario.setLoteOptimo(lote);
                    }else {
                        articulo.setLoteOptimo(0);
                        dtoInventario.setLoteOptimo(0);
                    }
                    articulo.setPuntoPedido(Math.round(dtoInventario.puntoPedido));
                    articulo.setStockSeguridad(Math.round(dtoInventario.stockSeguridad));
                    loteOptimo.add(dtoInventario);

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