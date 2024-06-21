package com.ERP.invOperativa.Repositories;
import com.ERP.invOperativa.DTO.DTOVentasFiltroArt;
import com.ERP.invOperativa.DTO.DTOVentasFiltroArtProjection;
import com.ERP.invOperativa.DTO.VentasPorMesDTO;
import com.ERP.invOperativa.DTO.VentasPorMesDTOProjection;
import com.ERP.invOperativa.Entities.Venta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VentaRepository extends BaseRepository<Venta,Long>{

    @Query(value = "SELECT v.fecha_Venta as fechaFacturacion, dv.cantidad, dv.articulo_id as idArt " +
            "FROM Venta v JOIN Detalle_Venta dv ON v.id = dv.venta_id " +
            "WHERE dv.articulo_id = :idArt " +
            "AND v.fecha_Venta BETWEEN :fechaIni AND :fechaFin " +
            "ORDER BY v.fecha_Venta",
            nativeQuery = true)
    List<DTOVentasFiltroArtProjection> filtroVentaArtFecha(@Param("fechaIni") Date fechaIni,
                                                           @Param("fechaFin") Date fechaFin,
                                                           @Param("idArt") Long idArt);


        @Query(value = "SELECT FORMATDATETIME(v.fecha_Venta, 'yyyy-MM') as mes, SUM(dv.cantidad) as cantidad " +
                "FROM Venta v " +
                "JOIN Detalle_Venta dv ON v.id = dv.venta_id " +
                "WHERE dv.articulo_id = :idArt " +
                "AND v.fecha_Venta BETWEEN :fechaIni AND :fechaFin " +
                "GROUP BY FORMATDATETIME(v.fecha_Venta, 'yyyy-MM') " +
                "ORDER BY FORMATDATETIME(v.fecha_Venta, 'yyyy-MM')",
                nativeQuery = true)
        List<VentasPorMesDTOProjection> obtenerVentasPorMes(@Param("fechaIni") Date fechaIni,
                                                            @Param("fechaFin") Date fechaFin,
                                                            @Param("idArt") Long idArt);


}
