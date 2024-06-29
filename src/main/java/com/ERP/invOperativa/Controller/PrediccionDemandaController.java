package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.DTO.DTOVentaBACK;
import com.ERP.invOperativa.DTO.RequestPrediccionDemanda;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.Prediccion;
import com.ERP.invOperativa.Services.ArticuloService;
import com.ERP.invOperativa.Services.PrediccionDemandaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "prediccion")
public class PrediccionDemandaController {
    @Autowired
    protected PrediccionDemandaImpl service;

    @Autowired
    private ArticuloService articuloService;

    @GetMapping("/PM")
    public ResponseEntity<?> promedioMovil(@RequestBody RequestPrediccionDemanda requestPrediccionDemanda) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.promedioMovil(requestPrediccionDemanda));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/PMP")
    public ResponseEntity<?> promedioMovilPonderado(@RequestBody RequestPrediccionDemanda requestPrediccionDemanda) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.promedioMovilPonderado(requestPrediccionDemanda));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @PostMapping("/asignarPrediccion")
    public ResponseEntity<?> asignarPrediccion(@RequestBody RequestPrediccionDemanda requestPrediccionDemanda) {
        try {
            //Ver que mierda le llega
            System.out.println("Datos recibidos: " + requestPrediccionDemanda);

            return ResponseEntity.status(HttpStatus.OK).body(service.asignarPrediccion(requestPrediccionDemanda));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
//    @GetMapping("/ver/{articuloId}")
//    public String verPrediccion(@PathVariable Long articuloId, Model model) {
//       // Obtener el artículo con su predicción
//        Optional<Articulo> articuloOpt = articuloService.findById(articuloId);
//       if (articuloOpt.isPresent() && articuloOpt.get().getPrediccion() != null) {
//            model.addAttribute("prediccion", articuloOpt.get().getPrediccion());
//        } else {
//           model.addAttribute("prediccion", null);
//        }
//       return "verPrediccion";
//    }


    //    @GetMapping("/crear")
//    public String crearPrediccion() {
//        return "crearPrediccion";
//    }
//    @GetMapping("/{articuloId}/ver")
//    public String verPrediccion(@PathVariable Long articuloId, Model model) {
//        Articulo articulo = articuloService.findById(articuloId).orElse(null);
//        Prediccion prediccion = articulo != null ? articulo.getPrediccion() : null;
//        model.addAttribute("articulo", articulo);
//        model.addAttribute("prediccion", prediccion);
//        return "verPrediccion";
//    }

    @GetMapping("/crear")
    public String crearPrediccion(Model model) {
        model.addAttribute("requestPrediccionDemanda", new RequestPrediccionDemanda());
        return "crearPrediccion";
    }
}

