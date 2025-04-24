package me.deeent.staffmonitor.bungeecord.utils;

import lombok.experimental.UtilityClass;
import me.deeent.staffmonitor.bungeecord.BungeeStaff;

@UtilityClass
public class S {

    public void runAsync(Runnable runnable) {
        BungeeStaff.getInstance().getProxy().getScheduler().runAsync(BungeeStaff.getInstance(), runnable);
    }

}
