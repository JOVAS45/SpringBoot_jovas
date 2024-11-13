package com.alibou.security.horario;

import com.alibou.security.especialidad.Especialidad;
import com.alibou.security.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface HorarioAtencionRepository extends JpaRepository<HorarioAtencion, Integer> {
    List<HorarioAtencion> findByMedicoAndEstadoTrue(Medico medico);

    List<HorarioAtencion> findByEspecialidad_IdEspecialidadAndEstadoTrue(Integer idEspecialidad);


    List<HorarioAtencion> findByDiaSemanaAndEstadoTrue(Integer diaSemana);

    boolean existsByMedicoAndDiaSemanaAndHoraInicioAndHoraFin(
            Medico medico,
            Integer diaSemana,
            LocalTime horaInicio,
            LocalTime horaFin);
}
