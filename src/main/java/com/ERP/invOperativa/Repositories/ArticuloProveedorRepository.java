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
//    //Selecciono los proveedores de AP que esten asociado al articulo que le paso
//    @Query("SELECT ap.proveedor FROM ArticuloProveedor ap WHERE ap.articulo = :articulo")
//    List<Proveedor> findProveedoresByArticulo(@Param("articulo") Articulo articulo);

//Supuestamente funciona por correlatividada de nombres en JPA , veremos
    List<ArticuloProveedor> findByArticuloId(Long articuloId);

    @Query("SELECT ap FROM ArticuloProveedor ap WHERE ap.articulo = :articulo AND ap.predeterminado = true")
    ArticuloProveedor findPredeterminadoByArticulo(@Param("articulo") Articulo articulo);
}
