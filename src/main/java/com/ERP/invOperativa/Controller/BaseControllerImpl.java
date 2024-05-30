package com.ERP.invOperativa.Controller;


import com.ERP.invOperativa.Entities.Base;
import com.ERP.invOperativa.Services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseControllerImpl<E extends Base, S extends BaseServiceImpl<E, Long>> implements BaseController<E, Long> {
    @Autowired
    protected S servicio;

}
