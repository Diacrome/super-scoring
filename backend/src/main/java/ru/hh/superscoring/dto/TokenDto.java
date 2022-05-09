package ru.hh.superscoring.dto;

import java.time.LocalDateTime;

public class TokenDto {

  private Integer id;
  private Integer userId;
  private String accessToken;
  private LocalDateTime expireDate;

  public TokenDto(Integer id, Integer userId, String accessToken, LocalDateTime expireDate) {
    this.id = id;
    this.userId = userId;
    this.accessToken = accessToken;
    this.expireDate = expireDate;
  }

  public Integer getId() {
    return id;
  }

  public Integer getUserId() {
    return userId;
  }

  public String getAccessToken() {
    return accessToken;
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

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setExpireDate(LocalDateTime expireDate) {
    this.expireDate = expireDate;
  }
}
