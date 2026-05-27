package org.aula.api.inventario;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Inventario;
import org.aula.model.Item;
import org.aula.model.Jogador;

@Schema(description = "Dados enviados para criar ou atualizar um inventario")
public record InventarioRequest(
        Long id,
        Integer quantidade,
        Long idJogador,
        Long idItem
) {
    public Inventario toEntity() {
        if (idJogador == null) {
            throw new IllegalArgumentException("Jogador é obrigatório para o inventário.");
        }
        if (idItem == null) {
            throw new IllegalArgumentException("Item é obrigatório para o inventário.");
        }

        Inventario inventario = new Inventario();
        if (id != null) {
            inventario.setId(id);
        }
        inventario.setQuantidade(quantidade);

        Jogador jogador = new Jogador();
        jogador.setId(idJogador);
        inventario.setJogador(jogador);

        Item item = new Item();
        item.setId(idItem);
        inventario.setItem(item);

        return inventario;
    }
}