package me.phastixtv.phallysystemvelocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.util.ConstantsGER;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentIteratorType;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.w3c.dom.html.HTMLAreaElement;

import java.util.*;
import java.util.stream.Collectors;

public class KickCommand implements SimpleCommand {

    private final ProxyServer proxyServer;
    public KickCommand(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        if (sender instanceof Player) {
            if (sender.hasPermission("phally.cmd.kick"))
                if (args.length == 1) {
                    Optional<Player> target = proxyServer.getPlayer(args[0]);
                    target.get().disconnect(ConstantsGER.kickMessage);
                    return;
                } else if (args.length <= 2) {

                }
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        if (invocation.arguments().length == 1) {
            return proxyServer.getAllPlayers()
                    .stream()
                    .map(player -> player.getUsername())
                    .filter(s -> s.startsWith(invocation.arguments()[0])).collect(Collectors.toList());
            }
        return Collections.emptyList();
    }
}
