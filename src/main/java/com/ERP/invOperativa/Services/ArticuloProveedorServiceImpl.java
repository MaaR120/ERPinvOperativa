package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Repositories.ArticuloProveedorRepository;
import com.ERP.invOperativa.Repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloProveedorServiceImpl extends BaseServiceImpl<ArticuloProveedor,Long> implements ArticuloProveedorService{
    @Autowired
    protected ArticuloProveedorRepository articuloProveedorRepository;
    public ArticuloProveedorServiceImpl(BaseRepository<ArticuloProveedor, Long> baseRepository,ArticuloProveedorRepository articuloProveedorRepository) {
        super(baseRepository);
        this.articuloProveedorRepository=articuloProveedorRepository;
    }
    public List<Proveedor> getProveedoresPorArticulo(Articulo articulo) {
        return articuloProveedorRepository.findProveedoresByArticulo(articulo);
    }

    public ArticuloProveedor getPredeterminadoPorArticulo(Articulo articulo) {
        return articuloProveedorRepository.findPredeterminadoByArticulo(articulo);
    }
}
