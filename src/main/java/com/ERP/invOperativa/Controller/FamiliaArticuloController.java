package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Services.FamiliaArticuloServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/familiaArticulo")
public class FamiliaArticuloController extends BaseControllerImpl<FamiliaArticulo, FamiliaArticuloServiceImpl>{
}
