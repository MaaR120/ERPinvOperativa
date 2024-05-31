package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Services.ArticuloProveedorServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/articuloProveedor")
public class ArticuloProveedorController extends BaseControllerImpl<ArticuloProveedor, ArticuloProveedorServiceImpl>{
}
