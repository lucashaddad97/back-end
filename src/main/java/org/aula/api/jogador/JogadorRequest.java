package org.aula.api.jogador;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Jogador;

@Schema(description = "Dados enviados para criar ou atualizar um jogador")
public record JogadorRequest(
        @Schema(description = "ID do jogador (opcional na criacao)", example = "1")
        Long id,

        @Schema(description = "Nome do jogador", example = "Carlos Silva")
        String nome,

        @Schema(description = "Nickname do jogador", example = "carlao99")
        String nickname,

        @Schema(description = "Email do jogador", example = "carlos@email.com")
        String email
) {
    public Jogador toEntity() {
        Jogador jogador = new Jogador();
        if (id != null) {
            jogador.setId(id);
        }
        jogador.setNome(nome);
        jogador.setNickname(nickname);
        jogador.setEmail(email);
        return jogador;
    }
}