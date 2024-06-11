package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Services.ArticuloService;
import com.ERP.invOperativa.Services.DetalleVentaService;
import com.ERP.invOperativa.Services.DetalleVentaServiceImpl;
import com.ERP.invOperativa.Services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "detalleVenta")
public class DetalleVentaController extends BaseControllerImpl<DetalleVenta, DetalleVentaServiceImpl>{

    @Autowired
    private DetalleVentaService detalleVentaService;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private ArticuloService articuloService;



}
