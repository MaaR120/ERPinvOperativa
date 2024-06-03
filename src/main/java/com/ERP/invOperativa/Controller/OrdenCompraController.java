package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.OrdenCompra;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Services.ArticuloProveedorServiceImpl;
import com.ERP.invOperativa.Services.ArticuloServiceImpl;
import com.ERP.invOperativa.Services.OrdenCompraService;
import com.ERP.invOperativa.Services.OrdenCompraServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/ordenCompra")
public class OrdenCompraController extends BaseControllerImpl<OrdenCompra, OrdenCompraServiceImpl>{
    @Autowired
    private OrdenCompraService ordenCompraService;

    @Autowired
    private ArticuloServiceImpl articuloService;

    @Autowired
    private ArticuloProveedorServiceImpl articuloProveedorService;

    @GetMapping("/crear")
    public String mostrarFormularioDeCreacion(Model model) {
        model.addAttribute("ordenCompra", new OrdenCompra());
        model.addAttribute("articulos", articuloService.ListarArticulos());
        return "crear_orden_de_compra";
    }
    @GetMapping("/listar")
    public String listarOrdenes(Model modelo){
        modelo.addAttribute("ordenes", service.ListarOrdenes());
        return "OrdenCompra";
    }


    //REVISAR QUE FUNCIONE EL FIND BY ID
    @GetMapping("/proveedoresPorArticulo")
    @ResponseBody
    public List<Proveedor> getProveedoresPorArticulo(@RequestParam("articuloId") Long articuloId) throws Exception {
        Articulo articulo = articuloService.findById(articuloId);
        return articuloProveedorService.getProveedoresPorArticulo(articulo);
    }

    @GetMapping("/predeterminado")
    @ResponseBody
    public ArticuloProveedor getDatosPredeterminados(@RequestParam("articuloId") Long articuloId) throws Exception {
        Articulo articulo = articuloService.findById(articuloId);
        return articuloProveedorService.getPredeterminadoPorArticulo(articulo);
    }

    @PostMapping("/crearOrden")
    public String crearOrdenCompra(@ModelAttribute OrdenCompra ordenDeCompra) {
        ordenCompraService.saveOrdenCompra(ordenDeCompra);
        return "redirect:/ordenesdecompra";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarOrdenCompra(@PathVariable long id){
        service.deleteOrdenCompra(id);
        return "redirect:/ordenCompra";
    }
}
