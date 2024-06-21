package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl extends BaseServiceImpl<Proveedor,Long> implements ProveedorService{
    @Autowired
    protected ProveedorRepository proveedorRepository;
    public ProveedorServiceImpl(BaseRepository<Proveedor, Long> baseRepository,ProveedorRepository proveedorRepository) {
        super(baseRepository);
        this.proveedorRepository=proveedorRepository;
    }

    @Override
    public List<Proveedor> ListarProveedor() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor saveProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor deleteProveedor(Long id) {
        proveedorRepository.deleteById(id);
        return null;
    }

    @Override
    public Proveedor getProveedorById(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    public Proveedor actualizarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
}
