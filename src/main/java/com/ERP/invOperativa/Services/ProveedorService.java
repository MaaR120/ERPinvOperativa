package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Entities.Proveedor;

import java.util.List;

public interface ProveedorService  extends BaseService<Proveedor,Long>{

    public List<Proveedor> ListarProveedor();

    public Proveedor saveProveedor(Proveedor proveedor);

    public Proveedor deleteProveedor(Long id);

    Proveedor getProveedorById(Long id);

    public Proveedor actualizarProveedor(Proveedor proveedor);


}
