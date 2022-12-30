package com.spring.practice.rest.examples.domain.lombokEx;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetterSetterEx {
    
    private Long value;

    // public Long getValue() {
    //     return value;
    // }

    // public void setValue(Long value) {
    //     this.value = value;
    // }
}
