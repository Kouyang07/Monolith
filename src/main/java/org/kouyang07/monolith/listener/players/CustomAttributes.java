package org.kouyang07.monolith.listener.players;

import lombok.Data;

@Data
public class CustomAttributes {
    int extraDamage;
    int extraDefense;
    int extraSpeed;
    public CustomAttributes(int extraDamage, int extraDefense, int extraSpeed) {
        this.extraDamage = extraDamage;
        this.extraDefense = extraDefense;
        this.extraSpeed = extraSpeed;
    }
}
