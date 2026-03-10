package com.forumhub.oneforumhub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(

        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String senha
) {
}
