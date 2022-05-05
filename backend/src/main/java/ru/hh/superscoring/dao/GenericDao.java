package ru.hh.superscoring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class GenericDao {
  protected final SessionFactory sessionFactory;

  public GenericDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true)
  public <T> T get(Class<T> clazz, Integer id) {
    return getSession().get(clazz, id);
  }

  @Transactional
  public void save(Object object) {
    if (object == null) {
      return;
    }
    getSession().saveOrUpdate(object);
  }

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
