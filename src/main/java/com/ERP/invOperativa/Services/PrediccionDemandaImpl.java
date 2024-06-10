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

        //restar 3 meses a la fecha que pidio el chango
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

    @Override
    public List<DTOPrediccion> promedioMovilv2(RequestPrediccionDemanda requestPrediccionDemanda) throws Exception {
        try{
            Long articuloId = requestPrediccionDemanda.getArticuloId();
            Date  fecha0= requestPrediccionDemanda.getFechaInicio();
            int corridas=requestPrediccionDemanda.getCantidadCorridas();

            // Crear un SimpleDateFormat con el patrón 'yyyy-MM'
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

            List<DTOPrediccion> prediccionList=new ArrayList<>();

            for(int i=0; i<corridas; i++){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha0);
                calendar.add(Calendar.MONTH, i);
                Date fechaCalculo=calendar.getTime();

                calendar.add(Calendar.MONTH, -3);
                Date fecha3meses=calendar.getTime();

                List<VentasPorMesDTO> ventasPorMes=ventaService.obtenerVentasPorMes(fecha3meses,fechaCalculo,articuloId);

                for (int k=0;k<(ventasPorMes.size());k++){
                    Boolean bandera=false;
                    for (DTOPrediccion prediccionComparacion:prediccionList){
                        if(prediccionComparacion.getMes()==ventasPorMes.get(k).getMes()){
                            bandera=true;
                            break;
                        }

                    }
                    if (!bandera){
                        DTOPrediccion prediccion = new DTOPrediccion();
                        prediccion.setMes(ventasPorMes.get(k).getMes());
                        prediccion.setCantidadReal(ventasPorMes.get(k).getCantidad());
                        prediccionList.add(prediccion);
                    }
                }



                DTOPrediccion prediccion=new DTOPrediccion();
                int cantidadPrediccion=0;

                calendar.setTime(fechaCalculo);
                calendar.add(Calendar.MONTH, 1);
                Date fechaPrediccion=calendar.getTime();
                prediccion.setMes(dateFormat.format(fechaPrediccion));

                for(int l=3;l>0;l--){
                    if (prediccionList.get(prediccionList.size() - l).getCantidadReal()>0) {
                        cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadReal();
                    } else{
                        cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadPrediccion();
                    }
                }
                cantidadPrediccion=cantidadPrediccion/3;
                prediccion.setCantidadPrediccion(cantidadPrediccion);
                prediccionList.add(prediccion);

            } return prediccionList;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }




//        try {
//            List<VentasPorMesDTO> ventasPorMes=ventaService.obtenerVentasPorMes(fechaIni,fechaInicio,articuloId);
//            List<DTOPrediccion> prediccionList=new ArrayList<>();
//            for (VentasPorMesDTO ventasPorMesDTO:ventasPorMes){
//                DTOPrediccion prediccion=new DTOPrediccion();
//                prediccion.setMes(ventasPorMesDTO.getMes());
//                prediccion.setCantidadReal(ventasPorMesDTO.getCantidad());
//                prediccionList.add(prediccion);
//            }
//            DTOPrediccion prediccion=new DTOPrediccion();
//
//            // Crear un SimpleDateFormat con el patrón 'yyyy-MM'
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
//            calendar.setTime(fechaInicio);
//            calendar.add(Calendar.MONTH, +1);
//            Date fechaPrediccion=calendar.getTime();
//
//            prediccion.setMes(dateFormat.format(fechaPrediccion));
//            int cantidadPrediccion=0;
//            for (DTOPrediccion prediccion1:prediccionList){
//                cantidadPrediccion+=prediccion1.getCantidadReal();
//            }
//            cantidadPrediccion=cantidadPrediccion/3;
//            prediccion.setCantidadPrediccion(cantidadPrediccion);
//            prediccionList.add(prediccion);
//            return prediccionList;
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }



}
