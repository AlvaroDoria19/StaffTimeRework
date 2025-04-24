package me.deeent.staffmonitor.bungeecord.managers;

import lombok.AllArgsConstructor;
import me.deeent.staffmonitor.bungeecord.BungeeStaff;
import me.deeent.staffmonitor.commons.managers.StaffManager;
import me.deeent.staffmonitor.commons.storage.Staff;
import me.deeent.staffmonitor.commons.utils.EmbedUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@AllArgsConstructor
public class BungeeManager extends StaffManager {

    private final BungeeStaff plugin;

    @Override
    public void addStaff(Object object) {
        if (!(object instanceof ProxiedPlayer))
            return;

        ProxiedPlayer player = (ProxiedPlayer) object;
        Staff staff = new Staff(player.getUniqueId(), plugin.getStorage().getStaffActivity(player.getUniqueId()));

        getStaffs().put(player.getUniqueId(), staff);

        if (plugin.getEmbedUtils().isEmbedEnabled(EmbedUtils.EmbedType.JOIN))
            plugin.getWebhook().sendEmbed(player, staff, EmbedUtils.EmbedType.JOIN);
    }

    @Override
    public void removeStaff(Object object) {
        if (!(object instanceof ProxiedPlayer))
            return;

        ProxiedPlayer player = (ProxiedPlayer) object;
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
