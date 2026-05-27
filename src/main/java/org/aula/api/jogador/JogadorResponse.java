package org.aula.api.jogador;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Jogador;

@Schema(description = "Dados retornados de um jogador")
public record JogadorResponse(
        @Schema(description = "ID do jogador", example = "1")
        Long id,

        @Schema(description = "Nome do jogador", example = "Carlos Silva")
        String nome,

        @Schema(description = "Nickname do jogador", example = "carlao99")
        String nickname,

        @Schema(description = "Email do jogador", example = "carlos@email.com")
        String email
) {
    public static JogadorResponse fromEntity(Jogador jogador) {
        return new JogadorResponse(
                jogador.getId(),
                jogador.getNome(),
                jogador.getNickname(),
                jogador.getEmail()
        );
    }
}