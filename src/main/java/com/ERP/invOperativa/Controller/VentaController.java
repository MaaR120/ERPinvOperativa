package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.DTO.DTOVenta;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Services.VentaService;
import com.ERP.invOperativa.Services.VentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "venta")
public class VentaController extends BaseControllerImpl <Venta, VentaServiceImpl>{


    @GetMapping("/listadoVentas")
    public String listarVentas(Model model) {
        model.addAttribute("ventas", service.findAll());
        return "ListadoVentas"; // Nombre de la plantilla HTML sin la extensión .html
    }


        @GetMapping("/nuevo")
    public String crearVenta(Model model) {
        model.addAttribute("venta", new DTOVenta());
        return "NuevaVenta";
    }

    @PostMapping("/nuevo")
    public String crearVenta(@ModelAttribute DTOVenta dtoVenta) {
        try {
            service.crearVenta(dtoVenta);
            return "redirect:/venta";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/detalleVenta/{id}")
    public String DetalleVenta(@PathVariable("id") Long id, Model model) {
        Optional<Venta> venta = service.findById(id);
        if (venta.isPresent()) {
            model.addAttribute("venta", venta.get());
            return "DetalleVenta"; // Asegúrate de que el nombre coincida con el archivo HTML
        } else {
            return "redirect:/venta/listadoVentas"; // O maneja el error de otra forma
        }
    }
    }
//
//    @GetMapping("/detalle/{id}")
//    public String detalleVenta(@PathVariable Long id, Model model) throws Exception {
//        Venta venta = service.findById(id).orElse(null);
//        model.addAttribute("venta", venta);
//        return "detalle";
//    }

//    @GetMapping("/eliminar/{id}")
//    public String eliminarVenta(@PathVariable Long id) throws Exception {
//        Venta venta = service.findById(id).orElse(null);
//        if (venta != null) {
//            service.delete(id);
//        }
//        return "redirect:/venta";
//    }
