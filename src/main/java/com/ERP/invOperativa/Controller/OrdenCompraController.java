package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.OrdenCompra;
import com.ERP.invOperativa.Services.OrdenCompraService;
import com.ERP.invOperativa.Services.OrdenCompraServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/ordenCompra")
public class OrdenCompraController extends BaseControllerImpl<OrdenCompra, OrdenCompraServiceImpl>{
    @Autowired
    private OrdenCompraService ordenCompraService;

    @GetMapping("/ordenCompra")
    public String listarOrdenes(Model modelo){
        modelo.addAttribute("ordenes", service.ListarOrdenes());
        return "OrdenCompra";
    }

    @GetMapping("/ordenCompra/crear")
    public String formularioCrearOrden(Model modelo){
        OrdenCompra ordenCompra = new OrdenCompra();
        modelo.addAttribute("orden", ordenCompra);
        return "crear_Orden";
    }

    @PostMapping("/ordenes")
    public String saveOrdenCompra(@ModelAttribute("orden") OrdenCompra ordenCompra){
        service.saveOrdenCompra(ordenCompra);
        return "redirect:/ordenCompra";
    }

    @GetMapping("/ordenCompra/{id}")
    public String eliminarOrdenCompra(@PathVariable long id){
        service.deleteOrdenCompra(id);
        return "redirect:/ordenCompra";
    }
}
