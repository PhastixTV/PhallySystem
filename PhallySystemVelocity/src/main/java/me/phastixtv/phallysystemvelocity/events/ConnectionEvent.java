package me.phastixtv.phallysystemvelocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.PhallySystemVelocity;
import me.phastixtv.phallysystemvelocity.database.MySQLTabel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.*;

public class ConnectionEvent {

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Component joinMessageTest = MiniMessage.miniMessage().deserialize("<red>" + event.getPlayer().getUsername()
                        + "</red><green> hat den Server betreten");
        event.getPlayer().sendMessage(joinMessageTest);
    }
}
