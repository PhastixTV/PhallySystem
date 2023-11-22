package me.phastixtv.phallysystemvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.commands.BanCommand;
import me.phastixtv.phallysystemvelocity.commands.KickCommand;
import me.phastixtv.phallysystemvelocity.commands.KickallCommand;
import me.phastixtv.phallysystemvelocity.events.ConnectionEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Plugin(
        id = "phallysystemvelocity",
        name = "PhallySystemVelocity",
        version = "1.0"
)
public class PhallySystemVelocity {

    private final Logger logger;
    private final ProxyServer server;

    private final Component prefix = MiniMessage.miniMessage().deserialize("<light_purple><bold>Phally</bold> <grey>| <reset>");
    private final Component noPerm = MiniMessage.miniMessage().deserialize(prefix + "<red>Dazu hast du keine Rechte!");


    @Inject
    public PhallySystemVelocity(Logger logger, ProxyServer server) {
        this.logger = logger;
        this.server = server;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getEventManager().register(this, new ConnectionEvent(server, this));
        CommandManager commandManager = server.getCommandManager();
        commandManager.register(commandManager.metaBuilder("kickall").build(), new KickallCommand(server, this));
        commandManager.register(commandManager.metaBuilder("kick").build(), new KickCommand(this, server));
        commandManager.register(commandManager.metaBuilder("ban").build(), new BanCommand(this, server));
        commandManager.register(commandManager.metaBuilder("unban").build(), new BanCommand(this, server));
    }

    public @NotNull Component getNoPerm() {
        return noPerm;
    }

    public @NotNull Component getPrefix() {
        return prefix;
    }
}
