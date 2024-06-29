package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.FamiliaArticulo;

import java.util.List;

public interface FamilaArticuloService extends BaseService<FamiliaArticulo,Long>{

    public List<FamiliaArticulo> ListarFamiliaArticulo();

    public FamiliaArticulo saveFamiliaArticulo(FamiliaArticulo familiaArticulo);

    public FamiliaArticulo deleteFamiliaArticulo(Long id);

    FamiliaArticulo getFamiliaArticuloById(Long id);

    public FamiliaArticulo actualizarFamiliaArticulo(FamiliaArticulo familiaArticulo);
}
