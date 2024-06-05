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
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService  {
    @Autowired
    protected VentaRepository ventaRepository;
    @Autowired
    protected ArticuloRepository articuloRepository;

    @Autowired
    protected DetalleVentaRepository detalleVentaRepository;

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
