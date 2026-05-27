package org.aula.api.item;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Item;

import java.math.BigDecimal;

@Schema(description = "Dados retornados de um item")
public record ItemResponse(
        @Schema(description = "ID do item", example = "1")
        Long id,

        @Schema(description = "Nome do item", example = "Espada de Fogo")
        String nome,

        @Schema(description = "Tipo do item", example = "Arma")
        String tipo,

        @Schema(description = "Valor do item", example = "99.99")
        BigDecimal valor
) {
    public static ItemResponse fromEntity(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getNome(),
                item.getTipo(),
                item.getValor()
        );
    }
}