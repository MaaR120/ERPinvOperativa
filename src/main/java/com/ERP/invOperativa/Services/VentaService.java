package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOVenta;
import com.ERP.invOperativa.DTO.DTOVentasFiltroArt;
import com.ERP.invOperativa.DTO.VentasPorMesDTO;
import com.ERP.invOperativa.Entities.Venta;

import java.util.Date;
import java.util.List;

public interface VentaService extends BaseService<Venta,Long>{

    public Venta crearVenta(DTOVenta dtoVenta) throws Exception;

    public List<DTOVentasFiltroArt> filtroVentaArtFecha(Date fechaIni, Date fechaFin,Long idArt) throws Exception;
    public List<VentasPorMesDTO> obtenerVentasPorMes(Date fechaIni, Date fechaFin, Long idArt) throws Exception;

}
