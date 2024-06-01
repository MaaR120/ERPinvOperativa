package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.OrdenCompra;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenCompraServiceImpl extends BaseServiceImpl<OrdenCompra,Long> implements OrdenCompraService {
    @Autowired
    protected OrdenCompraRepository ordenCompraRepository;
    public OrdenCompraServiceImpl(BaseRepository<OrdenCompra, Long> baseRepository,OrdenCompraRepository ordenCompraRepository) {
        super(baseRepository);
        this.ordenCompraRepository=ordenCompraRepository;
    }
    @Override
    public List<OrdenCompra> ListarOrdenes() {
        return ordenCompraRepository.findAll();
    }

    @Override
    public OrdenCompra saveOrdenCompra(OrdenCompra ordenCompra) {
        return ordenCompraRepository.save(ordenCompra);
    }

    @Override
    public OrdenCompra deleteOrdenCompra(Long id) {
       ordenCompraRepository.deleteById(id);
        return null;
    }
}
