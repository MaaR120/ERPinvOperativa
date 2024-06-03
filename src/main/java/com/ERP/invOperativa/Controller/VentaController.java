package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.DTO.DTOVenta;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Services.VentaServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/venta")
public class VentaController extends BaseControllerImpl<Venta, VentaServiceImpl> {

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

}
