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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrdenCompraController extends BaseControllerImpl<OrdenCompra, OrdenCompraServiceImpl>{
    @Autowired
    private OrdenCompraService ordenCompraService;

    @Autowired
    private ArticuloServiceImpl articuloService;

    @Autowired
    private ArticuloProveedorServiceImpl articuloProveedorService;

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


//    METODO VIEJO, IGNORAR
//    @GetMapping("/ordenCompra/proveedoresPorArticulo/{articuloId}")
//    @ResponseBody
//    public List<Proveedor> getProveedoresPorArticulo(@PathVariable("articuloId") Long articuloId) throws Exception {
//        Articulo articulo = articuloService.findById(articuloId);
//        return articuloProveedorService.getProveedoresPorArticulo(articulo);
//    }

    @GetMapping("/ordenCompra/proveedoresPorArticulo/{articuloId}")
    @ResponseBody
    public List<Proveedor> getProveedoresPorArticulo(@PathVariable("articuloId") Long articuloId) {
        return articuloProveedorService.getProveedoresPorArticulo(articuloId);
    }


    @GetMapping("/ordenCompra/articulo/{articuloId}/predeterminado")
    @ResponseBody
    public ArticuloProveedor getDatosPredeterminados(@PathVariable("articuloId") Long articuloId) throws Exception {
        Articulo articulo = articuloService.findById(articuloId);
        return articuloProveedorService.getPredeterminadoPorArticulo(articulo);
    }

    @GetMapping("/ordenCompra/precioUnitario/{articuloId}/{proveedorId}")
    @ResponseBody
    public Double getPrecioPorArticuloProveedor(@PathVariable("articuloId") Long articuloId, @PathVariable("proveedorId") Long proveedorId) {
        return articuloProveedorService.getPrecioPorArticuloProveedor(articuloId, proveedorId);
    }


    @GetMapping("/ordenCompra/eliminar/{id}")
    public String eliminarOrdenCompra(@PathVariable long id){
        ordenCompraService.deleteOrdenCompra(id);
        return "redirect:/ordenCompra";
    }
}
