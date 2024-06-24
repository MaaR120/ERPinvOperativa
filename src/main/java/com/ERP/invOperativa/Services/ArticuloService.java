package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Articulo;

import java.util.List;

public interface ArticuloService extends BaseService<Articulo,Long> {

    public List<Articulo> ListarArticulos();

    public Articulo saveArticulo(Articulo articulo);

    public Articulo deleteArticulo(Long id);

    List<Articulo> findAll();
    List<Articulo> listarArticulos();

    List<Articulo> listarArticuloReponer();

    List<Articulo> listarArticuloFaltantes();

}
