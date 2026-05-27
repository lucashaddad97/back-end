package org.aula.api.partida;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Partida;

import java.util.Date;

@Schema(description = "Dados retornados de uma partida")
public record PartidaResponse(
        @Schema(description = "ID da partida", example = "1")
        Long id,

        @Schema(description = "Data da partida", example = "2024-01-15")
        Date data,

        @Schema(description = "Pontuacao obtida na partida", example = "1500")
        Integer pontuacao,

        @Schema(description = "ID do jogador da partida", example = "10")
        Long idJogador,

        @Schema(description = "Nome do jogador da partida", example = "Carlos Silva")
        String nomeJogador,

        @Schema(description = "ID do jogo da partida", example = "3")
        Long idJogo,

        @Schema(description = "Nome do jogo da partida", example = "The Legend of Zelda")
        String nomeJogo
) {
    public static PartidaResponse fromEntity(Partida partida) {
        return new PartidaResponse(
                partida.getId(),
                partida.getData(),
                partida.getPontuacao(),
                partida.getJogador().getId(),
                partida.getJogador().getNome(),
                partida.getJogo().getId(),
                partida.getJogo().getNome()
        );
    }
}