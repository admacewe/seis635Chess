// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package seis.stthomas.edu.ai;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import seis.stthomas.edu.ai.PieceMove;

privileged aspect PieceMove_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager PieceMove.entityManager;
    
    public static final EntityManager PieceMove.entityManager() {
        EntityManager em = new PieceMove().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long PieceMove.countPieceMoves() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PieceMove o", Long.class).getSingleResult();
    }
    
    public static List<PieceMove> PieceMove.findAllPieceMoves() {
        return entityManager().createQuery("SELECT o FROM PieceMove o", PieceMove.class).getResultList();
    }
    
    public static PieceMove PieceMove.findPieceMove(Long id) {
        if (id == null) return null;
        return entityManager().find(PieceMove.class, id);
    }
    
    public static List<PieceMove> PieceMove.findPieceMoveEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PieceMove o", PieceMove.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void PieceMove.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void PieceMove.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PieceMove attached = PieceMove.findPieceMove(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void PieceMove.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void PieceMove.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public PieceMove PieceMove.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PieceMove merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
