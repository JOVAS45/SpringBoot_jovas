package com.alibou.security.controller;

import com.alibou.security.fichaDisponible.FichaDisponible;
import com.alibou.security.fichaDisponible.FichaDisponibleService;
import com.alibou.security.response.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fichas-disponibles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class FichaDisponibleController {


    private final FichaDisponibleService fichaDisponibleService;

    @PostMapping("/generar")
    @Operation(summary = "Generar fichas disponibles")
    public ResponseEntity<CustomResponse> generarFichasDisponibles(
            @RequestParam Integer idEspecialidad,
            @RequestParam Integer idCalendario
    ) {
        fichaDisponibleService.generarFichasDisponibles(idEspecialidad, idCalendario);
        return ResponseEntity.ok(CustomResponse.builder()
                .success(true)
                .message("Fichas disponibles generadas exitosamente")
                .status("OK")
                .build());
    }

    @PutMapping("/reservar")
    @Operation(summary = "Reservar una ficha")
    public ResponseEntity<CustomResponse> reservarFicha(
            @RequestParam Integer idCalendario,
            @RequestParam Integer nroFicha,
            @RequestParam Integer idUsuario
    ) {
        fichaDisponibleService.reservarFicha(idCalendario, nroFicha, idUsuario);
        return ResponseEntity.ok(CustomResponse.builder()
                .success(true)
                .message("Ficha reservada exitosamente")
                .status("OK")
                .build());
    }

    @PutMapping("/cancelar")
    @Operation(summary = "Cancelar una reserva")
    public ResponseEntity<CustomResponse> cancelarReserva(
            @RequestParam Integer idCalendario,
            @RequestParam Integer nroFicha,
            @RequestParam Integer idUsuario
    ) {
        fichaDisponibleService.cancelarReserva(idCalendario, nroFicha, idUsuario);
        return ResponseEntity.ok(CustomResponse.builder()
                .success(true)
                .message("Reserva cancelada exitosamente")
                .status("OK")
                .build());
    }

    @GetMapping("/calendario/{idCalendario}")
    @Operation(summary = "Obtener fichas disponibles por calendario")
    public ResponseEntity<CustomResponse> obtenerFichasDisponiblesPorCalendario(
            @PathVariable Integer idCalendario
    ) {
        List<FichaDisponible> fichasDisponibles = fichaDisponibleService.obtenerFichasDisponiblesPorCalendario(idCalendario);
        return ResponseEntity.ok(CustomResponse.builder()
                .success(true)
                .message("Fichas disponibles obtenidas exitosamente")
                .status("OK")
                .data(fichasDisponibles)
                .build());
    }
}
