package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.DTO.DTOInventario;
import com.ERP.invOperativa.Services.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

//    @GetMapping("/articulos")
//    public List<DTOInventario> obtenerInventario() {
//        return inventarioService.calcularLoteOptimo();
//    }

//    @GetMapping("/inventario/{id}")
//    public String mostrarInventario(@PathVariable Long id, Model model) {
//        DTOInventario resultado = inventarioService.calcularLoteOptimoPorArticulo(id);
//
//        if (resultado != null) {
//            model.addAttribute("puntoPedido", resultado.getPuntoPedido());
//            model.addAttribute("stockSeguridad", resultado.getStockSeguridad());
//            model.addAttribute("loteOptimo", resultado.getLoteOptimo());
//            model.addAttribute("cgi", resultado.getCGI());
//        }
//
//        return "inventario";
//    }
}
