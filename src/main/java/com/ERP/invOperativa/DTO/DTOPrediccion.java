package com.ERP.invOperativa.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTOPrediccion {
    public String mes;
    public int cantidad;

    public int prediccionCantidad;

}
