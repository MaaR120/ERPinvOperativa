package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Services.ArticuloProveedorServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "articuloProveedor")
public class ArticuloProveedorController extends BaseControllerImpl<ArticuloProveedor, ArticuloProveedorServiceImpl>{
}
