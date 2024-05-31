package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Repositories.ArticuloProveedorRepository;
import com.ERP.invOperativa.Repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticuloProveedorServiceImpl extends BaseServiceImpl<ArticuloProveedor,Long> implements ArticuloProveedorService{
    @Autowired
    ArticuloProveedorRepository articuloProveedorRepository;
    public ArticuloProveedorServiceImpl(BaseRepository<ArticuloProveedor, Long> baseRepository,ArticuloProveedorRepository articuloProveedorRepository) {
        super(baseRepository);
        this.articuloProveedorRepository=articuloProveedorRepository;
    }
}
