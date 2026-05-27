package org.aula.api.inventario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aula.dao.InventarioDao;
import org.aula.model.Inventario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/inventarios")
@Tag(name = "3. Inventarios", description = "Operacoes de cadastro e consulta de inventarios")
public class InventarioController {

    private final InventarioDao inventarioDao;

    public InventarioController(InventarioDao inventarioDao) {
        this.inventarioDao = inventarioDao;
    }

    @GetMapping
    @Operation(summary = "Listar inventarios", description = "Retorna todos os inventarios cadastrados.")
    public List<InventarioResponse> listarTodos() {
        return inventarioDao.findAll().stream().map(InventarioResponse::fromEntity).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar inventario por ID", description = "Retorna um inventario especifico pelo identificador.")
    public InventarioResponse buscarPorId(@PathVariable long id) {
        Inventario inventario = inventarioDao.findById(id);
        if (inventario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventario nao encontrado");
        }
        return InventarioResponse.fromEntity(inventario);
    }

    @GetMapping("/next-id")
    @Operation(summary = "Obter proximo ID de inventario", description = "Retorna o proximo identificador sequencial disponivel para inventario.")
    public long proximoId() {
        return inventarioDao.nextId();
    }

    @PostMapping
    @Operation(summary = "Criar inventario", description = "Cria um novo inventario. Jogador e Item sao obrigatorios.")
    public ResponseEntity<InventarioResponse> criar(@RequestBody InventarioRequest request) {
        if (request.idJogador() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jogador e obrigatorio para o inventario");
        }
        if (request.idItem() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item e obrigatorio para o inventario");
        }
        Inventario inventario = request.toEntity();
        return ResponseEntity.status(HttpStatus.CREATED).body(InventarioResponse.fromEntity(inventarioDao.create(inventario)));
    }

    @PostMapping("/update")
    @Operation(summary = "Atualizar inventario", description = "Atualiza um inventario existente a partir do ID informado no corpo da requisicao.")
    public InventarioResponse atualizar(@RequestBody InventarioRequest request) {
        if (request.id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id e obrigatorio para atualizacao");
        }
        return InventarioResponse.fromEntity(inventarioDao.update(request.toEntity()));
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Remover inventario por ID", description = "Remove um inventario existente pelo identificador.")
    public ResponseEntity<Void> removerPorId(@PathVariable long id) {
        if (!inventarioDao.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventario nao encontrado");
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete-all")
    @Operation(summary = "Remover todos os inventarios", description = "Exclui todos os inventarios cadastrados e retorna a quantidade removida.")
    public int removerTodos() {
        return inventarioDao.deleteAll();
    }
}