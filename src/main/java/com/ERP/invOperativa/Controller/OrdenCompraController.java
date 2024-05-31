package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.OrdenCompra;
import com.ERP.invOperativa.Services.OrdenCompraServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/ordenCompra")
public class OrdenCompraController extends BaseControllerImpl<OrdenCompra, OrdenCompraServiceImpl>{
}
