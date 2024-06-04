package com.ERP.invOperativa.Services;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.DTO.DTOVenta;
import com.ERP.invOperativa.DTO.DTODetalleVenta;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository) {
        super(baseRepository);
    }
    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }
    @Override
    public Optional<Venta> findById(Long id) {
        return ventaRepository.findById(id);
    }

    @Override
    public Venta crearVenta(DTOVenta dtoVenta) throws Exception {
        try {
            Venta newVenta = new Venta();
            double total = 0;
            newVenta.setFechaFacturacion(dtoVenta.getFechaFacturacion());
            for (DTODetalleVenta dtoDetalleVenta : dtoVenta.getDetalleVentas()) {
                Optional<Articulo> articuloOptional = articuloRepository.findById(dtoDetalleVenta.getIdArticulo());
                if (articuloOptional.isPresent()) {
                    Articulo articulo = articuloOptional.get();
                    DetalleVenta detalleVenta = new DetalleVenta();
                    detalleVenta.setArticulo(articulo);
                    detalleVenta.setCantidad(dtoDetalleVenta.getCantidad());
                    detalleVenta.setSubtotal(articulo.getPrecio() * dtoDetalleVenta.getCantidad());
                    total += detalleVenta.getSubtotal();
                    newVenta.agregarDetalleVenta(detalleVenta);
                } else {
                    throw new Exception("Artículo no encontrado con id: " + dtoDetalleVenta.getIdArticulo());
                }
            }

            newVenta.setTotalVenta(total);
            ventaRepository.save(newVenta);
            return newVenta;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
