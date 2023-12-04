package me.phastixtv.phallysystemvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.api.CoinAPI;
import me.phastixtv.phallysystemvelocity.database.MySQLConnection;
import me.phastixtv.phallysystemvelocity.database.PlayerTabel;
import me.phastixtv.phallysystemvelocity.managers.CoinManager;
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

    private final MySQLConnection connection;
    PlayerTabel playerTabel;
    CoinManager coinManager;


    private final Logger logger;
    private final ProxyServer server;

    private final Component prefix = MiniMessage.miniMessage().deserialize("<light_purple><bold>Phally</bold> <grey>| <reset>");
    private final Component noPerm = MiniMessage.miniMessage().deserialize(prefix + "<red>Dazu hast du keine Rechte!");


    @Inject
    public PhallySystemVelocity(MySQLConnection connection, Logger logger, ProxyServer server) {
        this.connection = connection;
        this.logger = logger;
        this.server = server;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        connection.connect("localhost", "3306", "minecraft", "minecraft", "minecraft");
        playerTabel = new PlayerTabel(this);
        coinManager = new CoinManager(this);

        server.getEventManager().register(this, coinManager);
        server.getEventManager().register(this, playerTabel);

        CoinAPI.setApi(coinManager);

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
}
