package com.alibou.security.calendarioFicha;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarioFichaDTO {
    private Integer id;
    private Integer idHorario;
    private LocalDate fecha;
    private Boolean estado;
}
