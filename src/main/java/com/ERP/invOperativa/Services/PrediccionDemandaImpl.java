package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOPrediccion;
import com.ERP.invOperativa.DTO.RequestPrediccionDemanda;
import com.ERP.invOperativa.DTO.VentasPorMesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
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
        try {
            List<VentasPorMesDTO> ventasPorMes=ventaService.obtenerVentasPorMes(fechaIni,fechaInicio,articuloId);
            List<DTOPrediccion> prediccionList=new ArrayList<>();
            for (VentasPorMesDTO ventasPorMesDTO:ventasPorMes){
                DTOPrediccion prediccion=new DTOPrediccion();
                prediccion.setMes(ventasPorMesDTO.getMes());
                prediccion.setCantidadReal(ventasPorMesDTO.getCantidad());
                prediccionList.add(prediccion);
            }
            DTOPrediccion prediccion=new DTOPrediccion();
            // Crear un SimpleDateFormat con el patrón 'yyyy-MM'
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            calendar.setTime(fechaInicio);
            calendar.add(Calendar.MONTH, +1);
            Date fechaPrediccion=calendar.getTime();
            prediccion.setMes(dateFormat.format(fechaPrediccion));
            int cantidadPrediccion=0;
            for (DTOPrediccion prediccion1:prediccionList){
                cantidadPrediccion+=prediccion1.getCantidadReal();
            }
            cantidadPrediccion=cantidadPrediccion/3;
            prediccion.setCantidadPrediccion(cantidadPrediccion);
            prediccionList.add(prediccion);
            return prediccionList;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }
}
