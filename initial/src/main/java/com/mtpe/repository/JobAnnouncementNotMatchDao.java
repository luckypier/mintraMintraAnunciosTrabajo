package com.mtpe.repository;

import com.mtpe.model.JobAnnouncementNotMatch;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Created by precuay on 4/30/15.
 */
@Repository
@Transactional
public class JobAnnouncementNotMatchDao {

    @PersistenceContext
    private EntityManager _entityManager;

    public void create(JobAnnouncementNotMatch jobAnnouncementNotMatch) {
        _entityManager.persist(jobAnnouncementNotMatch);
        return;
    }

    @SuppressWarnings("unchecked")
    public Collection<JobAnnouncementNotMatch> getAll() {

        Query query = _entityManager.createQuery("SELECT e FROM JobAnnouncementNotMatch e");
        return (Collection<JobAnnouncementNotMatch>) query.getResultList();
    }
}
