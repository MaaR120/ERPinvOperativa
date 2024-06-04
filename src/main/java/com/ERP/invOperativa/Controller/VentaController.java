package com.ERP.invOperativa.Controller;
import java.util.Date;
import com.ERP.invOperativa.DTO.DTOVenta;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Services.ArticuloService;
import com.ERP.invOperativa.Services.VentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "venta")
public class VentaController extends BaseControllerImpl <Venta, VentaServiceImpl>{

    @Autowired
    private ArticuloService articuloService;

    //controlador de ver el listado de las ventas
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

    //controlador de ver el detalle
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


    //Controaldor de borrar venta en la lista
    @GetMapping("/listadoVentas/{id}")
    public String eliminarVenta(@PathVariable Long id){
        service.deleteVenta(id);
        return "redirect:/venta/listadoVentas";
    }

    //    @PostMapping("/guardarVenta")
//    public String guardarVenta(@ModelAttribute Venta venta) {
//        try {
//            service.save(venta);
//            return "redirect:/venta";
//        } catch (Exception e) {
//            return "error";
//        }
//    }

}



