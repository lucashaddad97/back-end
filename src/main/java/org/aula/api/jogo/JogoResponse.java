package org.aula.api.jogo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Jogo;

@Schema(description = "Dados retornados de um jogo")
public record JogoResponse(
        @Schema(description = "ID do jogo", example = "1")
        Long id,

        @Schema(description = "Nome do jogo", example = "The Legend of Zelda")
        String nome,

        @Schema(description = "Genero do jogo", example = "Aventura")
        String genero
) {
    public static JogoResponse fromEntity(Jogo jogo) {
        return new JogoResponse(
                jogo.getId(),
                jogo.getNome(),
                jogo.getGenero()
        );
    }
}