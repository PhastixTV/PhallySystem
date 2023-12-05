package me.phastixtv.phallysystemvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.api.CoinAPI;
import me.phastixtv.phallysystemvelocity.database.MySQLConnection;
import me.phastixtv.phallysystemvelocity.database.MySQLDriver;
import me.phastixtv.phallysystemvelocity.events.ConnectionEvent;
import me.phastixtv.phallysystemvelocity.managers.CoinManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.nio.file.Paths;

@Plugin(
        id = "phallysystemvelocity",
        name = "PhallySystemVelocity",
        version = "1.0"
)
public class PhallySystemVelocity {

    MySQLConnection connection;
    CoinManager coinManager;


    private final Logger logger;
    private final ProxyServer server;

    private final Component prefix = MiniMessage.miniMessage().deserialize("<light_purple><bold>Phally</bold> <grey>| <reset>");
    private final Component noPerm = MiniMessage.miniMessage().deserialize(prefix + "<red>Dazu hast du keine Rechte!");


    @Inject
    public PhallySystemVelocity(Logger logger, ProxyServer server) {
        this.logger = logger;
        this.server = server;

        try {
            new MySQLDriver(Paths.get("plugins/FriendSystem/driver"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        connection = new MySQLConnection("localhost", 3306, "minecraft", "minecraft", "minecraft");
        coinManager = new CoinManager(this);

        CoinAPI.setApi(coinManager);

    server.getEventManager().register(this, coinManager);
    server.getEventManager().register(this, new ConnectionEvent());
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        connection.disconnect();
    }

    public @NotNull Component getNoPerm() {
        return noPerm;
    }

    public @NotNull Component getPrefix() {
        return prefix;
    }

    public MySQLConnection getConnection() {
        return connection;
    }

    public ProxyServer getServer() {
        return server;
    }
}
