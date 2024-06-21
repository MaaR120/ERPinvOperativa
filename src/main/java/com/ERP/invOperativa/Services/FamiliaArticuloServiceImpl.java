package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.FamiliaArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamiliaArticuloServiceImpl extends BaseServiceImpl<FamiliaArticulo,Long> implements FamilaArticuloService {
    @Autowired
    protected FamiliaArticuloRepository familiaArticuloRepository;
    public FamiliaArticuloServiceImpl(BaseRepository<FamiliaArticulo, Long> baseRepository,FamiliaArticuloRepository familiaArticuloRepository) {
        super(baseRepository);
        this.familiaArticuloRepository=familiaArticuloRepository;

    }

    @Override
    public List<FamiliaArticulo> ListarFamiliaArticulo() {
        return familiaArticuloRepository.findAll();
    }

    @Override
    public FamiliaArticulo saveFamiliaArticulo(FamiliaArticulo familiaArticulo) {
        return familiaArticuloRepository.save(familiaArticulo);
    }

    public FamiliaArticulo getFamiliaArticuloById(Long id) {
        return familiaArticuloRepository.findById(id).orElse(null);
    }

    @Override
    public FamiliaArticulo actualizarFamiliaArticulo(FamiliaArticulo familiaArticulo) {
        return familiaArticuloRepository.save(familiaArticulo);
    }

    @Override
    public FamiliaArticulo deleteFamiliaArticulo(Long id) {
        familiaArticuloRepository.deleteById(id);
        return null;
    }


}
