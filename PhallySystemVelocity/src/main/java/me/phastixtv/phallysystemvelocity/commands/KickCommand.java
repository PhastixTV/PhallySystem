package me.phastixtv.phallysystemvelocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.PhallySystemVelocity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KickCommand implements SimpleCommand {

    private final PhallySystemVelocity plugin;
    private final ProxyServer proxyServer;

    public KickCommand(PhallySystemVelocity plugin, ProxyServer proxyServer) {
        this.plugin = plugin;
        this.proxyServer = proxyServer;
    }

    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();
        if (!invocation.source().hasPermission("phally.cmd.kick")) {
            return;
        }

        Optional<Player> target = proxyServer.getPlayer(args[0]);
        if (args.length == 1) {
            if (target.isPresent()) {
                target.get().disconnect(Component.text("Du wurdest gekickt", NamedTextColor.RED));
            }

        } else if (args.length >= 2) {
            if (target.isPresent()) {
                String kickReason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                target.get().disconnect(Component.text(kickReason, NamedTextColor.RED));
            }
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        if (invocation.arguments().length == 1) {
            if (invocation.source().hasPermission("phally.cmd.kick")) {
                return proxyServer.getAllPlayers()
                        .stream()
                        .map(player -> player.getUsername())
                        .filter(s -> s.startsWith(invocation.arguments()[0])).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
