package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Services.DetalleVentaServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/detalleVenta")
public class DetalleVentaController extends BaseControllerImpl<DetalleVenta, DetalleVentaServiceImpl>{
}
