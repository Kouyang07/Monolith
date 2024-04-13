package org.kouyang07.monolith.items.cooldown;

import lombok.Data;

@Data
public class Cooldown {
    private CoolDownItems item;
    private long expiration;

    public Cooldown(CoolDownItems item, long expiration){
        this.item = item;
        this.expiration = expiration;
    }
}


