package org.aula.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aula.model.Inventario;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InventarioDao {
    private static final AtomicLong ID_SEQ = new AtomicLong(1L);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Inventario create(Inventario inventario) {
        entityManager.persist(inventario);
        entityManager.flush();           // garante que foi salvo no banco
        entityManager.refresh(inventario); // recarrega com os dados completos
        return inventario;
    }

    public Inventario findById(Long id) {
        return entityManager.find(Inventario.class, id);
    }

    public List<Inventario> findAll() {
        return entityManager.createQuery("from Inventario", Inventario.class).getResultList();
    }

    @Transactional
    public Inventario update(Inventario inventario) {
        return entityManager.merge(inventario);
    }

    @Transactional
    public boolean deleteById(Long id) {
        Inventario inventario = entityManager.find(Inventario.class, id);
        if (inventario == null) {
            return false;
        }
        entityManager.remove(inventario);
        return true;
    }

    @Transactional
    public int deleteAll() {
        return entityManager.createQuery("delete from Inventario").executeUpdate();
    }

    public long nextId() {
        return ID_SEQ.incrementAndGet();
    }
}