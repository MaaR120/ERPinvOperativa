package com.ERP.invOperativa.Entities;

import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "OrdenCompra")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class OrdenCompra extends Base{
    @NotNull
    @Column(name = "fecha_Entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;

    @NotNull
    @Column(name = "fecha_Inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @NotNull
    @Column(name = "fecha_Ultimo_Cambio")
    @Temporal(TemporalType.DATE)
    private Date fechaUltimoCambio;

    @NotNull
    @Column(name = "total_Orden_Compra")
    private double totalOrden;

    @NotNull
    @Column(name = "Estado_Orden")
    @Enumerated(EnumType.STRING)
    private EstadoOrdenCompra estadoOrdenCompra;

    @ManyToOne()
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_orden")
    @Builder.Default
    private List<DetalleOrdenCompra> detalleOrdenCompras = new ArrayList<>();
    public void agregarDetalleOrdenCompra(DetalleOrdenCompra detalleOrdenCompra){

        detalleOrdenCompras.add(detalleOrdenCompra);
    }
}
