package me.deeent.staffmonitor.commons.managers;

import com.google.common.collect.Maps;
import lombok.Getter;
import me.deeent.staffmonitor.commons.storage.Staff;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public abstract class StaffManager {

    @Getter private final Map<UUID, Staff> staffs = Maps.newHashMap();

    public Staff getStaff(UUID uuid) {
        return staffs.get(uuid);
    }

    public int getStaffCount() {
        return staffs.size();
    }

    public boolean isStaff(UUID uuid) {
        return staffs.containsKey(uuid);
    }

    public abstract void addStaff(Object player);
    public abstract void removeStaff(Object player);

    public abstract void saveAll();

}
