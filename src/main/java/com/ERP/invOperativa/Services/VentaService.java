package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOVenta;
import com.ERP.invOperativa.Entities.Venta;

public interface VentaService extends BaseService<Venta,Long>{

    public Venta crearVenta(DTOVenta dtoVenta) throws Exception;

}