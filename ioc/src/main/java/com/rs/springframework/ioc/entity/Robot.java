package com.rs.springframework.ioc.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Robot {

    private Hand hand;     //需要注入 hand 和 mouth
    private Mouth mouth;

    public void show(){
        hand.waveHand();
        mouth.speak();
    }
}
