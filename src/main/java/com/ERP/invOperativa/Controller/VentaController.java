package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Services.VentaServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping(path = "api/v1/venta")
public class VentaController extends BaseControllerImpl<Venta, VentaServiceImpl> {

}
