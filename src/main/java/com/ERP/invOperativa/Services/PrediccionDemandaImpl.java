package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOPrediccion;
import com.ERP.invOperativa.DTO.RequestPrediccionDemanda;
import com.ERP.invOperativa.DTO.VentasPorMesDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PrediccionDemandaImpl implements PrediccionDemanda{
    @Autowired
    protected VentaServiceImpl ventaService;
    @Override
    public List<DTOPrediccion> promedioMovil(RequestPrediccionDemanda requestPrediccionDemanda) throws Exception {

        Long articuloId = requestPrediccionDemanda.getArticuloId();
        Date  fechaInicio= requestPrediccionDemanda.getFechaInicio();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);
        calendar.add(Calendar.MONTH, -3);
        Date fechaIni=calendar.getTime();

        List<VentasPorMesDTO> ventasPorMesDTOS=ventaService.obtenerVentasPorMes(fechaIni,fechaInicio,articuloId);


        return null;
    }
}
