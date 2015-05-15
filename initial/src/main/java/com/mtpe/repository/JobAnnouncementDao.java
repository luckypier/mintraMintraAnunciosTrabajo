package com.mtpe.repository;

/**
 * Created by precuay on 4/29/15.
 */

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.mtpe.model.JobAnnouncement;
import com.mtpe.model.User;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class JobAnnouncementDao {


    @PersistenceContext
    private EntityManager _entityManager;

    public void create(JobAnnouncement jobAnnouncement) {
        _entityManager.persist(jobAnnouncement);
        return;
    }

    @SuppressWarnings("unchecked")
    public Collection<JobAnnouncement> getAll() {
        //return _entityManager.createQuery("from JOBANNOUNCEMENT").getResultList();

        Query query = _entityManager.createQuery("SELECT e FROM JobAnnouncement e");
        return (Collection<JobAnnouncement>) query.getResultList();
    }

}
