package org.aula.api.partida;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Jogador;
import org.aula.model.Jogo;
import org.aula.model.Partida;

import java.util.Date;

@Schema(description = "Dados enviados para criar ou atualizar uma partida")
public record PartidaRequest(
        @Schema(description = "ID da partida (opcional na criacao)", example = "1")
        Long id,

        @Schema(description = "Data da partida", example = "2024-01-15")
        Date data,

        @Schema(description = "Pontuacao obtida na partida", example = "1500")
        Integer pontuacao,

        @Schema(description = "ID do jogador da partida", example = "10")
        Long idJogador,

        @Schema(description = "ID do jogo da partida", example = "3")
        Long idJogo
) {
    public Partida toEntity() {
        Partida partida = new Partida();
        if (id != null) {
            partida.setId(id);
        }
        partida.setData(data);
        partida.setPontuacao(pontuacao);

        Jogador jogador = new Jogador();
        jogador.setId(idJogador);
        partida.setJogador(jogador);

        Jogo jogo = new Jogo();
        jogo.setId(idJogo);
        partida.setJogo(jogo);

        return partida;
    }
}