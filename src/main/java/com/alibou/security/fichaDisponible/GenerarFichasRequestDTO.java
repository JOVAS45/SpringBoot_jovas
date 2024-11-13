package com.alibou.security.fichaDisponible;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerarFichasRequestDTO {
    private Integer idEspecialidad;
    private Integer idCalendario;
    private Boolean regenerarExistentes;
}
