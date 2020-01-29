package com.kkwonsy.jpasample.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // tip 임베디드타입에 대해 protected로 기본생성자를 두어 더 안전하게 했다 (JPA가 리플렉션 하도록..)
    protected Address() {
    }

    // tip immutable 하도록 생성자로 생성되도록 한다. setter는 쓰지 않는다
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
