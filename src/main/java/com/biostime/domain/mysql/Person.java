package com.biostime.domain.mysql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by 13006 on 2017/6/9.
 */
@Entity
@Table(name = "mama100_person")
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String nationBaseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getNationBaseName() {
        return nationBaseName;
    }

    public void setNationBaseName(String nationBaseName) {
        this.nationBaseName = nationBaseName;
    }
}
