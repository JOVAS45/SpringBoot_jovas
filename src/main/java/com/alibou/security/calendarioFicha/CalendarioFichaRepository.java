
package com.alibou.security.calendarioFicha;

import com.alibou.security.calendarioFicha.CalendarioFicha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarioFichaRepository extends JpaRepository<CalendarioFicha, Integer> {
    List<CalendarioFicha> findByIdHorario(Integer idHorario);
    boolean existsByIdHorarioAndFecha(Integer idHorario, LocalDate fecha);
}
