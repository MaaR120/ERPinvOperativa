package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.*;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.DetalleVentaRepository;
import com.ERP.invOperativa.Repositories.VentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository, VentaRepository ventaRepository) {
        super(baseRepository);
        this.ventaRepository=ventaRepository;
    }


    //Listar todas las ventas
    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    //Ver los detalles de las ventas
    @Override
    public Optional<Venta> findById(Long id) {
        return super.findById(id);
    }

    //Borrar venta
    @Override
    public Venta deleteVenta(Long id) {
        ventaRepository.deleteById(id);
        return null;
    }

    @Override
    public Venta crearVenta(DTOVentaBACK dtoVenta) throws Exception {
        try {
            Venta newVenta = new Venta();
            double total = 0;
            newVenta.setFechaFacturacion(dtoVenta.getFechaFacturacion());

            // Lista para almacenar detalles de venta modificados
            List<DetalleVenta> detallesActualizados = new ArrayList<>();

            for (DTODetalleVentaBACK dtoDetalleVenta : dtoVenta.getDetalleVentas()) {
                Optional<Articulo> optionalArticulo = articuloRepository.findById(dtoDetalleVenta.getIdArticulo());
                if (optionalArticulo.isPresent()) {
                    Articulo articulo = optionalArticulo.get();
                    DetalleVenta detalleVenta = new DetalleVenta();
                    detalleVenta.setArticulo(articulo);
                    detalleVenta.setCantidad(dtoDetalleVenta.getCantidad());
                    detalleVenta.setSubtotal(articulo.getPrecio() * dtoDetalleVenta.getCantidad());
                    total += detalleVenta.getSubtotal();
                    newVenta.agregarDetalleVenta(detalleVenta);

                    // Actualizar el stock del artículo
                    int nuevoStock = articulo.getStock() - dtoDetalleVenta.getCantidad();
                    articulo.setStock(nuevoStock);
                    articuloRepository.save(articulo); // Guardar el artículo actualizado en la base de datos

                    // Guardar el detalleVenta en la lista para actualizar
                    detallesActualizados.add(detalleVenta);
                } else {
                    throw new Exception("Artículo no encontrado con id: " + dtoDetalleVenta.getIdArticulo());
                }
            }

            newVenta.setTotalVenta(total);
            ventaRepository.save(newVenta);

            // Guardar los detalles de venta actualizados
            detalleVentaRepository.saveAll(detallesActualizados);

            return newVenta;
        } catch (Exception e) {
            throw new Exception("Error al crear venta: " + e.getMessage());
        }
    }
    

    @Override
    public List<DTOVentasFiltroArt> filtroVentaArtFecha(Date fechaIni, Date fechaFin, Long idArt) throws Exception {
        List<DTOVentasFiltroArtProjection> ventasFiltro=ventaRepository.filtroVentaArtFecha(fechaIni,fechaFin,idArt);
        if (!ventasFiltro.isEmpty()){
            List<DTOVentasFiltroArt> listVentasFiltro=new ArrayList<DTOVentasFiltroArt>();
            for (DTOVentasFiltroArtProjection ventaProjection:ventasFiltro){
                DTOVentasFiltroArt dtoVentasFiltro=new DTOVentasFiltroArt();
                dtoVentasFiltro.setFechaFactruacion(ventaProjection.getFechaFacturacion());
                dtoVentasFiltro.setCantidad(ventaProjection.getCantidad());
                dtoVentasFiltro.setIdArt(ventaProjection.getIdArt());
                listVentasFiltro.add(dtoVentasFiltro);
            }
            return listVentasFiltro;
        } else throw new Exception("No se encontraron ventas para este articulo en las fechas indicadas...");
    }

    @Override
    public List<VentasPorMesDTO> obtenerVentasPorMes(Date fechaIni, Date fechaFin, Long idArt) throws Exception {
        List<VentasPorMesDTOProjection> ventasFiltroProjection=ventaRepository.obtenerVentasPorMes(fechaIni,fechaFin,idArt);
        if(!ventasFiltroProjection.isEmpty()) {
            List<VentasPorMesDTO> ventasFiltro=new ArrayList<VentasPorMesDTO>();
            for(VentasPorMesDTOProjection ventasProjection:ventasFiltroProjection){
                VentasPorMesDTO ventasPorMesDTO= new VentasPorMesDTO();
                ventasPorMesDTO.setMes(ventasProjection.getMes());
                ventasPorMesDTO.setCantidad(ventasProjection.getCantidad());
                ventasFiltro.add(ventasPorMesDTO);
            }
            return ventasFiltro;
        }else throw new Exception("No se encontraron ventas para este articulo en las fechas indicadas...");
    }

}
