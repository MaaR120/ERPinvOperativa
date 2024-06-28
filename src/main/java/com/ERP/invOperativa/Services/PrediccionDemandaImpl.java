package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOPrediccion;
import com.ERP.invOperativa.DTO.RequestPrediccionDemanda;
import com.ERP.invOperativa.DTO.VentasPorMesDTO;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.Prediccion;
import com.ERP.invOperativa.Entities.PrediccionDetalle;
import com.ERP.invOperativa.Enum.MetodoPrediccion;
import com.ERP.invOperativa.Repositories.PrediccionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PrediccionDemandaImpl implements PrediccionDemanda{
    @Autowired
    protected VentaServiceImpl ventaService;
    @Autowired
    protected PrediccionRepository prediccionRepository;
    @Autowired
    protected ArticuloServiceImpl articuloService;

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
                    boolean bandera=false;
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
                boolean bandera1=false;

                for (DTOPrediccion dtoPrediccion:prediccionList){
                    if (Objects.equals(dtoPrediccion.getMes(), dateFormat.format(fechaCalculo))){
                        bandera1=true;
                        break;
                    }
                }
                if (bandera1){
                    for(int l=4;l>1;l--){
                        if (prediccionList.get(prediccionList.size() - l).getCantidadReal()>0) {
                            cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadReal();
                        } else{
                            cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadPrediccion();
                        }
                    }
                } else{
                    for(int l=3;l>0;l--){
                        if (prediccionList.get(prediccionList.size() - l).getCantidadReal()>0) {
                            cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadReal();
                        } else{
                            cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadPrediccion();
                        }
                    }
                }

                cantidadPrediccion=cantidadPrediccion/3;
                boolean bandera=false;
                for (DTOPrediccion prediccionComparacion:prediccionList){
                    if (Objects.equals(prediccionComparacion.getMes(), dateFormat.format(fechaCalculo))){
                        prediccionComparacion.setCantidadPrediccion(cantidadPrediccion);
                        prediccionComparacion.setError(Math.abs(prediccionComparacion.getCantidadReal()-cantidadPrediccion));
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

    @Override
    public List<DTOPrediccion> promedioMovilPonderado(RequestPrediccionDemanda requestPrediccionDemanda) throws Exception {
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
                    boolean bandera=false;
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




                double cantidadPrediccion=0;
                boolean bandera1=false;

                for (DTOPrediccion dtoPrediccion:prediccionList){
                    if (Objects.equals(dtoPrediccion.getMes(), dateFormat.format(fechaCalculo))){
                        bandera1=true;
                        break;
                    }
                }
                if (bandera1){
                    for(int l=4;l>1;l--){
                        if (l==4){
                            if (prediccionList.get(prediccionList.size() - l).getCantidadReal()>0) {
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadReal()*0.2;
                            } else{
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadPrediccion()*0.2;
                            }
                        } else if (l==3){
                            if (prediccionList.get(prediccionList.size() - l).getCantidadReal()>0) {
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadReal()*0.3;
                            } else{
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadPrediccion()*0.3;
                            }
                        } else{
                            if (prediccionList.get(prediccionList.size() - l).getCantidadReal()>0) {
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadReal()*0.5;
                            } else{
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadPrediccion()*0.5;
                            }
                        }

                    }
                } else{
                    for(int l=3;l>0;l--){
                        if (l==3){
                            if (prediccionList.get(prediccionList.size() - l).getCantidadReal()>0) {
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadReal()*0.2;
                            } else{
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadPrediccion()*0.2;
                            }
                        } else if (l==2) {
                            if (prediccionList.get(prediccionList.size() - l).getCantidadReal()>0) {
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadReal()*0.3;
                            } else{
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadPrediccion()*0.3;
                            }
                        } else {
                            if (prediccionList.get(prediccionList.size() - l).getCantidadReal()>0) {
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadReal()*0.5;
                            } else{
                                cantidadPrediccion += prediccionList.get(prediccionList.size() - l).getCantidadPrediccion()*0.5;
                            }
                        }

                    }
                }

                cantidadPrediccion=cantidadPrediccion/3.0;
                boolean bandera=false;
                for (DTOPrediccion prediccionComparacion:prediccionList){
                    if (Objects.equals(prediccionComparacion.getMes(), dateFormat.format(fechaCalculo))){
                        prediccionComparacion.setCantidadPrediccion((int) Math.round(cantidadPrediccion));
                        prediccionComparacion.setError(Math.abs(prediccionComparacion.getCantidadReal()-Math.round(cantidadPrediccion)));
                        bandera=true;
                    }
                    if (bandera){
                        break;
                    }
                }
                if (!bandera) {
                    DTOPrediccion prediccion = new DTOPrediccion();
                    prediccion.setMes(dateFormat.format(fechaCalculo));
                    prediccion.setCantidadPrediccion((int) Math.round(cantidadPrediccion));
                    prediccionList.add(prediccion);
                }

            } return prediccionList;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    @Transactional
    public Prediccion update(Long id, Prediccion prediccion) throws Exception {
        try{
            return prediccionRepository.save(prediccion);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Prediccion asignarPrediccion(RequestPrediccionDemanda requestPrediccionDemanda) throws Exception {
        try{
            Optional<Articulo> articulo=articuloService.findById(requestPrediccionDemanda.getArticuloId());


            List<DTOPrediccion> promedioMovilPonderado=promedioMovilPonderado(requestPrediccionDemanda);
            List<DTOPrediccion> promedioMovil=promedioMovil(requestPrediccionDemanda);

            double errorPM=0.0;
            double errorPMP=0.0;

            for (DTOPrediccion prediccionPM:promedioMovil){
                errorPM+=prediccionPM.getError();
            }
            for (DTOPrediccion prediccionPMP:promedioMovilPonderado){
                errorPMP+=prediccionPMP.getError();
            }
            Prediccion prediccion=new Prediccion();

            Date fechaActual = Calendar.getInstance().getTime();
            prediccion.setFechaUtilizacion(fechaActual);

            if (errorPMP<errorPM){
                prediccion.setMetodoPrediccion(MetodoPrediccion.PromedioPonderado);
                prediccion.setErrorDemanda(errorPMP);
                prediccion.asignarDetalle(promedioMovilPonderado);
            } else{
                prediccion.setMetodoPrediccion(MetodoPrediccion.PromedioMovil);
                prediccion.setErrorDemanda(errorPM);
                prediccion.asignarDetalle(promedioMovil);
            }
            if (articulo.isPresent()){
                if (articulo.get().getPrediccion()==null){

                    Articulo articulo1 = articulo.get();
                    articulo1.setPrediccion(prediccion);

                    articuloService.update(articulo.get().getId(), articulo1);
                } else {
                    prediccion.setId(articulo.get().getPrediccion().getId()); // Asegurar que la predicción tenga el mismo ID
                    this.update(prediccion.getId(), prediccion);
                }
            }

            return prediccion;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
