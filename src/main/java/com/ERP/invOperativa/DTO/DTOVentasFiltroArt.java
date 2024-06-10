package com.ERP.invOperativa.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DTOVentasFiltroArt {
    private Date fechaFactruacion;
    private int cantidad;
    private Long idArt;

}
