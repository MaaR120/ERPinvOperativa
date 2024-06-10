package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTOPrediccion;
import com.ERP.invOperativa.DTO.RequestPrediccionDemanda;
import com.ERP.invOperativa.Repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PrediccionDemandaImpl implements PrediccionDemanda{
    @Autowired
    protected VentaRepository ventaRepository;
    @Override
    public List<DTOPrediccion> promedioMovil(RequestPrediccionDemanda requestPrediccionDemanda) throws Exception {

        requestPrediccionDemanda.getArticuloId();
    }
}
