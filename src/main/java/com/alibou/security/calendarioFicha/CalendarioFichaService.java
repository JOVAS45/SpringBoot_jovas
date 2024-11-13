package com.alibou.security.calendarioFicha;


import com.alibou.security.horario.HorarioAtencion;
import com.alibou.security.horario.HorarioAtencionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarioFichaService {

    private final CalendarioFichaRepository calendarioFichasRepository;
    private final HorarioAtencionRepository horarioAtencionRepository;

    public void generarCalendarioFichas(Integer idHorario, LocalDate fechaInicio, LocalDate fechaFin) {
        HorarioAtencion horario = horarioAtencionRepository.findById(idHorario)
                .orElseThrow(() -> new IllegalStateException("Horario no encontrado"));

        int diaSemana = horario.getDiaSemana();

        LocalDate fecha = fechaInicio;
        while (!fecha.isAfter(fechaFin)) {
            if (fecha.getDayOfWeek().getValue() == diaSemana) {
                if (!calendarioFichasRepository.existsByIdHorarioAndFecha(idHorario, fecha)) {
                    CalendarioFicha calendarioFichas = CalendarioFicha.builder()
                            .idHorario(idHorario)
                            .fecha(fecha)
                            .estado(true)
                            .createdAt(LocalDateTime.now())
                            .build();
                    calendarioFichasRepository.save(calendarioFichas);
                }
            }
            fecha = fecha.plusDays(1);
        }
    }

    public List<CalendarioFichaDTO> obtenerCalendarioFichas(Integer idHorario) {
        List<CalendarioFicha> calendarioFichas = calendarioFichasRepository.findByIdHorario(idHorario);

        return calendarioFichas.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void actualizarEstadoFicha(Integer id, Boolean estado) {
        CalendarioFicha calendarioFichas = calendarioFichasRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Ficha no encontrada"));

        calendarioFichas.setEstado(estado);
        calendarioFichasRepository.save(calendarioFichas);
    }

    private CalendarioFichaDTO mapToDTO(CalendarioFicha calendarioFichas) {
        return CalendarioFichaDTO.builder()
                .id(calendarioFichas.getId())
                .idHorario(calendarioFichas.getIdHorario())
                .fecha(calendarioFichas.getFecha())
                .estado(calendarioFichas.getEstado())
                .build();
    }
}
