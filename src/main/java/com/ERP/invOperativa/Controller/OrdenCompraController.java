package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.DTO.DTOInventario;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.OrdenCompra;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import com.ERP.invOperativa.Exceptions.NoProveedorPredeterminadoException;
import com.ERP.invOperativa.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller

public class OrdenCompraController extends BaseControllerImpl<OrdenCompra, OrdenCompraServiceImpl> {
    @Autowired
    private OrdenCompraService ordenCompraService;

    @Autowired
    private ArticuloServiceImpl articuloService;

    @Autowired
    private ArticuloProveedorServiceImpl articuloProveedorService;

    @Autowired
    private VentaServiceImpl ventaService;


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
    @GetMapping("/ordenCompra/form/{articuloId}")
    public String mostrarFormularioOrdenArticulo(@PathVariable Long articuloId, Model model) {
        Optional<Articulo> articuloOptional = articuloService.findById(articuloId);
        if(articuloOptional.isEmpty()){
            return "error/404";
        }

        model.addAttribute("ordenCompra", new OrdenCompra());
        model.addAttribute("articulo", articuloOptional.get());

        return "crear_ordenCompra_articulo";
    }

    @PostMapping("/ordenCompra/crear")
    public String crearOrdenCompra(@ModelAttribute("ordenCompra") OrdenCompra ordenCompra) {
        ordenCompra.setEstadoOrdenCompra(EstadoOrdenCompra.Preparacion);
        ordenCompraService.saveOrdenCompra(ordenCompra);
        return "redirect:/ordenCompra";
    }

    @PostMapping("/ordenCompra/crearAutomatica/{articuloId}")
    public String crearOrdenAutomatica(@PathVariable Long articuloId) throws  Exception {

        ArticuloProveedor articuloProveedor = getDatosPredeterminados(articuloId);

        ordenCompraService.saveOrdenAutomatica(articuloProveedor);

        return "redirect:/maestroarticulo";


    }
//    public ResponseEntity<String> crearOrdenAutomatica(@PathVariable Long articuloId) {
//        try {
//            ArticuloProveedor articuloProveedor = getDatosPredeterminados(articuloId);
//            if (articuloProveedor == null) {
//                throw new NoProveedorPredeterminadoException("No se encontró un proveedor predeterminado");
//            }
//            ordenCompraService.saveOrdenAutomatica(articuloProveedor);
//            return ResponseEntity.status(HttpStatus.OK).build();
//        } catch (NoProveedorPredeterminadoException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró un proveedor predeterminado");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la orden de compra");
//        }
//    }


    @GetMapping("/ordenCompra")
    public String listarOrdenes(Model modelo) {
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
            ordenCompra.setFechaUltimoCambio(new Date());
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
    public ArticuloProveedor getDatosPredeterminados(@PathVariable("articuloId") Long articuloId) throws Exception{
        Optional<Articulo> articulo = articuloService.findById(articuloId);
        if (articulo.isPresent()) {
            return articuloProveedorService.getPredeterminadoPorArticulo(articulo.get());

        } else throw new Exception("Error al buscar el articulo");
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
    public String eliminarOrdenCompra(@PathVariable long id) {
        ordenCompraService.deleteOrdenCompra(id);
        return "redirect:/ordenCompra";
    }

//    @PostMapping("/ordenCompra/actualizarStock/{ordenCompraId}")
//    public boolean actualizarStock(@PathVariable Long ordenCompraId) throws Exception{
//        Optional <OrdenCompra> ordenCompra = ordenCompraService.findById(ordenCompraId);
//        if(ordenCompra.isPresent()) {
//            double cantidadOrden = ordenCompra.get().getCantidad();
//            Articulo articuloOrden = ordenCompra.get().getArticulo();
//            boolean stockActualizado = articuloService.actualizarStock(cantidadOrden,articuloOrden);
//            }else return false;
//
//        }else throw new Exception("Error al buscar el articulo");


@PostMapping("/ordenCompra/actualizarStock/{ordenCompraId}")
public ResponseEntity<?> actualizarStock(@PathVariable("ordenCompraId") Long ordenCompraId, @RequestBody OrdenCompra ordenCompra) throws Exception {
    Optional<OrdenCompra> ordenCompraOptional = ordenCompraService.findById(ordenCompraId);
    if (ordenCompraOptional.isPresent()) {
        OrdenCompra existingOrdenCompra = ordenCompraOptional.get();
        if (ordenCompra.getEstadoOrdenCompra() == EstadoOrdenCompra.Entregado) {
            Articulo articulo = existingOrdenCompra.getArticulo();
            int nuevaCantidad = articulo.getStock() + existingOrdenCompra.getCantidad();
            articulo.setStock(nuevaCantidad);
            articuloService.save(articulo);
        }
        return ResponseEntity.ok().body("{\"success\": true}");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"success\": false, \"message\": \"Orden de compra no encontrada.\"}");
    }
}


    @GetMapping("ordenCompra/loteOptimo/{articuloId}/{proveedorId}")
    public ResponseEntity<Double> getLoteOptimo(@PathVariable Long articuloId, @PathVariable Long proveedorId) {
        try {
            double loteOptimo = calcularLoteOptimoPorProveedor(articuloId, proveedorId);
            return ResponseEntity.ok(loteOptimo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    private double calcularLoteOptimoPorProveedor(Long articuloId, Long proveedorId) throws Exception {
        Articulo articulo = articuloService.findById(articuloId).orElseThrow(() -> new Exception("Articulo no encontrado"));
        double demanda = ventaService.obtenerDemandaArt(articuloId, 2024);
//        double demanda = ventaService.obtenerDemandaArt(articuloId, 2024);
        ArticuloProveedor articuloProveedor = articulo.getArticuloProveedores().stream()
                .filter(ap -> ap.getProveedor().getId().equals(proveedorId))
                .findFirst()
                .orElseThrow(() -> new Exception("Proveedor no encontrado para este articulo"));

        double demandaAnual = demanda;
        double costoPedido = articuloProveedor.getProveedor().getCostoPedido() != null ? articuloProveedor.getProveedor().getCostoPedido() : 1000;


        double costoAlmacenamiento = (DTOInventario.INTERES_ALMACENAMIENTO * articuloProveedor.getPrecioArticuloProveedor());

        double loteOptimo = Math.sqrt((2 * demandaAnual * costoPedido) / costoAlmacenamiento);
        return loteOptimo;
    }



}

