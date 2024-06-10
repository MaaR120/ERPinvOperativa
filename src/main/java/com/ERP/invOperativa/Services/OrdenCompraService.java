package com.ERP.invOperativa.Services;


import com.ERP.invOperativa.Entities.OrdenCompra;

import java.util.List;

public interface OrdenCompraService extends BaseService<OrdenCompra,Long>{
    public List<OrdenCompra> ListarOrdenes();

    public OrdenCompra saveOrdenCompra(OrdenCompra ordenCompra);

    public OrdenCompra deleteOrdenCompra(Long id);

    public boolean existeOrdenEnPreparacion(Long articuloId);
}
