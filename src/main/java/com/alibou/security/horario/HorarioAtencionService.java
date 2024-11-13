package com.alibou.security.horario;

import com.alibou.security.especialidad.Especialidad;
import com.alibou.security.especialidad.EspecialidadRepository;
import com.alibou.security.medico.Medico;
import com.alibou.security.medico.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HorarioAtencionService {

    private final HorarioAtencionRepository horarioRepository;
    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    public HorarioAtencionDTO crearHorario(HorarioAtencionDTO horarioDTO) {
        Medico medico = medicoRepository.findById(horarioDTO.getIdMedico())
                .orElseThrow(() -> new IllegalStateException("Médico no encontrado"));

        Especialidad especialidad = especialidadRepository.findById(horarioDTO.getIdEspecialidad())
                .orElseThrow(() -> new IllegalStateException("Especialidad no encontrada"));

        if (horarioRepository.existsByMedicoAndDiaSemanaAndHoraInicioAndHoraFin(
                medico, horarioDTO.getDiaSemana(), horarioDTO.getHoraInicio(), horarioDTO.getHoraFin())) {
            throw new IllegalStateException("Ya existe un horario para este médico en este día y hora");
        }

        HorarioAtencion horario = HorarioAtencion.builder()
                .medico(medico)
                .especialidad(especialidad)
                .diaSemana(horarioDTO.getDiaSemana())
                .horaInicio(horarioDTO.getHoraInicio())
                .horaFin(horarioDTO.getHoraFin())
                .nroFichasTurno(horarioDTO.getNroFichasTurno())
                .estado(true)
                .build();

        horarioRepository.save(horario);

        return mapToDTO(horario);
    }

    public List<HorarioAtencionDTO> obtenerPorMedico(Integer idMedico) {
        Medico medico = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new IllegalStateException("Médico no encontrado"));

        List<HorarioAtencion> horarios = horarioRepository.findByMedicoAndEstadoTrue(medico);

        return horarios.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<HorarioAtencionDTO> obtenerPorEspecialidad(Integer idEspecialidad) {
        List<HorarioAtencion> horarios = horarioRepository.findByEspecialidad_IdEspecialidadAndEstadoTrue(idEspecialidad);

        return horarios.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public List<HorarioAtencionDTO> obtenerPorDia(Integer diaSemana) {
        List<HorarioAtencion> horarios = horarioRepository.findByDiaSemanaAndEstadoTrue(diaSemana);

        return horarios.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public HorarioAtencionDTO actualizarHorario(Integer id, HorarioAtencionDTO horarioDTO) {
        HorarioAtencion horario = horarioRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Horario no encontrado"));

        Medico medico = medicoRepository.findById(horarioDTO.getIdMedico())
                .orElseThrow(() -> new IllegalStateException("Médico no encontrado"));

        Especialidad especialidad = especialidadRepository.findById(horarioDTO.getIdEspecialidad())
                .orElseThrow(() -> new IllegalStateException("Especialidad no encontrada"));

        horario.setMedico(medico);
        horario.setEspecialidad(especialidad);
        horario.setDiaSemana(horarioDTO.getDiaSemana());
        horario.setHoraInicio(horarioDTO.getHoraInicio());
        horario.setHoraFin(horarioDTO.getHoraFin());
        horario.setNroFichasTurno(horarioDTO.getNroFichasTurno());

        horarioRepository.save(horario);

        return mapToDTO(horario);
    }

    public void eliminarHorario(Integer id) {
        HorarioAtencion horario = horarioRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Horario no encontrado"));

        horario.setEstado(false);
        horarioRepository.save(horario);
    }

    private HorarioAtencionDTO mapToDTO(HorarioAtencion horario) {
        return HorarioAtencionDTO.builder()
                .id(horario.getId())
                .idEspecialidad(horario.getEspecialidad().getIdEspecialidad())
                .nombreEspecialidad(horario.getEspecialidad().getNombre())
                .idMedico(horario.getMedico().getId())
                .nombreMedico(horario.getMedico().getNombre() + " " + horario.getMedico().getApellidos())
                .diaSemana(horario.getDiaSemana())
                .horaInicio(horario.getHoraInicio())
                .horaFin(horario.getHoraFin())
                .nroFichasTurno(horario.getNroFichasTurno())
                .estado(horario.getEstado())
                .build();
    }
}
