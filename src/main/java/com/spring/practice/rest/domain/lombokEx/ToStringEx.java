package com.spring.practice.rest.domain.lombokEx;

import lombok.ToString;

@ToString
public class ToStringEx {

    private Long id;
    private String name;

    // @Override
    // public String toString() {
    //     return "ToStringEx(id=" + id + ", name=" + name + ")";
    // }
}
