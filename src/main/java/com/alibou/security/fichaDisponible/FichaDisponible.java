package com.alibou.security.fichaDisponible;

import com.alibou.security.fichaDisponible.EstadoFicha;
import com.alibou.security.paciente.Paciente;
import com.alibou.security.user.User;
import jakarta.persistence.*;
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
@Entity
@Table(name = "fichas_disponibles",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id_calendario", "nro_ficha"})})

public class FichaDisponible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_calendario", nullable = false)
    private Integer idCalendario;

    @Column(name = "nro_ficha", nullable = false)
    private Integer nroFicha;

    @Column(name = "hora_programada", nullable = false)
    private LocalTime horaProgramada;

    @Column(name = "estado_ficha", nullable = false)
    private String estadoFicha;

    @Column(name = "id_ficha_atencion")
    private Integer idFichaAtencion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(nullable = false)
    private Boolean estado;

    @Column(name = "id_usuario")
    private Integer idUsuario;
}