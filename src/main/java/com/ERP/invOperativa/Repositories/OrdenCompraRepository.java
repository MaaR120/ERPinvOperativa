package com.ERP.invOperativa.Repositories;
import com.ERP.invOperativa.Entities.OrdenCompra;
import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenCompraRepository extends BaseRepository<OrdenCompra,Long>{
    List<OrdenCompra> findByArticuloId(Long articuloId);
    List<OrdenCompra> findByArticuloIdAndEstadoOrdenCompra(Long articuloId, EstadoOrdenCompra estadoOrdenCompra);
}
