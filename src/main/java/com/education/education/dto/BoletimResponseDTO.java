package com.education.education.dto;

import com.education.education.model.Nota;

import java.util.List;

public record BoletimResponseDTO(List<NotaResponseDTO> notas) {
}
