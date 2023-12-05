package me.phastixtv.phallysystemvelocity.util;

import com.velocitypowered.api.proxy.Player;
import me.phastixtv.phallysystemvelocity.PhallySystemVelocity;

import java.util.UUID;

public class UUIDConverter {

    private final PhallySystemVelocity plugin;

    public UUIDConverter(PhallySystemVelocity plugin) {

        this.plugin = plugin;
    }

    public String getUsername(UUID uuid) {
        Player player = plugin.getServer().getPlayer(uuid).orElse(null);
        if (player != null) {
            return player.getUsername();
        }
        return null;
    }
}
