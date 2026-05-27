package org.aula.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aula.model.Jogador;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class JogadorDao {
    private static final AtomicLong ID_SEQ = new AtomicLong(1L);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Jogador create(Jogador jogador) {
        jogador.setId(null);
        entityManager.persist(jogador);
        entityManager.flush();
        entityManager.refresh(jogador);
        return jogador;
    }

    public Jogador findById(Long id) {
        return entityManager.find(Jogador.class, id);
    }

    public List<Jogador> findAll() {
        return entityManager.createQuery("from Jogador", Jogador.class).getResultList();
    }

    @Transactional
    public Jogador update(Jogador jogador) {
        return entityManager.merge(jogador);
    }

    @Transactional
    public boolean deleteById(Long id) {
        Jogador jogador = entityManager.find(Jogador.class, id);
        if (jogador == null) {
            return false;
        }
        entityManager.remove(jogador);
        return true;
    }

    @Transactional
    public int deleteAll() {
        return entityManager.createQuery("delete from Jogador").executeUpdate();
    }

    public long nextId() {
        return ID_SEQ.incrementAndGet();
    }
}