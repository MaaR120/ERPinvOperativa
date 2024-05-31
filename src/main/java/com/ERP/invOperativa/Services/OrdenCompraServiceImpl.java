package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.OrdenCompra;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrdenCompraServiceImpl extends BaseServiceImpl<OrdenCompra,Long> implements OrdenCompraService {
    @Autowired
    protected OrdenCompraRepository ordenCompraRepository;
    public OrdenCompraServiceImpl(BaseRepository<OrdenCompra, Long> baseRepository,OrdenCompraRepository ordenCompraRepository) {
        super(baseRepository);
        this.ordenCompraRepository=ordenCompraRepository;
    }
}
