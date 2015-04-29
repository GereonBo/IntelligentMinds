package at.intelligentminds.service.model;

// Generated 29-Apr-2015 15:07:52 by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Group.
 * @see at.intelligentminds.service.model.Group
 * @author Hibernate Tools
 */
public class GroupHome {

  private static final Log log = LogFactory.getLog(GroupHome.class);

  @PersistenceContext
  private EntityManager entityManager;

  public void persist(Group transientInstance) {
    log.debug("persisting Group instance");
    try {
      entityManager.persist(transientInstance);
      log.debug("persist successful");
    }
    catch (RuntimeException re) {
      log.error("persist failed", re);
      throw re;
    }
  }

  public void remove(Group persistentInstance) {
    log.debug("removing Group instance");
    try {
      entityManager.remove(persistentInstance);
      log.debug("remove successful");
    }
    catch (RuntimeException re) {
      log.error("remove failed", re);
      throw re;
    }
  }

  public Group merge(Group detachedInstance) {
    log.debug("merging Group instance");
    try {
      Group result = entityManager.merge(detachedInstance);
      log.debug("merge successful");
      return result;
    }
    catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public Group findById(int id) {
    log.debug("getting Group instance with id: " + id);
    try {
      Group instance = entityManager.find(Group.class, id);
      log.debug("get successful");
      return instance;
    }
    catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }
}
