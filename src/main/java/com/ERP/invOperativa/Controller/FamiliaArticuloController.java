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

    @GetMapping("/familiaarticulo/editar/{id}")
    public String formularioEditarFamilia(@PathVariable Long id, Model model){
        model.addAttribute("familias", service.getFamiliaArticuloById(id));
        model.addAttribute("modelos", Modelo.values());
        return "modificarFamiliaArticulo";
    }

    @PostMapping("/familiaarticulo/{id}")
    public String actualizarFamiliaArticulo(@PathVariable Long id,
                                            @ModelAttribute("familias") FamiliaArticulo familiaArticulo,
                                            Model model){
        FamiliaArticulo familiaArticuloExistente = service.getFamiliaArticuloById(id);
        familiaArticuloExistente.setId(id);
        familiaArticuloExistente.setNombreFamilia(familiaArticulo.getNombreFamilia());
        familiaArticuloExistente.setModelo(familiaArticulo.getModelo());
        service.actualizarFamiliaArticulo(familiaArticuloExistente);
        return "redirect:/familiaarticulo";
    }
}
