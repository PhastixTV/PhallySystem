package me.phastixtv.phallysystemvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.commands.CoinCommand;
import me.phastixtv.phallysystemvelocity.commands.KickCommand;
import me.phastixtv.phallysystemvelocity.database.MySQLConnection;
import me.phastixtv.phallysystemvelocity.database.MySQLDriver;
import me.phastixtv.phallysystemvelocity.events.ConnectionEvent;
import me.phastixtv.phallysystemvelocity.managers.CoinManager;
import me.phastixtv.phallysystemvelocity.util.ConstantsGER;
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


    @Inject
    public PhallySystemVelocity(Logger logger, ProxyServer server) {
        this.logger = logger;
        this.server = server;

        try {
            new MySQLDriver(Paths.get("plugins/PhallySystem/driver"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        connection = new MySQLConnection("localhost", 3306, "minecraft", "minecraft", "minecraft");
        coinManager = new CoinManager(this);

        //CoinAPI.setApi(coinManager);
        CommandManager commandManager = server.getCommandManager();
        loadCommands(commandManager);

        server.getEventManager().register(this, coinManager);
        server.getEventManager().register(this, new ConnectionEvent());
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        connection.disconnect();
    }

    private void loadCommands(CommandManager commandManager) {
        commandManager.register(commandManager.metaBuilder("coin").build(), new CoinCommand(server));
        commandManager.register(commandManager.metaBuilder("kick").build(), new KickCommand(server));
    }

    private void startMessage() {
        logger.info(ConstantsGER.prefix + "Loading...");
        logger.info(" ___  _         _  _       ___             _               \n" +
                "| . \\| |_  ___ | || | _ _ / __> _ _  ___ _| |_  ___ ._ _ _ \n" +
                "|  _/| . |<_> || || || | |\\__ \\| | |<_-<  | |  / ._>| ' ' |\n" +
                "|_|  |_|_|<___||_||_|`_. |<___/`_. |/__/  |_|  \\___.|_|_|_|\n" +
                "                     <___'     <___'                       ");
        logger.info(ConstantsGER.prefix + "The system is ready to work!");
    }

    public MySQLConnection getConnection() {
        return connection;
    }

    public ProxyServer getServer() {
        return server;
    }
}
