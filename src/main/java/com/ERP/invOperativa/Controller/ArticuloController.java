package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.DTO.DTOInventario;
import com.ERP.invOperativa.DTO.RequestPrediccionDemanda;
import com.ERP.invOperativa.Entities.*;
import com.ERP.invOperativa.Repositories.ArticuloProveedorRepository;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.ProveedorRepository;
import com.ERP.invOperativa.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ArticuloController{
    @Autowired
    private ArticuloServiceImpl service;
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

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ArticuloProveedorService articuloProveedorService;

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
    public String deleteArticulo(@PathVariable Long id) throws Exception {
        service.delete(id);
        return "redirect:/maestroarticulo";
    }

//Listado de articulos proveedores
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
            articulo.getId();
//        DTOInventario resultado = inventarioService.calcularLoteOptimoPorArticulo(id);

            try {
                // Calcular el inventario óptimo para todos los artículos
                List<DTOInventario> inventario = inventarioService.calcularLoteOptimo();

                // Filtrar el inventario para el artículo específico
                Optional<DTOInventario> inventarioArticulo = inventario.stream()
                        .filter(dtoInventario -> dtoInventario.idArticulo.equals(id))
                        .findFirst();

                if (inventarioArticulo.isPresent()) {
                    model.addAttribute("inventario", inventarioArticulo.get());
                } else {
                    // Si no se encuentra inventario para el artículo se agrega un DTOInventario vacío
                    DTOInventario dtoVacio = new DTOInventario();
                    dtoVacio.setIdArticulo(id);
                    dtoVacio.setPuntoPedido(10); // Valores predeterminados para prueba
                    dtoVacio.setStockSeguridad(0);
                    dtoVacio.setCGI(0);
                    model.addAttribute("inventario", dtoVacio);
                }

            } catch (Exception e) {
                // Manejar la excepción y agregar un mensaje de error al modelo
                model.addAttribute("error", "Error al calcular el inventario óptimo: " + e.getMessage());
            }

            return "informacion_inventario"; // Nombre de la vista HTML
        } else {
            // Manejar el caso en el que no se encuentra el artículo
            return "redirect:/maestroarticulo";
        }
    }


//    @GetMapping("/maestroarticulo/{id}/informacion_inventario")
//    public String verInventario(@PathVariable Long id, Model model) {
//        // Obtén el artículo por su ID
//        Optional<Articulo> optionalArticulo = articuloRepository.findById(id);
//        if (optionalArticulo.isPresent()) {
//            Articulo articulo = optionalArticulo.get();
//            List<DTOInventario> inventario = inventarioService.calcularLoteOptimo().stream()
//                    .filter(dto -> dto.idArticulo.equals(id))
//                    .collect(Collectors.toList());
//
//            if (!inventario.isEmpty()) {
//                model.addAttribute("inventario", inventario.get(0));
//                return "informacion_inventario"; // Nombre de la vista HTML
//            } else {
//                // Manejar el caso en el que no se encuentra el inventario
//                return "redirect:/maestroarticulo";
//            }
//        } else {
//            // Manejar el caso en el que no se encuentra el artículo
//            return "redirect:/maestroarticulo";
//        }
//    }

    @GetMapping("maestroarticulo/verPrediccion/{id}")
    public String verPrediccion(@PathVariable Long id, Model model) {
        // Busca el artículo por su ID
        Optional<Articulo> articuloOpt = service.findById(id);

        // Verifica si el artículo existe y si tiene una predicción asociada
        if (articuloOpt.isPresent()) {
            model.addAttribute("articulo", articuloOpt.get());
                model.addAttribute("prediccion", articuloOpt.get().getPrediccion());
            }
         else {
            // Si no encuentra el artículo o no tiene predicción, agrega valores nulos al modelo
            model.addAttribute("articulo", null);
            model.addAttribute("prediccion", null);
        }

        // Devuelve el nombre de la vista Thymeleaf que mostrará la información de la predicción
        return "verPrediccion";
    }

    @GetMapping("/{id}/crearPrediccion")
    public String crearPrediccion(@PathVariable Long id, Model model) {
        Optional<Articulo> articuloOpt = service.findById(id);

        if (articuloOpt.isPresent()) {
            model.addAttribute("articulo", articuloOpt.get());
            model.addAttribute("requestPrediccionDemanda", new RequestPrediccionDemanda());
            return "crearPrediccion";
        } else {
            return "redirect:/error"; // Manejo del caso donde el artículo no existe
        }
    }

}


