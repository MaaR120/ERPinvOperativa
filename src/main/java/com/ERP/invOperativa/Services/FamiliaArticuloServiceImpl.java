package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.FamiliaArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class FamiliaArticuloServiceImpl extends BaseServiceImpl<FamiliaArticulo,Long> implements FamilaArticuloService {
    @Autowired
    protected FamiliaArticuloRepository familiaArticuloRepository;
    public FamiliaArticuloServiceImpl(BaseRepository<FamiliaArticulo, Long> baseRepository,FamiliaArticuloRepository familiaArticuloRepository) {
        super(baseRepository);
        this.familiaArticuloRepository=familiaArticuloRepository;

    }


    //AGUANTE MESSI
    
}
