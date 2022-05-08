package ru.hh.superscoring.dto;

import java.time.LocalDateTime;

public class TokenDto {

  private Integer id;
  private Integer userId;
  private String token;
  private LocalDateTime expireDate;

  public TokenDto(Integer id, Integer userId, String token, LocalDateTime expireDate) {
    this.id = id;
    this.userId = userId;
    this.token = token;
    this.expireDate = expireDate;
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
