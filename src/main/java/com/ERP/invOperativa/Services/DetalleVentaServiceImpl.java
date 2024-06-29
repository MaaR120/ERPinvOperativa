package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleVentaServiceImpl extends BaseServiceImpl<DetalleVenta,Long> implements DetalleVentaService{
    @Autowired
    protected DetalleVentaRepository detalleVentaRepository;
    public DetalleVentaServiceImpl(BaseRepository<DetalleVenta, Long> baseRepository, DetalleVentaRepository detalleVentaRepository) {
        super(baseRepository);
        this.detalleVentaRepository=detalleVentaRepository;
    }
}