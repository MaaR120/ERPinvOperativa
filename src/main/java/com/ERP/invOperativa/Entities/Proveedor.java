package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Proveedor extends Base{
    @Column(name = "nombre_Proveedor", length = 50, nullable = false)
    private String nombreProveedor;

    @Column(name = "fecha_baja")
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

    @NotNull
    @Column(name = "Tiempo_Estimado_Entrega")
    private int tiempoEstimadoEntrega;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor")
    @Builder.Default
    private List<ArticuloProveedor> articuloProveedores = new ArrayList<>();
    public void agregarArticuloProveedor(ArticuloProveedor articuloProveedor){
        articuloProveedores.add(articuloProveedor);
    }
}
