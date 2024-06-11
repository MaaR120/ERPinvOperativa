package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloServiceImpl extends BaseServiceImpl<Articulo,Long> implements ArticuloService{

    @Autowired
    ArticuloRepository articuloRepository;
    public ArticuloServiceImpl(BaseRepository<Articulo, Long> baseRepository,ArticuloRepository articuloRepository) {
        super(baseRepository);
        this.articuloRepository=articuloRepository;
    }

    @Override
    public List<Articulo> ListarArticulos() {

        return articuloRepository.findAll();
    }

    @Override
    public Articulo saveArticulo(Articulo articulo) {

        return articuloRepository.save(articulo);
    }

    @Override
    public Articulo deleteArticulo(Long id) {
        articuloRepository.deleteById(id);
        return null;
    }

    @Override
    public List<Articulo> findAll() {
        return articuloRepository.findAll();


    }

    @Override
    public List<Articulo> listarArticulos() {
        return articuloRepository.findAll();
    }
}
