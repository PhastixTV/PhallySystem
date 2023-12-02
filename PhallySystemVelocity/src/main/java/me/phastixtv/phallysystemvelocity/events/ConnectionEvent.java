package me.phastixtv.phallysystemvelocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.PhallySystemVelocity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.security.Key;
import java.util.*;

public class ConnectionEvent {

    private final ProxyServer proxyServer;
    private final PhallySystemVelocity plugin;

    public ConnectionEvent(ProxyServer proxyServer, PhallySystemVelocity plugin) {
        this.plugin = plugin;
        this.proxyServer = proxyServer;
    }

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Component joinMessageTest = MiniMessage.miniMessage().deserialize("<red>" + event.getPlayer().getUsername()
                + "</red><green> hat den Server betreten");


    }

    @Subscribe
    public void onPlayerJoin(PlayerChooseInitialServerEvent event) {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();
        String playerIP = player.getRemoteAddress().getAddress().getHostAddress();
        String banReason = BanCommand.bannedUUIDList.get(playerID);

        if (BanCommand.bannedUUIDList == null || BanCommand.bannedIpList == null) return;
        if (BanCommand.bannedUUIDList.containsKey(playerID) ||
                BanCommand.bannedIpList.contains(playerIP)) {
            player.disconnect(Component.text(banReason, NamedTextColor.RED));
        }
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        Component disconnectMessage = MiniMessage.miniMessage().deserialize("<red>" + event.getPlayer().getUsername()
                + "</red><green> hat den Server verlassen");
        proxyServer.sendMessage(disconnectMessage); 
    }
}
