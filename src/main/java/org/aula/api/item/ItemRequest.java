package org.aula.api.item;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Item;

import java.math.BigDecimal;

@Schema(description = "Dados enviados para criar ou atualizar um item")
public record ItemRequest(
        @Schema(description = "ID do item (opcional na criacao)", example = "1")
        Long id,

        @Schema(description = "Nome do item", example = "Espada de Fogo")
        String nome,

        @Schema(description = "Tipo do item", example = "Arma")
        String tipo,

        @Schema(description = "Valor do item", example = "99.99")
        BigDecimal valor
) {
    public Item toEntity() {
        Item item = new Item();
        if (id != null) {
            item.setId(id);
        }
        item.setNome(nome);
        item.setTipo(tipo);
        item.setValor(valor);
        return item;
    }
}