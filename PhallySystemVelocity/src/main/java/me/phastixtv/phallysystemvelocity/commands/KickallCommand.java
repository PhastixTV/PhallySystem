package me.phastixtv.phallysystemvelocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import me.phastixtv.phallysystemvelocity.PhallySystemVelocity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.*;
import java.util.stream.Collectors;

public class KickallCommand implements SimpleCommand {

    private final String permission = "phally.cmd.kickall";
    private final ProxyServer proxyServer;
    private final PhallySystemVelocity plugin;

    public KickallCommand(ProxyServer proxyServer, PhallySystemVelocity plugin) {
        this.proxyServer = proxyServer;
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        Player player = (Player) invocation.source();

        Component kickMessage = MiniMessage.miniMessage().deserialize("<red>Du wurdest gekickt!");
        if (invocation.arguments().length != 1) {
            proxyServer.getAllPlayers().forEach(playerKick -> playerKick.disconnect(kickMessage));
            return;
        }

        Optional<RegisteredServer> server = proxyServer.getServer(invocation.arguments()[0]);

        if (server.isEmpty()) {
            invocation.source().sendMessage(Component.text("Server nicht gefunden", NamedTextColor.RED));
            return;
        }

        server.get().getPlayersConnected().forEach(playerKick -> playerKick.disconnect(
                Component.text("Du wurdest gekickt!", NamedTextColor.RED)
        ));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        if (invocation.arguments().length == 1) {
            if (invocation.source().hasPermission("phally.cmd.kickall")) {
                return proxyServer.getAllServers()
                        .stream()
                        .map(server -> server.getServerInfo().getName())
                        .filter(s -> s.startsWith(invocation.arguments()[0])).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
