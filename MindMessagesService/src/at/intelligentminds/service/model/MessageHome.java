package at.intelligentminds.service.model;

// Generated 29-Apr-2015 15:07:52 by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Message.
 * @see at.intelligentminds.service.model.Message
 * @author Hibernate Tools
 */
public class MessageHome {

  private static final Log log = LogFactory.getLog(MessageHome.class);

  @PersistenceContext
  private EntityManager entityManager;

  public void persist(Message transientInstance) {
    log.debug("persisting Message instance");
    try {
      entityManager.persist(transientInstance);
      log.debug("persist successful");
    }
    catch (RuntimeException re) {
      log.error("persist failed", re);
      throw re;
    }
  }

  public void remove(Message persistentInstance) {
    log.debug("removing Message instance");
    try {
      entityManager.remove(persistentInstance);
      log.debug("remove successful");
    }
    catch (RuntimeException re) {
      log.error("remove failed", re);
      throw re;
    }
  }

  public Message merge(Message detachedInstance) {
    log.debug("merging Message instance");
    try {
      Message result = entityManager.merge(detachedInstance);
      log.debug("merge successful");
      return result;
    }
    catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public Message findById(int id) {
    log.debug("getting Message instance with id: " + id);
    try {
      Message instance = entityManager.find(Message.class, id);
      log.debug("get successful");
      return instance;
    }
    catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }
}
