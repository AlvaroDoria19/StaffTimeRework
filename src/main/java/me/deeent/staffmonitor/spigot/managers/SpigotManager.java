package me.deeent.staffmonitor.spigot.managers;

import lombok.AllArgsConstructor;
import me.deeent.staffmonitor.commons.managers.StaffManager;
import me.deeent.staffmonitor.commons.storage.Staff;
import me.deeent.staffmonitor.commons.utils.EmbedUtils;
import me.deeent.staffmonitor.spigot.SpigotStaff;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class SpigotManager extends StaffManager {

    private final SpigotStaff plugin;

    @Override
    public void addStaff(Object object) {
        if (!(object instanceof Player))
            return;

        Player player = (Player) object;
        Staff staff = new Staff(player.getUniqueId(), plugin.getStorage().getStaffActivity(player.getUniqueId()));

        getStaffs().put(player.getUniqueId(), staff);

        if (plugin.getEmbedUtils().isEmbedEnabled(EmbedUtils.EmbedType.JOIN))
            plugin.getWebhook().sendEmbed(player, staff, EmbedUtils.EmbedType.JOIN);
    }

    @Override
    public void removeStaff(Object object) {
        if (!(object instanceof Player))
            return;

        Player player = (Player) object;
        Staff staff = getStaffs().get(player.getUniqueId());

        getStaffs().remove(player.getUniqueId());

        staff.setLeave(System.currentTimeMillis());
        plugin.getStorage().saveStaffActivity(player.getUniqueId(), staff.getAllActivity());

        if (plugin.getEmbedUtils().isEmbedEnabled(EmbedUtils.EmbedType.LEAVE))
            plugin.getWebhook().sendEmbed(player, staff, EmbedUtils.EmbedType.LEAVE);
    }

    @Override
    public void saveAll() {
        getStaffs().forEach((uuid, staff) -> {
            staff.setLeave(System.currentTimeMillis());
            plugin.getStorage().saveStaffActivity(uuid, staff.getAllActivity());
        });
    }

}
