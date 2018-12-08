package com.financetracker.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "username", "email"}))
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private long userId;

  @NotNull
  @Size(min = 3, max = 45)
  @NotEmpty
  @Pattern(regexp = "[^\\s]+")
  @Column(name = "username")
  private String username;

  @NotNull
  @NotEmpty
  @Column(name = "password", updatable = false)
  private byte[] password;

  @NotNull
  @NotEmpty
  @Email
  @Pattern(regexp =
      "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
  @Column(name = "email")
  private String email;

  @NotNull
  @Size(min = 3, max = 45)
  @NotEmpty
  @Pattern(regexp = "[^\\s]+")
  @Column(name = "first_name")
  private String firstName;

  @NotNull
  @Size(min = 3, max = 45)
  @NotEmpty
  @Pattern(regexp = "[^\\s]+")
  @Column(name = "last_name")
  private String lastName;

  @Column(name = "password_token")
  private String passwordToken;

  @Column(name = "position")
  private String position;

  @Column(name = "isAdmin")
  private Boolean isAdmin;

  public User() {
  }

  public User(String email, String firstName) {
    this.email = email;
    this.firstName = firstName;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username.trim();
  }

  public byte[] getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = DigestUtils.sha512(password.trim());
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName.trim();
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName.trim();
  }

  public String getPasswordToken() {
    return passwordToken;
  }

  public void setPasswordToken(String passwordToken) {
    this.passwordToken = passwordToken;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setAdmin(Boolean admin) {
    isAdmin = admin;
  }
}
