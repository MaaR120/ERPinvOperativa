package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Services.DetalleVentaServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "api/v1/detalleVenta")
public class DetalleVentaController extends BaseControllerImpl<DetalleVenta, DetalleVentaServiceImpl>{
}
