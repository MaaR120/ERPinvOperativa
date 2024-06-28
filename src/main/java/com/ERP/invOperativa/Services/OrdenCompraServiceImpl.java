package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.OrdenCompra;
import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenCompraServiceImpl extends BaseServiceImpl<OrdenCompra,Long> implements OrdenCompraService {
    @Autowired
    protected OrdenCompraRepository ordenCompraRepository;
    @Autowired
    protected InventarioServiceImpl inventarioService;


    public OrdenCompraServiceImpl(BaseRepository<OrdenCompra, Long> baseRepository, OrdenCompraRepository ordenCompraRepository) {
        super(baseRepository);
        this.ordenCompraRepository = ordenCompraRepository;
    }

    @Override
    public List<OrdenCompra> ListarOrdenes() {
        return ordenCompraRepository.findAll();
    }

    @Override
    public OrdenCompra saveOrdenCompra(OrdenCompra ordenCompra) {
        return ordenCompraRepository.save(ordenCompra);
    }

    @Override
    public OrdenCompra deleteOrdenCompra(Long id) {
        ordenCompraRepository.deleteById(id);
        return null;
    }

    @Override
    public boolean existeOrdenEnPreparacion(Long articuloId) {
        List<OrdenCompra> ordenes = ordenCompraRepository.findByArticuloIdAndEstadoOrdenCompra(articuloId, EstadoOrdenCompra.Preparacion);
        return !ordenes.isEmpty();
    }
    @Override
    public OrdenCompra saveOrdenAutomatica(ArticuloProveedor articuloProveedor) throws Exception {
        double loteOptimo = inventarioService.calcularLoteOptimoParaArticuloYProveedor(articuloProveedor.getArticulo().getId(), articuloProveedor.getProveedor().getId());
        double puntoPedido = inventarioService.calcularPuntoPedidoParaArticuloYProveedor(articuloProveedor.getArticulo().getId(), articuloProveedor.getProveedor().getId());
        int puntoPedidoEntero = (int)Math.round(puntoPedido);
        int loteOptimoEntero = (int)Math.round(loteOptimo);
        int NuevaCantidad = loteOptimoEntero + puntoPedidoEntero;
        //revisar que la cantidad este cargada, recordemos que hay datos en la bd que todavia no lo tienen
        double total = (articuloProveedor.getPrecioArticuloProveedor())*(NuevaCantidad);

        OrdenCompra ordenCompra = OrdenCompra.builder()
                .articulo(articuloProveedor.getArticulo())
                .proveedor(articuloProveedor.getProveedor())
                .cantidad(NuevaCantidad)
                .fechaInicio(new Date())
                .estadoOrdenCompra(EstadoOrdenCompra.Preparacion)
                .totalOrden(total)
                .build();
        return ordenCompraRepository.save(ordenCompra);
    }
}

//    @Override
//    public boolean actualizarStock(OrdenCompra ordenCompra){
//        Articulo articuloOrden = ordenCompra.getArticulo();
//        double cantidadOrden = ordenCompra.getCantidad();
//        public boolean stockActualizado = articuloService.actualizarStock(cantidadOrden, articuloOrden);
//        return stockActualizado;
//    }
//}
