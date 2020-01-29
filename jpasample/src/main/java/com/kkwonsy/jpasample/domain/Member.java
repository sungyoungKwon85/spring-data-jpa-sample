package com.kkwonsy.jpasample.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>(); // tip best practice임. null이 안나므로.
    // 하이버네이트가 내부적으로 arraylist를 감싸버려서 다른 객체가 됨. 컬렉션을 도중에 변경하면 하이버네이트가 못쓰게되서 문제가발생할 것임
}