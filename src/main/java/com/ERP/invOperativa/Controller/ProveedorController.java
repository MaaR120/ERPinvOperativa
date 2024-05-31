package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Services.ProveedorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;

@RestController
@RequestMapping(path = "api/v1/proveedor")
public class ProveedorController extends BaseControllerImpl<Proveedor,ProveedorServiceImpl>{

}
