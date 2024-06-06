package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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



    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private List<ArticuloProveedor> articuloProveedores = new ArrayList<>();


    // Método para agregar un ArticuloProveedor a la lista
    public void addArticuloProveedor(ArticuloProveedor articuloProveedor) {
        articuloProveedores.add(articuloProveedor);
        articuloProveedor.setProveedor(this);
    }

}
