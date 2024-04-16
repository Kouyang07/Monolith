package org.kouyang07.monolith.items.cooldown;

import lombok.Data;
import org.kouyang07.monolith.Monolith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.kouyang07.monolith.Monolith.debug;

@Data
public class Cooldown {
    private CoolDownItems item;
    private long expiration;
    public static Map<UUID, ArrayList<Cooldown>> cooldownList = new HashMap<>();

    public Cooldown(CoolDownItems item, long expiration){
        this.item = item;
        this.expiration = expiration;
    }
    public static void addCoolDown(UUID uuid, CoolDownItems item, double coolDown){
        if(cooldownList.containsKey(uuid)){
            boolean alreadySet = false;
            for(Cooldown cd : cooldownList.get(uuid)){
                if(cd.getItem().equals(item)){
                    cd.setExpiration((long) (System.currentTimeMillis() + (coolDown * 1000)));
                    if(debug) {
                        getLogger().log(Level.INFO, "Overwrote " + cooldownList.get(uuid).get(cooldownList.get(uuid).size() - 1).getItem() + " expiring at " + cooldownList.get(uuid).get(cooldownList.get(uuid).size() - 1).getExpiration());
                    }
                    alreadySet = true;
                }
            }
            if(!alreadySet) {
                cooldownList.get(uuid).add(new Cooldown(item, (long) (System.currentTimeMillis() + (coolDown * 1000))));
                if(debug) {
                    getLogger().log(Level.INFO, "Appended " + cooldownList.get(uuid).get(cooldownList.get(uuid).size() - 1).getItem() + " expiring at " + cooldownList.get(uuid).get(cooldownList.get(uuid).size() - 1).getExpiration());
                }
            }
        }else{
            cooldownList.put(uuid, new ArrayList<>());
            cooldownList.get(uuid).add(new Cooldown(item, (long) (System.currentTimeMillis() + (coolDown * 1000))));
            if(debug) {
                getLogger().log(Level.INFO, "Added " + cooldownList.get(uuid).get(cooldownList.get(uuid).size() - 1).getItem() + " expiring at " + cooldownList.get(uuid).get(cooldownList.get(uuid).size() - 1).getExpiration());
            }
        }
    }
    public static boolean useable(UUID uuid, CoolDownItems item){
        if(debug) {
            getLogger().log(Level.INFO, "Looking for " + item + " by " + uuid);
        }
        if(cooldownList.containsKey(uuid)) {
            for (Cooldown cd : cooldownList.get(uuid)) {
                if (cd.getItem().equals(item) && cd.getExpiration() < System.currentTimeMillis()) {
                    getLogger().log(Level.INFO, "Ready to use, current time is " + System.currentTimeMillis() + " expired at " + cd.getExpiration() + ", " + Math.abs((System.currentTimeMillis() - cd.getExpiration()))/1000 + " ago");
                    return true;
                }else{
                    if(debug){
                        getLogger().log(Level.INFO, "On cooldown, current time is " + System.currentTimeMillis() + " expiring at " + cd.getExpiration() + " with " + (System.currentTimeMillis() - cd.getExpiration()) + " left");
                    }
                    return false;
                }
            }
        }
        return true;
    }
}


