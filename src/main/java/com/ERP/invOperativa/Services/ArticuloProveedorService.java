package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.ArticuloProveedor;

import java.util.Date;

public interface ArticuloProveedorService extends BaseService<ArticuloProveedor,Long>{

    public void guardarProveedorYRelacion(Long articuloId, String nombreProveedor, Date fechaVigencia,
                                          double precioArticuloProveedor, boolean predeterminado, int tiempoDemora) throws Exception;
}
