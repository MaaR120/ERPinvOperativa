package com.ERP.invOperativa.Repositories;


import com.ERP.invOperativa.Entities.ArticuloProveedor;

import java.util.List;

public interface ArticuloProveedorRepository extends BaseRepository<ArticuloProveedor, Long>{
        List<ArticuloProveedor> findByArticuloId(Long articuloId);
}
