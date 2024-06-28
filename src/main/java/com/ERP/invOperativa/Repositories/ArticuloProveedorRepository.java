package com.ERP.invOperativa.Repositories;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.Proveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloProveedorRepository extends BaseRepository<ArticuloProveedor,Long>{


    List<ArticuloProveedor> findByArticuloId(Long articuloId);

    @Query("SELECT ap FROM ArticuloProveedor ap WHERE ap.articulo = :articulo AND ap.predeterminado = true")
    ArticuloProveedor findPredeterminadoByArticulo(@Param("articulo") Articulo articulo);

    @Query("SELECT ap FROM ArticuloProveedor ap WHERE ap.articulo.id =:articuloId AND ap.proveedor.id =:proveedorId")
    ArticuloProveedor findByArticuloYProveedor(@Param("articuloId") Long articuloId, @Param("proveedorId") Long proveedorId);


}
