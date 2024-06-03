package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.DetalleOrdenCompra;
import com.ERP.invOperativa.Services.DetalleOrdenCompraServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/detalleOrdenCompra")
public class DetalleOrdenCompraController extends BaseControllerImpl<DetalleOrdenCompra, DetalleOrdenCompraServiceImpl>{
}
