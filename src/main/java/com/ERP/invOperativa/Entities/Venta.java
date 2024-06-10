package com.ERP.invOperativa.Entities;

import com.ERP.invOperativa.DTO.DTODetalleVenta;
import com.ERP.invOperativa.DTO.DTOVenta;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import lombok.Builder;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Venta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Venta extends Base {

    @NotNull
    @Column(name = "fecha_Venta")
    @Temporal(TemporalType.DATE)
    private Date fechaFacturacion;

    @NotNull
    @Column(name = "total_venta")
    private double totalVenta;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "venta_id")
    @Builder.Default
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public void agregarDetalleVenta(DetalleVenta detalleVenta){
        detalleVentas.add(detalleVenta);
    }
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

    public List<DetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(List<DetalleVenta> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }



}


