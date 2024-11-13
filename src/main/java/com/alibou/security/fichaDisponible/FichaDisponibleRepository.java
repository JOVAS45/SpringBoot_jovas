package com.alibou.security.fichaDisponible;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FichaDisponibleRepository extends JpaRepository<FichaDisponible, Integer> {
    List<FichaDisponible> findByIdCalendarioAndEstadoFichaOrderByHoraProgramada(Integer idCalendario, String estadoFicha);
    FichaDisponible findByIdCalendarioAndNroFicha(Integer idCalendario, Integer nroFicha);
}

