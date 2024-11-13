package com.alibou.security.controller;


import com.alibou.security.calendarioFicha.CalendarioFichaService;
import com.alibou.security.response.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/calendario-fichas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class CalendarioFichaController {

    private final CalendarioFichaService calendarioFichasService;

    @PostMapping
    @Operation(summary = "Generar calendario de fichas")
    public ResponseEntity<CustomResponse> generarCalendarioFichas(
            @RequestParam Integer idHorario,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin
    ) {
        calendarioFichasService.generarCalendarioFichas(idHorario, fechaInicio, fechaFin);
        return ResponseEntity.ok(CustomResponse.builder()
                .success(true)
                .message("Calendario de fichas generado exitosamente")
                .status("OK")
                .build());
    }

    @GetMapping
    @Operation(summary = "Obtener calendario de fichas")
    public ResponseEntity<CustomResponse> obtenerCalendarioFichas(@RequestParam Integer idHorario) {
        return ResponseEntity.ok(CustomResponse.builder()
                .success(true)
                .message("Calendario de fichas obtenido exitosamente")
                .status("OK")
                .data(calendarioFichasService.obtenerCalendarioFichas(idHorario))
                .build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estado de ficha")
    public ResponseEntity<CustomResponse> actualizarEstadoFicha(
            @PathVariable Integer id,
            @RequestParam Boolean estado
    ) {
        calendarioFichasService.actualizarEstadoFicha(id, estado);
        return ResponseEntity.ok(CustomResponse.builder()
                .success(true)
                .message("Estado de ficha actualizado exitosamente")
                .status("OK")
                .build());
    }
}
