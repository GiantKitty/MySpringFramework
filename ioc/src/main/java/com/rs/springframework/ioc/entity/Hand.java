package com.rs.springframework.ioc.entity;

import lombok.Data;
import lombok.ToString;

@Data
public class Hand {
    private String handCode;
    private Mouth mouth;


    public void waveHand(){
        System.out.println("hand 编号：" + handCode + ",依赖于mouth 编号" + mouth.getMouthCode());
        System.out.println("挥一挥手");
    }
}
