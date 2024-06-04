package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Services.ArticuloService;
import com.ERP.invOperativa.Services.FamilaArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticuloController {
    @Autowired
    private ArticuloService service;

    @Autowired
    private FamilaArticuloService familiaservice;

    @GetMapping("/maestroarticulo")
    public String listarArticulos(Model modelo) {
        modelo.addAttribute("articulos", service.ListarArticulos());
        return "MaestroArticulo"; // nos retorna al archivo html MaestroArticulo
    }

    @GetMapping("/maestroarticulo/nuevo")
    public String formularioCreararticulo(Model modelo){
        Articulo articulo = new Articulo();
        modelo.addAttribute("articulo", articulo);
        modelo.addAttribute("familias", familiaservice.ListarFamiliaArticulo());
        return "crear_articulo";
    }

    @PostMapping("/articulos")
    public String saveArticulo(@ModelAttribute("articulo") Articulo articulo, @RequestParam("familiaArticulo.id") Long familiaId){
        FamiliaArticulo familiaArticulo = familiaservice.getFamiliaArticuloById(familiaId);
        articulo.setFamiliaArticulo(familiaArticulo);
        service.saveArticulo(articulo);
        return "redirect:/maestroarticulo";
    }

    @GetMapping("/maestroarticulo/{id}")
    public String eliminarArticulo(@PathVariable Long id){
        service.deleteArticulo(id);
        return "redirect:/maestroarticulo";
    }
    /*
    @GetMapping("/informacion_inventario/{id}")
    public String mostrarInventarioArticulo(@PathVariable Long id){

    }*/

}