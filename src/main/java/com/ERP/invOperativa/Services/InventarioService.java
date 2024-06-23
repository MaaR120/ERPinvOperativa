package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOInventario;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Entities.OrdenCompra;

import java.util.List;

public interface InventarioService {
    public List<DTOInventario> calcularLoteOptimo();
    public List<DTOInventario> calcularStockSeguridad();
    public List<DTOInventario> calcularPuntoPedido();
    public List<Double> obtenerCantidadesVendidas(List<DetalleVenta> detallesVenta);
}
