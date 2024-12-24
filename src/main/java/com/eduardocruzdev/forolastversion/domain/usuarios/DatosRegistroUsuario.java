package com.eduardocruzdev.forolastversion.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record DatosRegistroUsuario(

        @NotBlank
        String login,
        @NotBlank
        String clave,
        @Email
        String email

) {
}
