package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.DTO.DTOVenta;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Services.ArticuloService;
import com.ERP.invOperativa.Services.VentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "venta")
public class VentaController {

    @Autowired
    private VentaServiceImpl service;

    @Autowired
    private ArticuloService articuloService;

    @GetMapping("/listadoVentas")
    public String listarVentas(Model model) {
        model.addAttribute("ventas", service.findAll());
        return "ListadoVentas";
    }



    @GetMapping("/detalleVenta/{id}")
    public String DetalleVenta(@PathVariable("id") Long id, Model model) {
        Optional<Venta> venta = service.findById(id);
        if (venta.isPresent()) {
            model.addAttribute("venta", venta.get());
            return "DetalleVenta";
        } else {
            return "redirect:/venta/listadoVentas";
        }
    }

    @GetMapping("/listadoVentas/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        service.deleteVenta(id);
        return "redirect:/venta/listadoVentas";
    }


}


