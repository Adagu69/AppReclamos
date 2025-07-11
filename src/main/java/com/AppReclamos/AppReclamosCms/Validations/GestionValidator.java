package com.AppReclamos.AppReclamosCms.Validations;


import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoReclamo;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.GestionCompetencia;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.GestionEtapa;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.MedioPresentacion;
import com.AppReclamos.AppReclamosCms.Modelos.ReclamoDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class GestionValidator implements ConstraintValidator<ValidGestion, ReclamoDTO> {
    @Override
    public boolean isValid(ReclamoDTO dto, ConstraintValidatorContext context) {
        if (dto.getGestion() == null || dto.getEstado() == null) {
            return true; // Dejar que otras validaciones se encarguen.
        }


        boolean isValid = true;
        context.disableDefaultConstraintViolation(); // Para controlar los mensajes de error.

        // --- ¡NUEVA VALIDACIÓN PARA CLASIFICACIÓN 1, 2, 3! ---
        // Criterio 29: Los campos de Clasificación son obligatorios si la competencia es 1 (SI) o 3 (COMPARTIDA).


        // --- Extracción de variables para mayor claridad ---
        EstadoReclamo estado = dto.getEstado();
        GestionEtapa etapa = dto.getGestion().getEtapaReclamo();
        GestionCompetencia competencia = dto.getGestion().getCompetencia();
        MedioPresentacion medio = (dto.getDetalles() != null && !dto.getDetalles().isEmpty())
                ? dto.getDetalles().get(0).getMedioPresentacion()
                : null;


        // --- Criterio 33: Relación entre Estado y Competencia ---
        if (estado == EstadoReclamo.TRASLADADO && competencia != GestionCompetencia.NO) {
            isValid = false;
            addError(context, "estado", "El estado solo puede ser 'Trasladado' si la Competencia es 'No'.");
        }

        // --- Criterio 34: Código Primigenio ---
        boolean esRequeridoPorEstado = (estado == EstadoReclamo.ARCHIVADO_DUPLICIDAD || estado == EstadoReclamo.ACUMULADO);
        boolean esRequeridoPorMedio = (medio == MedioPresentacion.RECLAMO_TRASLADADO || medio == MedioPresentacion.RECLAMO_COPARTICIPADO);

        if (esRequeridoPorEstado || esRequeridoPorMedio) {
            if (isBlank(dto.getGestion().getCodigoPrimigenio())) {
                isValid = false;
                addError(context, "gestion.codigoPrimigenio", "Código Primigenio es obligatorio para este Estado o Medio de Presentación.");
            }
        }

        // --- Criterio 35: Relación entre Estado y Etapa del Reclamo (LÓGICA ACTUALIZADA) ---
        switch (estado) {
            case EN_TRAMITE: // Código 2
                // Si está en trámite, la etapa debe ser una de las tres primeras.
                if (!List.of(GestionEtapa.ADMISION_Y_REGISTRO, GestionEtapa.EVALUACION_E_INVESTIGACION, GestionEtapa.RESULTADO_Y_NOTIFICACION).contains(etapa)) {
                    isValid = false;
                    addError(context, "gestion.etapaReclamo", "Para un reclamo 'En Trámite', la etapa seleccionada no es válida.");
                }
                break;
            case RESUELTO: // Código 1
                if (etapa != GestionEtapa.RESULTADO_Y_NOTIFICACION) {
                    isValid = false;
                    addError(context, "gestion.etapaReclamo", "Para un reclamo 'Resuelto', la etapa debe ser '3 - Resultado y Notificación'.");
                }
                break;
            case CONCLUIDO: // Código 6
                if (etapa != GestionEtapa.ARCHIVO_Y_CUSTODIA) {
                    isValid = false;
                    addError(context, "gestion.etapaReclamo", "Para un reclamo 'Concluido', la etapa debe ser '4 - Archivo y Custodia'.");
                }
                break;
            default:
                // No hay restricciones de etapa para otros estados.
                break;
        }

        // --- Criterios 36 y 37: Campos de Traslado ---
        if (estado == EstadoReclamo.TRASLADADO) {
            if (dto.getGestion().getTipoAdministraTraslado() == null) {
                isValid = false;
                addError(context, "gestion.tipoAdministraTraslado", "Tipo de Administrado Destino es obligatorio.");
            }
            if (isBlank(dto.getGestion().getCodigoAdministraTraslado())) {
                isValid = false;
                addError(context, "gestion.codigoAdministraTraslado", "Código de Administrado Destino es obligatorio.");
            }
        }

        return isValid;
    }

    // Métodos de ayuda
    private void addError(ConstraintValidatorContext context, String field, String message) {
        context.buildConstraintViolationWithTemplate(message).addPropertyNode(field).addConstraintViolation();
    }
    private boolean isBlank(String str) {
        return str == null || str.isBlank();
    }
}
