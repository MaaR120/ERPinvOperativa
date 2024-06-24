package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOVentaBACK;
import com.ERP.invOperativa.Entities.Venta;

import java.util.List;
import java.util.Optional;
import com.ERP.invOperativa.DTO.DTOVentasFiltroArt;
import com.ERP.invOperativa.DTO.VentasPorMesDTO;

import java.util.Date;

public interface VentaService extends BaseService<Venta,Long>{

    //Listar las ventas
    List<Venta> findAll();

    //Ver detalle venta
    Optional<Venta> findById(Long id);



    //Guardar borrar
    Venta deleteVenta(Long id);

    //Crear Venta
    public Venta crearVenta(DTOVentaBACK dtoVenta) throws Exception;

    //Filtros
    public List<DTOVentasFiltroArt> filtroVentaArtFecha(Date fechaIni, Date fechaFin,Long idArt) throws Exception;
    public List<VentasPorMesDTO> obtenerVentasPorMes(Date fechaIni, Date fechaFin, Long idArt) throws Exception;

    public double obtenerDemandaArt(Long idArt, int anio) throws Exception;
}
