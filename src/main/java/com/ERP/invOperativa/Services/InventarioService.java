package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOInventario;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Entities.OrdenCompra;

import java.util.List;

public interface InventarioService {
    public List<DTOInventario> calcularLoteOptimo() throws Exception;


    //INVENTARIO

    public List<Double> obtenerCantidadesVendidas(List<DetalleVenta> detallesVenta);
    public double calcularPuntoPedidoParaArticuloYProveedor(Long articuloId, Long proveedorId) throws Exception;
    public double calcularLoteOptimoParaArticuloYProveedor(Long articuloId, Long proveedorId) throws Exception;
    public double calcularStockSeguridadParaArticuloYProveedor(Long articuloId, Long proveedorId) throws Exception;
    public double calcularCgiParaArticuloYProveedor(Long articuloId, Long proveedorId) throws Exception;



        List<Articulo> obtenerArticulosFaltantes();
    List<Articulo> obtenerArticulosReponer();
}
