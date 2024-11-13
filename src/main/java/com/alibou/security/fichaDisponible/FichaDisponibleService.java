package com.alibou.security.fichaDisponible;


import com.alibou.security.calendarioFicha.CalendarioFicha;
import com.alibou.security.calendarioFicha.CalendarioFichaRepository;
import com.alibou.security.especialidad.Especialidad;
import com.alibou.security.especialidad.EspecialidadRepository;
import com.alibou.security.horario.HorarioAtencion;
import com.alibou.security.horario.HorarioAtencionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class FichaDisponibleService {

    private final FichaDisponibleRepository fichaDisponibleRepository;
    private final CalendarioFichaRepository calendarioFichasRepository;
    private final EspecialidadRepository especialidadRepository;
    private final HorarioAtencionRepository horarioAtencionRepository;

    public void generarFichasDisponibles(Integer idEspecialidad, Integer idCalendario) {
        Especialidad especialidad = especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new IllegalStateException("Especialidad no encontrada"));

        CalendarioFicha calendarioFichas = calendarioFichasRepository.findById(idCalendario)
                .orElseThrow(() -> new IllegalStateException("Calendario de fichas no encontrado"));

        HorarioAtencion horarioAtencion = horarioAtencionRepository.findById(calendarioFichas.getIdHorario())
                .orElseThrow(() -> new IllegalStateException("Horario de atención no encontrado"));

        LocalTime horaInicio = horarioAtencion.getHoraInicio();
        LocalTime horaFin = horarioAtencion.getHoraFin();
        Integer duracionConsulta = especialidad.getDuracionConsulta();
        LocalTime tiempoAtencion = LocalTime.of(0, duracionConsulta);

        List<FichaDisponible> fichasExistentes = fichaDisponibleRepository.findByIdCalendarioAndEstadoFichaOrderByHoraProgramada(
                idCalendario, "disponible");
        fichaDisponibleRepository.deleteAll(fichasExistentes);

        // Generar nuevas fichas disponibles
        List<FichaDisponible> nuevasFichas = new ArrayList<>();
        LocalTime horaActual = horaInicio;
        int nroFicha = 1;

        while (horaActual.isBefore(horaFin) || horaActual.equals(horaFin)) {
            FichaDisponible fichaDisponible = FichaDisponible.builder()
                    .idCalendario(idCalendario)
                    .nroFicha(nroFicha)
                    .horaProgramada(horaActual)
                    .estadoFicha("disponible")
                    .fechaActualizacion(LocalDateTime.now())
                    .estado(true)
                    .build();

            nuevasFichas.add(fichaDisponible);

            horaActual = horaActual.plusMinutes(duracionConsulta);
            nroFicha++;
        }

        fichaDisponibleRepository.saveAll(nuevasFichas);
    }

    public void reservarFicha(Integer idCalendario, Integer nroFicha, Integer idUsuario) {
        FichaDisponible fichaDisponible = fichaDisponibleRepository.findByIdCalendarioAndNroFicha(idCalendario, nroFicha);

        if (fichaDisponible == null) {
            throw new IllegalStateException("Ficha disponible no encontrada");
        }

        fichaDisponible.setIdUsuario(idUsuario);
        fichaDisponible.setEstadoFicha("reservada");
        fichaDisponible.setFechaActualizacion(LocalDateTime.now());

        fichaDisponibleRepository.save(fichaDisponible);
    }

    public void cancelarReserva(Integer idCalendario, Integer nroFicha, Integer idUsuario) {
        FichaDisponible fichaDisponible = fichaDisponibleRepository.findByIdCalendarioAndNroFicha(idCalendario, nroFicha);

        if (fichaDisponible == null) {
            throw new IllegalStateException("Ficha disponible no encontrada");
        }

        if (!fichaDisponible.getIdUsuario().equals(idUsuario)) {
            throw new IllegalStateException("No tienes permiso para cancelar esta reserva");
        }

        fichaDisponible.setIdUsuario(null);
        fichaDisponible.setEstadoFicha("disponible");
        fichaDisponible.setFechaActualizacion(LocalDateTime.now());

        fichaDisponibleRepository.save(fichaDisponible);
    }

    public List<FichaDisponible> obtenerFichasDisponiblesPorCalendario(Integer idCalendario) {
        return fichaDisponibleRepository.findByIdCalendarioAndEstadoFichaOrderByHoraProgramada(idCalendario, "disponible");
    }
}