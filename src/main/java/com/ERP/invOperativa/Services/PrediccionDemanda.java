package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOPrediccion;
import com.ERP.invOperativa.DTO.RequestPrediccionDemanda;
import com.ERP.invOperativa.Entities.Prediccion;

import java.util.Date;
import java.util.List;

public interface PrediccionDemanda {
    public List<DTOPrediccion> promedioMovil(RequestPrediccionDemanda requestPrediccionDemanda) throws Exception;

    public List<DTOPrediccion> promedioMovilPonderado(RequestPrediccionDemanda requestPrediccionDemanda) throws Exception;

    public Prediccion update(Long id, Prediccion prediccion) throws Exception;

    public Prediccion asignarPrediccion(RequestPrediccionDemanda requestPrediccionDemanda) throws Exception;

}
