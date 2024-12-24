package com.eduardocruzdev.forolastversion.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(Long id, String titulo,
                                 String usuario, LocalDateTime fechaCreacion) {
    public DatosListadoTopico(Topico Topico) {
        this(Topico.getId(), Topico.getTitulo(), Topico.getUsuario().toString(), Topico.getFechaCreacion());
    }
}