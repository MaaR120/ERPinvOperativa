package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Repositories.ArticuloProveedorRepository;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticuloProveedorServiceImpl extends BaseServiceImpl<ArticuloProveedor,Long> implements ArticuloProveedorService{

   @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ArticuloService articuloService;
    @Autowired
    protected ArticuloProveedorRepository articuloProveedorRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public ArticuloProveedorServiceImpl(BaseRepository<ArticuloProveedor, Long> baseRepository,ArticuloProveedorRepository articuloProveedorRepository) {
        super(baseRepository);
        this.articuloProveedorRepository=articuloProveedorRepository;
    }


    public List<Proveedor> getProveedoresPorArticulo(Long articuloId) {


        List<ArticuloProveedor> articuloProveedores = articuloProveedorRepository.findByArticuloId(articuloId);
        return articuloProveedores.stream()
                .map(ArticuloProveedor::getProveedor)
                .collect(Collectors.toList());
    }

    public ArticuloProveedor getPredeterminadoPorArticulo(Articulo articulo) {
        return articuloProveedorRepository.findPredeterminadoByArticulo(articulo);
    }

    public double getPrecioPorArticuloProveedor(Long articuloId, Long proveedorId) {
        ArticuloProveedor articuloProveedor = articuloProveedorRepository.findByArticuloYProveedor(articuloId, proveedorId);
        if(articuloProveedor != null){
            return articuloProveedor.getPrecioArticuloProveedor();
        }else return 0.0;
//        return articuloProveedor != null ? articuloProveedor.getPrecioArticuloProveedor() : 0.0;
    }

    public void guardarProveedorYRelacion(Long articuloId, String nombreProveedor, Integer costoPedido, Date fechaVigencia,
                                          double precioArticuloProveedor, boolean predeterminado, int tiempoDemora) throws Exception {

        Articulo articulo = articuloService.findById(articuloId).orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));

        Proveedor proveedor = Proveedor.builder()
            .nombreProveedor(nombreProveedor)
                .costoPedido(costoPedido)
            .build();

       // proveedorService.save(proveedor); // Guardar el nuevo proveedor
        proveedorRepository.save(proveedor);

        ArticuloProveedor articuloProveedor = ArticuloProveedor.builder()
                .articulo(articulo)
                .proveedor(proveedor)
                .fechaVigencia(fechaVigencia)
                .precioArticuloProveedor(precioArticuloProveedor)
                .predeterminado(predeterminado)
                .tiempoDemora(tiempoDemora)
                .build();

        articuloProveedorRepository.save(articuloProveedor); // Guardar la relación
    }


}
