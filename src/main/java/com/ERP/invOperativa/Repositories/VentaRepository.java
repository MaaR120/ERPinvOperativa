package com.ERP.invOperativa.Repositories;
import com.ERP.invOperativa.DTO.DTOVentasFiltroArt;
import com.ERP.invOperativa.DTO.DTOVentasFiltroArtProjection;
import com.ERP.invOperativa.Entities.Venta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VentaRepository extends BaseRepository<Venta,Long>{

    @Query(value = "SELECT v.fecha_Venta as fechaFacturacion, dv.cantidad, dv.articulo as idArt " +
            "FROM Venta v JOIN Detalle_Venta dv ON v.id = dv.venta_id " +
            "WHERE dv.articulo = :idArt " +
            "AND v.fecha_Venta BETWEEN :fechaIni AND :fechaFin " +
            "ORDER BY v.fecha_Venta",
            nativeQuery = true)
    List<DTOVentasFiltroArtProjection> filtroVentaArtFecha(@Param("fechaIni") Date fechaIni,
                                                           @Param("fechaFin") Date fechaFin,
                                                           @Param("idArt") Long idArt);
}
