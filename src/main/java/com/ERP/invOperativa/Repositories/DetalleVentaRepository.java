package com.ERP.invOperativa.Repositories;

import com.ERP.invOperativa.Entities.DetalleVenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends BaseRepository<DetalleVenta,Long>{
//    @Query(value = "SELECT * FROM Detalle_Venta as dv WHERE dv.articulo = :idArt", nativeQuery = true)
    @Query(value = "SELECT * FROM Detalle_Venta WHERE articulo_id = :idArt", nativeQuery = true)
    List<DetalleVenta> findByArt(@Param("idArt") Long idArt);
}
