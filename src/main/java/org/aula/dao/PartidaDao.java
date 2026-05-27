package org.aula.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aula.model.Partida;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PartidaDao {
    private static final AtomicLong ID_SEQ = new AtomicLong(1L);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Partida create(Partida partida) {
        entityManager.persist(partida);
        entityManager.flush();           // garante que foi salvo
        entityManager.refresh(partida);  // recarrega com os dados completos
        return partida;
    }

    public Partida findById(Long id) {
        return entityManager.find(Partida.class, id);
    }

    public List<Partida> findAll() {
        return entityManager.createQuery("from Partida", Partida.class).getResultList();
    }

    @Transactional
    public Partida update(Partida partida) {
        return entityManager.merge(partida);
    }

    @Transactional
    public boolean deleteById(Long id) {
        Partida partida = entityManager.find(Partida.class, id);
        if (partida == null) {
            return false;
        }
        entityManager.remove(partida);
        return true;
    }

    @Transactional
    public int deleteAll() {
        return entityManager.createQuery("delete from Partida").executeUpdate();
    }

    public long nextId() {
        return ID_SEQ.incrementAndGet();
    }
}