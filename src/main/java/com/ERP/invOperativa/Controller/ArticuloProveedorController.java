package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Services.ArticuloProveedorService;
import com.ERP.invOperativa.Services.ArticuloProveedorServiceImpl;
import com.ERP.invOperativa.Services.ArticuloService;
import com.ERP.invOperativa.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@Controller
//@CrossOrigin(origins = "*")
@RequestMapping("/articulo")
public class ArticuloProveedorController extends BaseControllerImpl<ArticuloProveedor, ArticuloProveedorServiceImpl>{

    @Autowired
    private ArticuloProveedorServiceImpl articuloProveedorService;

    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private OrdenCompraController ordenCompraController;


    @GetMapping("/{articuloId}/articuloProveedor/nuevo")
    public String mostrarFormularioNuevo(@PathVariable Long articuloId, Model model){
        Optional<Articulo> articuloOptional = articuloService.findById(articuloId);
        if (articuloOptional.isEmpty()) {
            return "error/404"; // Redirigir a una página de error o mostrar un mensaje adecuado
        }
        ArticuloProveedor articuloProveedor = new ArticuloProveedor();
        model.addAttribute("articuloProveedor", articuloProveedor);
        model.addAttribute("articulo", articuloOptional.get());
        model.addAttribute("proveedores", proveedorService.ListarProveedor());

        return "NuevoAP";
    }

    @PostMapping("/{articuloId}/articuloProveedor/guardar")
    public String saveAP(@PathVariable Long articuloId, @ModelAttribute ArticuloProveedor articuloProveedor, @RequestParam("proveedor.id") Long proveedorId) throws Exception {
        Optional<Articulo> articuloOptional = articuloService.findById(articuloId);
        Optional<Proveedor> proveedorOptional = proveedorService.findById(proveedorId);
        if (articuloOptional.isEmpty() || proveedorOptional.isEmpty()) {
            return "error/404"; // Redirigir a una página de error o mostrar un mensaje adecuado
        }
        articuloProveedor.setArticulo(articuloOptional.get());
        articuloProveedor.setProveedor(proveedorOptional.get());
        articuloProveedorService.save(articuloProveedor);

        return "redirect:/maestroarticulo";
    }




    @GetMapping("/{articuloId}/proveedor/nuevo")
    public String mostrarFormulario(@PathVariable Long articuloId, Model model){
        Optional<Articulo> articuloOptional = articuloService.findById(articuloId);
        if (articuloOptional.isEmpty()) {
            return "error/404"; // Redirigir a una página de error o mostrar un mensaje adecuado
        }

        Proveedor proveedor = new Proveedor();

        model.addAttribute("articulo", articuloOptional.get());
        model.addAttribute("proveedor", proveedor);

        return "NuevoProveedor";
    }

    @PostMapping("/{articuloId}/proveedor/guardar")
    public String saveArticuloProveedor(@PathVariable Long articuloId,
                                        @RequestParam String nombreProveedor,
                                        @RequestParam Integer costoPedido,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaVigencia,
                                        @RequestParam double precioArticuloProveedor,
                                        @RequestParam(defaultValue = "false") boolean predeterminado,
                                        @RequestParam int tiempoDemora) throws Exception {
        articuloProveedorService.guardarProveedorYRelacion(articuloId, nombreProveedor, costoPedido, fechaVigencia, precioArticuloProveedor, predeterminado, tiempoDemora);
        return "redirect:/maestroarticulo" ; //redirije a la lista de proveedores


    }

    //Metodo para mostrar formulario y editar articuloProveedor
    @GetMapping("/{articuloId}/proveedor/editar/{id}")
    public String formularioEditarProveedor(@PathVariable Long articuloId, @PathVariable Long id, Model model){
        Optional<ArticuloProveedor> articuloProveedorOptional = articuloProveedorService.findById(id);
        if(articuloProveedorOptional.isEmpty()){
            return "error/404";
        }
        model.addAttribute("articuloProveedor",articuloProveedorOptional.get());
        model.addAttribute("articuloId", articuloId);
        return "modificarArticuloProveedor";

    }

//    @GetMapping("/{articuloProveedorId}/predeterminado")
//    @ResponseBody
//    public ArticuloProveedor getDatosPredeterminados(@PathVariable("articuloProveedorId") Long articuloProveedorId) throws Exception {
//       Optional<ArticuloProveedor> articuloProveedorOptional = articuloProveedorService.findById(articuloProveedorId);
//        if (articuloProveedorOptional.isPresent()) {
//            return articuloProveedorService.getPredeterminado(articuloProveedorOptional.get());
//
//        } else throw new Exception("Error al buscar el articulo proveedor predeterminado");
//    }


    @PostMapping("/actualizar/{id}")
    //recordar que borre el path variable articuloId
    public String actualizarProveedorArticulo(@PathVariable Long id,
                                              @ModelAttribute("articuloProveedor") ArticuloProveedor articuloProveedor,
                                              BindingResult result, Model model) throws Exception {
//       if (result.hasErrors()) {
//            return "modificarArticuloProveedor";
//        }
//        try {
            //si tuviera que guardar otro dato, hacerlo

        articuloProveedorService.save(articuloProveedor);

//        } catch (Exception e) {
//           result.rejectValue(null, "error.articuloProveedor", e.getMessage());
//            return "modificarArticuloProveedor";
//        }
        return "redirect:/maestroarticulo";
    }


    @GetMapping("/{articuloId}/proveedor/eliminar/{id}")
    public String eliminarProveedorArticulo(@PathVariable Long articuloId,@PathVariable Long id) throws Exception {
        service.delete(id);
        return "redirect:/maestroarticulo";

    }
//    @GetMapping("/eliminar/{id}")
//    public String eliminarProveedor(@PathVariable Long id){
//        serviceImpl.deleteProveedor(id);
//        return "redirect:/proveedor/listado";
//    }


}
