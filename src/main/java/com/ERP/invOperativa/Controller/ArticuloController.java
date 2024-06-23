package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.DTO.DTOInventario;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Repositories.ArticuloProveedorRepository;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.ProveedorRepository;
import com.ERP.invOperativa.Services.ArticuloService;
import com.ERP.invOperativa.Services.FamilaArticuloService;
import com.ERP.invOperativa.Services.InventarioService;
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
    private InventarioService inventarioService;
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

    @GetMapping("/faltantes")
    public String mostrarArticulosFaltantes(Model model) {
        List<Articulo> articulosFaltantes = inventarioService.obtenerArticulosFaltantes();
        model.addAttribute("articulosFaltantes", articulosFaltantes);
        return "Articulo_Faltante"; // Nombre de la vista HTML
    }

    @GetMapping("/reponer")
    public String mostrarArticulosReponer(Model model) {
        List<Articulo> articulosReponer = inventarioService.obtenerArticulosReponer();
        model.addAttribute("articulosReponer", articulosReponer);
        return "Articulo_Reponer"; // Nombre de la vista HTML
    }

    @GetMapping("/maestroarticulo/{id}/informacion_inventario")
    public String verInventario(@PathVariable Long id, Model model) {
        // Obtén el artículo por su ID
        Optional<Articulo> optionalArticulo = articuloRepository.findById(id);
        if (optionalArticulo.isPresent()) {
            Articulo articulo = optionalArticulo.get();
            List<DTOInventario> inventario = inventarioService.calcularLoteOptimo().stream()
                    .filter(dto -> dto.idArticulo.equals(id))
                    .collect(Collectors.toList());

            if (!inventario.isEmpty()) {
                model.addAttribute("inventario", inventario.get(0));
                return "informacion_inventario"; // Nombre de la vista HTML
            } else {
                // Manejar el caso en el que no se encuentra el inventario
                return "redirect:/maestroarticulo";
            }
        } else {
            // Manejar el caso en el que no se encuentra el artículo
            return "redirect:/maestroarticulo";
        }
    }
}

