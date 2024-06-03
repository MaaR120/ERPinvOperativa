package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTODetalleVenta;
import com.ERP.invOperativa.DTO.DTOVenta;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService  {
    @Autowired
    protected VentaRepository ventaRepository;
    @Autowired
    protected ArticuloRepository articuloRepository;

    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository, VentaRepository ventaRepository) {
        super(baseRepository);
        this.ventaRepository=ventaRepository;
    }

@Override
    public Venta crearVenta(DTOVenta dtoVenta) throws Exception {
        try{
            Venta newVenta=new Venta();
            double total=0;
            newVenta.setFechaFacturacion(dtoVenta.getFechaFacturacion());
            for (DTODetalleVenta dtoDetalleVenta:dtoVenta.getDetalleVentas()){
                Optional<Articulo> articulo=articuloRepository.findById(dtoDetalleVenta.getIdArticulo());
                if(articulo.isPresent()) {
                    DetalleVenta detalleVenta = new DetalleVenta();
                    detalleVenta.setArticulo(articulo.get());
                    detalleVenta.setCantidad(dtoDetalleVenta.getCantidad());
                    detalleVenta.setSubtotal(articulo.get().getPrecio() * dtoDetalleVenta.getCantidad());
                    total = total + detalleVenta.getSubtotal();
                    newVenta.agregarDetalleVenta(detalleVenta);
                } else {
                    throw new Exception("Artículo no encontrado con id: " + dtoDetalleVenta.getIdArticulo());
                }
            }


            newVenta.setTotalVenta(total);
            ventaRepository.save(newVenta);
            return newVenta;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
