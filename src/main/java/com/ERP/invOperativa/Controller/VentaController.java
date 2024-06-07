package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.DTO.DTOVenta;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.DTO.VentasPorMesDTO;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Services.ArticuloService;
import com.ERP.invOperativa.Services.VentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

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

import java.awt.print.Pageable;
//import java.util.Date;


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


    //Agregar nueva venta
    @PostMapping("/add")
    public ResponseEntity<?> crearVenta(@RequestBody DTOVenta dtoVenta){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.crearVenta(dtoVenta));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


    @GetMapping("/filtro")
    public ResponseEntity<?> filtroVentaArtFecha(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaIni,@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaFin, @RequestParam Long idArt){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.filtroVentaArtFecha(fechaIni,fechaFin,idArt));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() +"\"}"));
        }
    }

    @GetMapping("/filtroMes")
    public ResponseEntity<?> obtenerHistoricoVentas(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaIni,@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaFin,@RequestParam Long idArt) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.obtenerVentasPorMes(fechaIni, fechaFin, idArt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

}


