package ru.hh.superscoring.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "token")
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "token_id")
  private Integer id;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "token")
  private String token;

  @Column(name = "expire_date")
  private LocalDateTime expireDate;

  public Token() {
  }

  public Integer getId() {
    return id;
  }

  public Integer getUserId() {
    return userId;
  }

  public String getToken() {
    return token;
  }

  public LocalDateTime getExpireDate() {
    return expireDate;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setExpireDate(LocalDateTime expireDate) {
    this.expireDate = expireDate;
  }
}
