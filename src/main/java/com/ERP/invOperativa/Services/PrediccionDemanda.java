package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOPrediccion;
import com.ERP.invOperativa.DTO.RequestPrediccionDemanda;

import java.util.List;

public interface PrediccionDemanda {
    public List<DTOPrediccion> promedioMovil(RequestPrediccionDemanda requestPrediccionDemanda) throws Exception;

    public List<DTOPrediccion> promedioMovilPonderado(RequestPrediccionDemanda requestPrediccionDemanda) throws Exception;

}
