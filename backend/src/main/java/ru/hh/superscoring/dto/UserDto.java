package ru.hh.superscoring.dto;

import ru.hh.superscoring.entity.User;
import ru.hh.superscoring.util.Role;

public class UserDto {

  private Integer id;
  private String login;
  private String password;
  private Role role;

  public static UserDto map(User user) {
    if (user != null) {
      UserDto userDto = new UserDto();
      userDto.setId(user.getId());
      userDto.setLogin(user.getLogin());
      userDto.setPassword(user.getPassword());
      userDto.setRole(user.getRole());
      return userDto;
    } else {
      return null;
    }
  }

  public UserDto() {
  }

  public Integer getId() {
    return id;
  }

  public String getLogin() {
    return login;
  }

  public String getPassword() {
    return password;
  }

  public Role getRole() {
    return role;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
