package org.aula.api.jogo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aula.dao.JogoDao;
import org.aula.model.Jogo;
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
@RequestMapping("/jogos")
@Tag(name = "5. Jogos", description = "Operacoes de cadastro e consulta de jogos")
public class JogoController {

    private final JogoDao jogoDao;

    public JogoController(JogoDao jogoDao) {
        this.jogoDao = jogoDao;
    }

    @GetMapping
    @Operation(summary = "Listar jogos", description = "Retorna todos os jogos cadastrados.")
    public List<JogoResponse> listarTodos() {
        return jogoDao.findAll().stream().map(JogoResponse::fromEntity).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar jogo por ID", description = "Retorna um jogo especifico pelo identificador.")
    public JogoResponse buscarPorId(@PathVariable long id) {
        Jogo jogo = jogoDao.findById(id);
        if (jogo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogo nao encontrado");
        }
        return JogoResponse.fromEntity(jogo);
    }

    @GetMapping("/next-id")
    @Operation(summary = "Obter proximo ID de jogo", description = "Retorna o proximo identificador sequencial disponivel para jogo.")
    public long proximoId() {
        return jogoDao.nextId();
    }

    @PostMapping
    @Operation(summary = "Criar jogo", description = "Cria um novo jogo.")
    public ResponseEntity<JogoResponse> criar(@RequestBody JogoRequest request) {
        Jogo jogo = request.toEntity();
        return ResponseEntity.status(HttpStatus.CREATED).body(JogoResponse.fromEntity(jogoDao.create(jogo)));
    }

    @PostMapping("/update")
    @Operation(summary = "Atualizar jogo", description = "Atualiza um jogo existente a partir do ID informado no corpo da requisicao.")
    public JogoResponse atualizar(@RequestBody JogoRequest request) {
        if (request.id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id e obrigatorio para atualizacao");
        }
        return JogoResponse.fromEntity(jogoDao.update(request.toEntity()));
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Remover jogo por ID", description = "Remove um jogo existente pelo identificador.")
    public ResponseEntity<Void> removerPorId(@PathVariable long id) {
        if (!jogoDao.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogo nao encontrado");
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete-all")
    @Operation(summary = "Remover todos os jogos", description = "Exclui todos os jogos cadastrados e retorna a quantidade removida.")
    public int removerTodos() {
        return jogoDao.deleteAll();
    }
}