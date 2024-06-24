package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.DTO.DTOVenta;
import com.ERP.invOperativa.DTO.DTOVentaBACK;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Services.ArticuloService;
import com.ERP.invOperativa.Services.VentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



import java.util.List;
import java.util.Optional;
import java.util.Date;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "venta")
public class VentaController extends BaseControllerImpl<Venta, VentaServiceImpl>{

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

    @GetMapping("/crear")
    public String mostrarFormularioCrearVenta(Model model) {
        List<Articulo> articulos = articuloService.findAll();
        model.addAttribute("articulos", articulos);
        model.addAttribute("venta", new DTOVentaBACK());
        return "NuevaVenta";
    }

    @PostMapping("/add")
    public ModelAndView crearVenta(DTOVentaBACK dtoVenta) {
        try {
            service.crearVenta(dtoVenta);
            return new ModelAndView("redirect:/venta/listadoVentas"); // Redireccionar a la lista de ventas
        } catch (Exception e) {
            return new ModelAndView("error");
        }

    }



    @PostMapping("/addBack")
    public ResponseEntity<?> crearVentaBack(@RequestBody DTOVentaBACK dtoVenta){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.crearVenta(dtoVenta));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/filtro")
    public ResponseEntity<?> filtroVentaArtFecha(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaIni, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaFin, @RequestParam Long idArt){
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

    @GetMapping("/demanda")
    public ResponseEntity<?> obtenerHistoricoVentas(@RequestParam Long idArt,@RequestParam int fechaIni) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.obtenerDemandaArt(idArt,fechaIni));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
}


