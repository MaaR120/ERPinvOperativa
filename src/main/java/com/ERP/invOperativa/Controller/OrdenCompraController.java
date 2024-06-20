package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.OrdenCompra;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import com.ERP.invOperativa.Services.ArticuloProveedorServiceImpl;
import com.ERP.invOperativa.Services.ArticuloServiceImpl;
import com.ERP.invOperativa.Services.OrdenCompraService;
import com.ERP.invOperativa.Services.OrdenCompraServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class OrdenCompraController extends BaseControllerImpl<OrdenCompra, OrdenCompraServiceImpl>{
    @Autowired
    private OrdenCompraService ordenCompraService;

    @Autowired
    private ArticuloServiceImpl articuloService;

    @Autowired
    private ArticuloProveedorServiceImpl articuloProveedorService;

    @ModelAttribute("estadosOrdenCompra")
    public EstadoOrdenCompra[] estadosOrdenCompra() {
        return EstadoOrdenCompra.values();
    }

    @GetMapping("/ordenCompra/form")
    public String mostrarFormularioDeCreacion(Model model) {
        model.addAttribute("ordenCompra", new OrdenCompra());
        model.addAttribute("articulos", articuloService.ListarArticulos());
        return "crear_ordenCompra";
    }
    @PostMapping("/ordenCompra/crear")
    public String crearOrdenCompra(@ModelAttribute("ordenCompra") OrdenCompra ordenCompra) {
        ordenCompra.setEstadoOrdenCompra(EstadoOrdenCompra.Preparacion);
        ordenCompraService.saveOrdenCompra(ordenCompra);
        return "redirect:/ordenCompra";
    }
    @GetMapping("/ordenCompra")
    public String listarOrdenes(Model modelo){
        modelo.addAttribute("ordenes", ordenCompraService.ListarOrdenes());
        return "OrdenCompra";
    }

    //Metodo para mostrar el formulario de modificacion
    @GetMapping("/ordenCompra/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable("id") Long id, Model model) {
        Optional<OrdenCompra> ordenCompraOptional = ordenCompraService.findById(id);
        if (ordenCompraOptional.isPresent()) {
            model.addAttribute("ordenCompra", ordenCompraOptional.get());
        } else {
            model.addAttribute("errorMessage", "No se pudo encontrar la orden de compra con ID " + id);
            return "error";
        }
        return "modificarOrdenCompra";
    }

    @PostMapping("/ordenCompra/actualizar")
    public String actualizarOrdenCompra(@ModelAttribute OrdenCompra ordenCompra, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "modificarOrdenCompra";
        }
        try {
            ordenCompraService.save(ordenCompra);
        } catch (Exception e) {
            result.rejectValue(null, "error.ordenCompra", e.getMessage());
            return "modificarOrdenCompra";
        }
        return "redirect:/ordenCompra";
    }

    @GetMapping("/ordenCompra/proveedoresPorArticulo/{articuloId}")
    @ResponseBody
    public List<Proveedor> getProveedoresPorArticulo(@PathVariable("articuloId") Long articuloId) {
        return articuloProveedorService.getProveedoresPorArticulo(articuloId);
    }

    @GetMapping("/ordenCompra/articulo/{articuloId}/predeterminado")
    @ResponseBody
    public ArticuloProveedor getDatosPredeterminados(@PathVariable("articuloId") Long articuloId) throws Exception {
        Optional<Articulo> articulo = articuloService.findById(articuloId);
        if (articulo.isPresent()){
            return articuloProveedorService.getPredeterminadoPorArticulo(articulo.get());
        }
        else throw new Exception("Error al buscar el articulo");
    }

    @GetMapping("/ordenCompra/precioUnitario/{articuloId}/{proveedorId}")
    @ResponseBody
    public Double getPrecioPorArticuloProveedor(@PathVariable("articuloId") Long articuloId, @PathVariable("proveedorId") Long proveedorId) {
        return articuloProveedorService.getPrecioPorArticuloProveedor(articuloId, proveedorId);
    }

    @GetMapping("/ordenCompra/estado/{articuloId}")

    public ResponseEntity<String> obtenerEstadoOrdenCompra(@PathVariable Long articuloId) {
        boolean enPreparacion = ordenCompraService.existeOrdenEnPreparacion(articuloId);
        if (enPreparacion) {
            return ResponseEntity.ok("En preparación");
        } else {
            return ResponseEntity.ok("Disponible");
        }
    }
    @GetMapping("/ordenCompra/eliminar/{id}")
    public String eliminarOrdenCompra(@PathVariable long id){
        ordenCompraService.deleteOrdenCompra(id);
        return "redirect:/ordenCompra";
    }
}
