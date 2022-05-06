package ru.hh.superscoring.dao;

import java.util.Optional;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class AnswerDao extends GenericDao{

  protected AnswerDao(SessionFactory sessionFactory){
    super(sessionFactory);
  }

  @Transactional(readOnly = true)
  public Optional<Integer> getRecordByUserId(Integer userId) {
    return getSession()
        .createQuery("select r.id from TestPass r where r.userId = :user_id and r.timeFinished is null", Integer.class)
        .setParameter("user_id", userId)
        .list()
        .stream()
        .findAny();
  }
}
