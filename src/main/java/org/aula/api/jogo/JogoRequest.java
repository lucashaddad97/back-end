package org.aula.api.jogo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Jogo;

@Schema(description = "Dados enviados para criar ou atualizar um jogo")
public record JogoRequest(
        @Schema(description = "ID do jogo (opcional na criacao)", example = "1")
        Long id,

        @Schema(description = "Nome do jogo", example = "The Legend of Zelda")
        String nome,

        @Schema(description = "Genero do jogo", example = "Aventura")
        String genero
) {
    public Jogo toEntity() {
        Jogo jogo = new Jogo();
        if (id != null) {
            jogo.setId(id);
        }
        jogo.setNome(nome);
        jogo.setGenero(genero);
        return jogo;
    }
}