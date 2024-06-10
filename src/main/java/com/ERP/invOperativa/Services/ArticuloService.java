package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Articulo;

import java.util.List;

public interface ArticuloService extends BaseService<Articulo,Long> {
    List<Articulo> findAll();
    public List<Articulo> ListarArticulos();

    public Articulo saveArticulo(Articulo articulo);

    Articulo deleteArticulo(Long id);
}
