package ru.hh.superscoring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "qualification")
public class Qualification {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "test_id")
  private Integer testId;

  @Column(name = "point_number")
  private Integer pointNumber;

  @Column(name = "qualification_name")
  private String qualificationName;

  public Qualification() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getTestId() {
    return testId;
  }

  public void setTestId(Integer testId) {
    this.testId = testId;
  }

  public Integer getPointNumber() {
    return pointNumber;
  }

  public void setPointNumber(Integer pointNumber) {
    this.pointNumber = pointNumber;
  }

  public String getQualificationName() {
    return qualificationName;
  }

  public void setQualificationName(String qualificationName) {
    this.qualificationName = qualificationName;
  }
}
