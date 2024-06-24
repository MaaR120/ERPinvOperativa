package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.DTO.DTOVentaBACK;
import com.ERP.invOperativa.DTO.RequestPrediccionDemanda;
import com.ERP.invOperativa.Services.PrediccionDemandaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "prediccion")
public class PrediccionDemandaController {
    @Autowired
    protected PrediccionDemandaImpl service;
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
            return ResponseEntity.status(HttpStatus.OK).body(service.asignarPrediccion(requestPrediccionDemanda));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
}
