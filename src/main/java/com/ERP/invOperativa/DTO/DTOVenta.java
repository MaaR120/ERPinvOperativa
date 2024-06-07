package com.ERP.invOperativa.DTO;

import com.ERP.invOperativa.DTO.DTODetalleVenta;
import com.ERP.invOperativa.Entities.DetalleVenta;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTOVenta {
    private Date fechaFacturacion;
    private double totalVenta;
    private List<DetalleDTO> detalles = new ArrayList<>();

    // Getters y Setters
    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public List<DetalleDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleDTO> detalles) {
        this.detalles = detalles;
    }

    public static class DetalleDTO {
        private Long articuloId;
        private int cantidad;
        private double subtotal;

        // Getters y Setters
        public Long getArticuloId() {
            return articuloId;
        }

        public void setArticuloId(Long articuloId) {
            this.articuloId = articuloId;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }
    }
}