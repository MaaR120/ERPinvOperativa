package com.ERP.invOperativa.Entities;

import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import com.ERP.invOperativa.Enum.Modelo;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "FamiliaArticulo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class FamiliaArticulo extends Base{
    @NotNull
    @Column(name = "nombre_Familia", length = 50, nullable = false)
    private String nombreFamilia;

    @NotNull
    @Column(name = "Modelo")
    @Enumerated(EnumType.STRING)
    private Modelo modelo;

}
