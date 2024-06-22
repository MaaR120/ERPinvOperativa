package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Repositories.ArticuloProveedorRepository;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.ProveedorRepository;
import com.ERP.invOperativa.Services.ArticuloService;
import com.ERP.invOperativa.Services.FamilaArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ArticuloController {
    @Autowired
    private ArticuloService service;

    @Autowired
    private FamilaArticuloService familiaservice;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ArticuloProveedorRepository articuloProveedorRepository;


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
    public String deleteArticulo(@PathVariable Long id){
        service.deleteArticulo(id);
        return "redirect:/maestroarticulo";
    }
    /*
    @GetMapping("maestroarticulo/informacion_inventario/{id}")
    public String mostrarInventarioArticulo(@PathVariable Long id){

    }*/

    @GetMapping("maestroarticulo/{id}/articulo_proveedor")
    public String verProveedores(@PathVariable Long id, Model model) {
        Optional<Articulo> optionalArticulo = articuloRepository.findById(id);
        if (optionalArticulo.isPresent()) {
            Articulo articulo = optionalArticulo.get();
            List<Proveedor> proveedores = articulo.getArticuloProveedores().stream()
                    .map(ArticuloProveedor::getProveedor)
                    .collect(Collectors.toList());
            model.addAttribute("articulo", articulo);
            model.addAttribute("proveedores", proveedores);
            return "articulo_proveedor"; // Devuelve el nombre de la vista
        } else {
            // Manejar el caso en el que no se encuentra el artículo
            return "redirect:/maestroarticulo"; // Por ejemplo, redirigir a la página principal de los artículos
        }
    }

    @GetMapping("/maestroarticulo/reponer")
    public String listarArticulosAReponer(Model model) {
        List<Articulo> articuloReponer = service.listarArticuloReponer();
        model.addAttribute("articuloReponer", articuloReponer);
        return "Articulo_Reponer"; // Nombre de la vista correspondiente
    }

    @GetMapping("/maestroarticulo/faltantes")
    public String listarArticulosFaltantes(Model model) {
        List<Articulo> articuloFaltante = service.listarArticuloFaltantes();
        model.addAttribute("productosFaltantes", articuloFaltante);
        return "Articulo_Faltante"; // Nombre de la vista correspondiente
    }
}

