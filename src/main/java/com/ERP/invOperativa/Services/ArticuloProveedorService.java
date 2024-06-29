package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.Proveedor;

import java.util.Date;
import java.util.List;

public interface ArticuloProveedorService extends BaseService<ArticuloProveedor,Long>{

    public void guardarProveedorYRelacion(Long articuloId, String nombreProveedor, Integer costoPedido,Date fechaVigencia,
                                          double precioArticuloProveedor, boolean predeterminado, int tiempoDemora) throws Exception;


    public List<Proveedor> getProveedoresPorArticulo(Long articuloId);
}
