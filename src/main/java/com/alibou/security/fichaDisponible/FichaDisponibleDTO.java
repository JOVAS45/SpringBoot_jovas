package com.alibou.security.fichaDisponible;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FichaDisponibleDTO {
    private Integer id;
    private Integer idCalendario;
    private String nroFicha;
    private LocalTime horaProgramada;
    private EstadoFicha estadoFicha;
    private Integer idPaciente;
    private String nombrePaciente;
    private String dniPaciente;
    private Integer idUsuarioReserva;
    private String nombreUsuarioReserva;
    private LocalDateTime fechaReserva;
    private String motivoCancelacion;
    private LocalDateTime fechaCancelacion;
    private String nombreMedico;
    private String nombreEspecialidad;
    private String gravedad;
}
