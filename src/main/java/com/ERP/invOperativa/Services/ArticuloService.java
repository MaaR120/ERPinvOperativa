package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.Venta;

import java.util.List;

public interface ArticuloService extends BaseService<Articulo,Long> {
    List<Articulo> findAll();
    List<Articulo> ListarArticulos();

    Articulo saveArticulo(Articulo articulo);

    Articulo deleteArticulo(Long id);
}
