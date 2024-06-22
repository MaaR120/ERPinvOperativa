package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Services.ArticuloProveedorService;
import com.ERP.invOperativa.Services.ArticuloProveedorServiceImpl;
import com.ERP.invOperativa.Services.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
//@CrossOrigin(origins = "*")
@RequestMapping("/articulo/{articuloId}/proveedor")
public class ArticuloProveedorController extends BaseControllerImpl<ArticuloProveedor, ArticuloProveedorServiceImpl>{

    @Autowired
    private ArticuloProveedorService articuloProveedorService;

    @Autowired
    private ArticuloService articuloService;


    @GetMapping("/nuevo")
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

    @PostMapping("/guardar")
    public String saveArticuloProveedor(@PathVariable Long articuloId,
                                        @RequestParam String nombreProveedor,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaVigencia,
                                        @RequestParam double precioArticuloProveedor,
                                        @RequestParam(defaultValue = "false") boolean predeterminado,
                                        @RequestParam int tiempoDemora) throws Exception {
        articuloProveedorService.guardarProveedorYRelacion(articuloId, nombreProveedor, fechaVigencia, precioArticuloProveedor, predeterminado, tiempoDemora);
        return "redirect:/maestroarticulo" ; //redirije a la lista de proveedores


    }

}
