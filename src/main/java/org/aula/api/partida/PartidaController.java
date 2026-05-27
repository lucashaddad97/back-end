package org.aula.api.partida;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aula.dao.JogadorDao;
import org.aula.dao.JogoDao;
import org.aula.dao.PartidaDao;
import org.aula.model.Partida;
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
@RequestMapping("/partidas")
@Tag(name = "6. Partidas", description = "Operacoes de cadastro e consulta de partidas")
public class PartidaController {

    private final PartidaDao partidaDao;
    private final JogadorDao jogadorDao;
    private final JogoDao jogoDao;

    public PartidaController(PartidaDao partidaDao, JogadorDao jogadorDao, JogoDao jogoDao) {
        this.partidaDao = partidaDao;
        this.jogadorDao = jogadorDao;
        this.jogoDao = jogoDao;
    }

    @GetMapping
    @Operation(summary = "Listar partidas", description = "Retorna todas as partidas cadastradas.")
    public List<PartidaResponse> listarTodos() {
        return partidaDao.findAll().stream().map(PartidaResponse::fromEntity).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar partida por ID", description = "Retorna uma partida especifica pelo identificador.")
    public PartidaResponse buscarPorId(@PathVariable long id) {
        Partida partida = partidaDao.findById(id);
        if (partida == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida nao encontrada");
        }
        return PartidaResponse.fromEntity(partida);
    }

    @GetMapping("/next-id")
    @Operation(summary = "Obter proximo ID de partida", description = "Retorna o proximo identificador sequencial disponivel para partida.")
    public long proximoId() {
        return partidaDao.nextId();
    }

    @PostMapping
    @Operation(summary = "Criar partida", description = "Cria uma nova partida. Jogador e Jogo sao obrigatorios.")
    public ResponseEntity<PartidaResponse> criar(@RequestBody PartidaRequest request) {
        if (request.idJogador() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jogador e obrigatorio para a partida");
        }
        if (request.idJogo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jogo e obrigatorio para a partida");
        }
        if (jogadorDao.findById(request.idJogador()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogador nao encontrado");
        }
        if (jogoDao.findById(request.idJogo()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogo nao encontrado");
        }
        Partida partida = request.toEntity();
        return ResponseEntity.status(HttpStatus.CREATED).body(PartidaResponse.fromEntity(partidaDao.create(partida)));
    }

    @PostMapping("/update")
    @Operation(summary = "Atualizar partida", description = "Atualiza uma partida existente a partir do ID informado no corpo da requisicao.")
    public PartidaResponse atualizar(@RequestBody PartidaRequest request) {
        if (request.id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id e obrigatorio para atualizacao");
        }
        if (partidaDao.findById(request.id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida nao encontrada");
        }
        if (request.idJogador() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jogador e obrigatorio para a partida");
        }
        if (request.idJogo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jogo e obrigatorio para a partida");
        }
        if (jogadorDao.findById(request.idJogador()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogador nao encontrado");
        }
        if (jogoDao.findById(request.idJogo()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogo nao encontrado");
        }
        Partida partida = request.toEntity();
        return PartidaResponse.fromEntity(partidaDao.update(partida));
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "Remover partida por ID", description = "Remove uma partida existente pelo identificador.")
    public ResponseEntity<Void> removerPorId(@PathVariable long id) {
        if (!partidaDao.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida nao encontrada");
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete-all")
    @Operation(summary = "Remover todas as partidas", description = "Exclui todas as partidas cadastradas e retorna a quantidade removida.")
    public int removerTodos() {
        return partidaDao.deleteAll();
    }
}