package com.eduardocruzdev.forolastversion.domain.topico;

import com.eduardocruzdev.forolastversion.domain.usuarios.DatosRespuestaUsuario;
import com.eduardocruzdev.forolastversion.domain.usuarios.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(

        Long id,
        @NotBlank
        String titulo,
        @NotBlank
        String contenido,
        LocalDateTime fechaCreacion,
        @NotNull
        DatosRespuestaUsuario usuario)
{

}
