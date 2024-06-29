package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProveedorServiceImpl extends BaseServiceImpl<Proveedor,Long> implements ProveedorService{
    @Autowired
    protected ProveedorRepository proveedorRepository;
    public ProveedorServiceImpl(BaseRepository<Proveedor, Long> baseRepository,ProveedorRepository proveedorRepository) {
        super(baseRepository);
        this.proveedorRepository=proveedorRepository;
    }
}
