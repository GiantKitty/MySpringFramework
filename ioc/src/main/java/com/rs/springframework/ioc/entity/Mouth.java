package com.rs.springframework.ioc.entity;

import lombok.Data;
import lombok.ToString;

@Data
public class Mouth {
    private String mouthCode;
    private Hand hand;

    public void speak(){
        System.out.println("mouth 编号：" + mouthCode + ",依赖于hand 编号" + hand.getHandCode());
        System.out.println("say hello world");
    }
}
