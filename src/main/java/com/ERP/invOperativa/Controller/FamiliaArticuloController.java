package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Enum.Modelo;
import com.ERP.invOperativa.Services.FamilaArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FamiliaArticuloController {
    @Autowired
    private FamilaArticuloService service;

    @GetMapping("/familiaarticulo")
    public String listarfamilias(Model modelo){
        modelo.addAttribute("familias", service.ListarFamiliaArticulo());
        return "FamiliaArticulo";
    }

    @GetMapping("familiaarticulo/nuevo")
    public String formularioCrearFamilia(Model model) {
        FamiliaArticulo familiaArticulo = new FamiliaArticulo();
        model.addAttribute("familias", familiaArticulo);
        model.addAttribute("modelos", Modelo.values());
        return "crear_familia_articulo";
    }

    @PostMapping("/familias")
    public String saveFamiliaArticulo(@ModelAttribute("familias") FamiliaArticulo familiaArticulo){
        service.saveFamiliaArticulo(familiaArticulo);
        return "redirect:/familiaarticulo";
    }

    @GetMapping("/familiaarticulo/eliminar/{id}")
    public String eliminarFamiliaArticulo(@PathVariable Long id){
        service.deleteFamiliaArticulo(id);
        return "redirect:/familiaarticulo";
    }
}
