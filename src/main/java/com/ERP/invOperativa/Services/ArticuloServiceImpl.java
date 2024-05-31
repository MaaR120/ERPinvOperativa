package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticuloServiceImpl extends BaseServiceImpl<Articulo,Long> implements ArticuloService{
    @Autowired
    ArticuloRepository articuloRepository;
    public ArticuloServiceImpl(BaseRepository<Articulo, Long> baseRepository,ArticuloRepository articuloRepository) {
        super(baseRepository);
        this.articuloRepository=articuloRepository;
    }
}
