package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import lombok.Builder;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;


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
        detalleVenta.setVenta(this);
    }


}


