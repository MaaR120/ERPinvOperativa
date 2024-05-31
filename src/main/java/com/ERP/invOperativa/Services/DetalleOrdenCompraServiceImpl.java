package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.DetalleOrdenCompra;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.DetalleOrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class DetalleOrdenCompraServiceImpl extends BaseServiceImpl<DetalleOrdenCompra,Long> implements DetalleOrdenCompraService {

    @Autowired
    protected DetalleOrdenCompraRepository detalleOrdenCompraRepository;
    public DetalleOrdenCompraServiceImpl(BaseRepository<DetalleOrdenCompra, Long> baseRepository, DetalleOrdenCompraRepository detalleOrdenCompraRepository) {
        super(baseRepository);
        this.detalleOrdenCompraRepository=detalleOrdenCompraRepository;
    }
}
