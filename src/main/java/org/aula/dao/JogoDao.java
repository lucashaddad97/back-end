package org.aula.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aula.model.Jogo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class JogoDao {
    private static final AtomicLong ID_SEQ = new AtomicLong(1L);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Jogo create(Jogo jogo) {
        jogo.setId(null);
        entityManager.persist(jogo);
        entityManager.flush();
        entityManager.refresh(jogo);
        return jogo;
    }

    public Jogo findById(Long id) {
        return entityManager.find(Jogo.class, id);
    }

    public List<Jogo> findAll() {
        return entityManager.createQuery("from Jogo", Jogo.class).getResultList();
    }

    @Transactional
    public Jogo update(Jogo jogo) {
        return entityManager.merge(jogo);
    }

    @Transactional
    public boolean deleteById(Long id) {
        Jogo jogo = entityManager.find(Jogo.class, id);
        if (jogo == null) {
            return false;
        }
        entityManager.remove(jogo);
        return true;
    }

    @Transactional
    public int deleteAll() {
        return entityManager.createQuery("delete from Jogo").executeUpdate();
    }

    public long nextId() {
        return ID_SEQ.incrementAndGet();
    }
}