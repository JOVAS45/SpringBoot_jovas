package com.alibou.security.calendarioFicha;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calendario_fichas")
public class CalendarioFicha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_horario", nullable = false)
    private Integer idHorario;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Boolean estado;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}