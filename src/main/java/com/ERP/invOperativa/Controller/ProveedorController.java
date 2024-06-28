package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.OrdenCompra;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Services.ProveedorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Optional;


@Controller
@RequestMapping(path = "proveedor")
public class ProveedorController extends BaseControllerImpl<Proveedor,ProveedorServiceImpl>{

    @Autowired
    private ProveedorServiceImpl serviceImpl;

    @GetMapping("/listado")
    public String ListarProveedor(Model modelo){
        modelo.addAttribute("proveedor", serviceImpl.ListarProveedor());
        return "ListaProveedores";
    }

    @GetMapping("/listado/nuevo")
    public String formularioCrearProveedor(Model model){
        Proveedor proveedor = new Proveedor();
        model.addAttribute("proveedor", proveedor);
        return "crear_proveedor";
    }


    @PostMapping("/guardarProv")
    public String saveProveedor(@ModelAttribute("proveedor") Proveedor proveedor){
        serviceImpl.saveProveedor(proveedor);
        return "redirect:/proveedor/listado";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id){
        serviceImpl.deleteProveedor(id);
        return "redirect:/proveedor/listado";
    }



    @GetMapping("/editar/{id}")
    public String formularioEditarProveedor(@PathVariable Long id, Model model){
        model.addAttribute("proveedor", serviceImpl.getProveedorById(id));
        return "modificarProveedor";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProveedor(@PathVariable Long id,
                                      @ModelAttribute("proveedor") Proveedor proveedor,
                                      Model model){
        Proveedor proveedorExistente = serviceImpl.getProveedorById(id);
        proveedorExistente.setId(id);
        proveedorExistente.setNombreProveedor(proveedor.getNombreProveedor());
        proveedorExistente.setCostoPedido(proveedor.getCostoPedido());
        serviceImpl.actualizarProveedor(proveedorExistente);
        return "redirect:/proveedor/listado";
    }
}
