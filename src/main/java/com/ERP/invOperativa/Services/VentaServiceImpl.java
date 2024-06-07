package com.ERP.invOperativa.Services;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.DetalleVentaRepository;
import com.ERP.invOperativa.Repositories.VentaRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository) {
        super(baseRepository);
    }

    //Listar todas las ventas
    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    //Ver los detalles de las ventas
    @Override
    public Optional<Venta> findById(Long id) {
        return super.findById(id);
    }

    //Borrar venta
    @Override
    public Venta deleteVenta(Long id) {
        ventaRepository.deleteById(id);
        return null;
    }



}