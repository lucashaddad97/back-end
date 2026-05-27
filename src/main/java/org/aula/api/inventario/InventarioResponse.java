package org.aula.api.inventario;

import io.swagger.v3.oas.annotations.media.Schema;
import org.aula.model.Inventario;

public record InventarioResponse(
        Long id,
        Integer quantidade,
        Long idJogador,
        String nomeJogador,
        Long idItem,
        String nomeItem
) {
    public static InventarioResponse fromEntity(Inventario inventario) {
        return new InventarioResponse(
                inventario.getId(),
                inventario.getQuantidade(),
                inventario.getJogador().getId(),
                inventario.getJogador().getNome(),
                inventario.getItem().getId(),
                inventario.getItem().getNome()
        );
    }
}