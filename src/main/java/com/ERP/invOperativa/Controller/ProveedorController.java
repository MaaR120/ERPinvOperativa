package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Services.ProveedorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/proveedor")
public class ProveedorController extends BaseControllerImpl<Proveedor,ProveedorServiceImpl>{

}
