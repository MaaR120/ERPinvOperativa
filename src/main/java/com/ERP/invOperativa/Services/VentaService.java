package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOVenta;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Entities.Venta;

import java.util.List;
import java.util.Optional;

public interface VentaService extends BaseService<Venta,Long>{
    Venta crearVenta(DTOVenta dtoVenta) throws Exception;
    List<Venta> findAll();
    Optional<Venta> findById(Long id);
}
