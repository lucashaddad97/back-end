package org.aula.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aula.model.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemDao {
    private static final AtomicLong ID_SEQ = new AtomicLong(1L);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Item create(Item item) {
        item.setId(null);
        entityManager.persist(item);
        entityManager.flush();
        entityManager.refresh(item);
        return item;
    }

    public Item findById(Long id) {
        return entityManager.find(Item.class, id);
    }

    public List<Item> findAll() {
        return entityManager.createQuery("from Item", Item.class).getResultList();
    }

    @Transactional
    public Item update(Item item) {
        return entityManager.merge(item);
    }

    @Transactional
    public boolean deleteById(Long id) {
        Item item = entityManager.find(Item.class, id);
        if (item == null) {
            return false;
        }
        entityManager.remove(item);
        return true;
    }

    @Transactional
    public int deleteAll() {
        return entityManager.createQuery("delete from Item").executeUpdate();
    }

    public long nextId() {
        return ID_SEQ.incrementAndGet();
    }
}