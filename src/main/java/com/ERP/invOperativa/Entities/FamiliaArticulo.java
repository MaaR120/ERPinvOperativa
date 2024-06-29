package com.ERP.invOperativa.Entities;

import com.ERP.invOperativa.Enum.Modelo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FamiliaArticulo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FamiliaArticulo extends Base{
    @NotNull
    @Column(name = "nombre_Familia", length = 50, nullable = false)
    private String nombreFamilia;

    @NotNull
    @Column(name = "Modelo")
    @Enumerated(EnumType.STRING)
    private Modelo modelo;

    @OneToMany(cascade = CascadeType.REFRESH,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "FamiliaArticulo")
    @Builder.Default
    @JsonIgnore
    private List<Articulo> articulos = new ArrayList<>();

    public void agregarArticulos(Articulo articulo){
        articulos.add(articulo);
    }

}
