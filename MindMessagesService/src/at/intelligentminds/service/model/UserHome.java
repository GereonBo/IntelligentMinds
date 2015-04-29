package at.intelligentminds.service.model;

// Generated 29-Apr-2015 15:07:52 by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class User.
 * @see at.intelligentminds.service.model.User
 * @author Hibernate Tools
 */
public class UserHome {

  private static final Log log = LogFactory.getLog(UserHome.class);

  @PersistenceContext
  private EntityManager entityManager;

  public void persist(User transientInstance) {
    log.debug("persisting User instance");
    try {
      entityManager.persist(transientInstance);
      log.debug("persist successful");
    }
    catch (RuntimeException re) {
      log.error("persist failed", re);
      throw re;
    }
  }

  public void remove(User persistentInstance) {
    log.debug("removing User instance");
    try {
      entityManager.remove(persistentInstance);
      log.debug("remove successful");
    }
    catch (RuntimeException re) {
      log.error("remove failed", re);
      throw re;
    }
  }

  public User merge(User detachedInstance) {
    log.debug("merging User instance");
    try {
      User result = entityManager.merge(detachedInstance);
      log.debug("merge successful");
      return result;
    }
    catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public User findById(int id) {
    log.debug("getting User instance with id: " + id);
    try {
      User instance = entityManager.find(User.class, id);
      log.debug("get successful");
      return instance;
    }
    catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

}
