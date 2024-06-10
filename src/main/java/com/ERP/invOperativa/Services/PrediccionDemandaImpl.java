package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOPrediccion;
import com.ERP.invOperativa.DTO.RequestPrediccionDemanda;
import com.ERP.invOperativa.DTO.VentasPorMesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PrediccionDemandaImpl implements PrediccionDemanda{
    @Autowired
    protected VentaServiceImpl ventaService;


    @Override
    public List<DTOPrediccion> promedioMovil(RequestPrediccionDemanda requestPrediccionDemanda) throws Exception {
        try{
            Long articuloId = requestPrediccionDemanda.getArticuloId();
            Date  fecha0= requestPrediccionDemanda.getFechaInicio();
            int corridas=requestPrediccionDemanda.getCantidadCorridas();

            // Crear un SimpleDateFormat con el patrón 'yyyy-MM'
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

            List<DTOPrediccion> prediccionList=new ArrayList<>();

            for(int i=1; i<=corridas; i++){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha0);
                calendar.add(Calendar.MONTH, i);
                Date fechaCalculo=calendar.getTime();

                calendar.add(Calendar.MONTH, -4);
                Date fecha3meses=calendar.getTime();

                List<VentasPorMesDTO> ventasPorMes;
                try {
                    ventasPorMes = ventaService.obtenerVentasPorMes(fecha3meses, fechaCalculo, articuloId);
                } catch (Exception e) {
                    // Manejar el error de obtenerVentasPorMes, por ejemplo, registrando el error y continuar con una lista vacía
                    System.err.println("Error al obtener ventas por mes: " + e.getMessage());
                    ventasPorMes = new ArrayList<>();
                }

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




                int cantidadPrediccion=0;



                for(int l=3;l>0;l--){
                    if (prediccionList.get(prediccionList.size() - l).getCantidadReal()>0) {
                        cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadReal();
                    } else{
                        cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadPrediccion();
                    }
                }
                cantidadPrediccion=cantidadPrediccion/3;
                boolean bandera=false;
                for (DTOPrediccion prediccionComparacion:prediccionList){
                    if (Objects.equals(prediccionComparacion.getMes(), dateFormat.format(fechaCalculo))){
                        prediccionComparacion.setCantidadPrediccion(cantidadPrediccion);
                        bandera=true;
                    }
                    if (bandera){
                        break;
                    }
                }
                if (!bandera) {
                    DTOPrediccion prediccion = new DTOPrediccion();
                    prediccion.setMes(dateFormat.format(fechaCalculo));
                    prediccion.setCantidadPrediccion(cantidadPrediccion);
                    prediccionList.add(prediccion);
                }

            } return prediccionList;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



}
