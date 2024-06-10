package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import lombok.Builder;

import java.util.Date;

@Entity
@Table(name = "ArticuloProveedor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArticuloProveedor extends Base{
    @Column(name = "fecha_Vigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaVigencia;

    @NotNull
    @Column(name = "precio_articulo_Proveedor")
    private double precioArticuloProveedor;

    @ManyToOne()
    @JoinColumn(name = "id_Articulo")
    private Articulo Articulo;

}
