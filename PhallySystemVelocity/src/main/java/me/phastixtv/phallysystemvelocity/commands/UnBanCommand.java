package me.phastixtv.phallysystemvelocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.PhallySystemVelocity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.*;

public class UnBanCommand implements SimpleCommand {

    private final ProxyServer proxyServer;
    private final PhallySystemVelocity plugin;

    public UnBanCommand(ProxyServer proxyServer, PhallySystemVelocity plugin) {
        this.proxyServer = proxyServer;
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();

        if (args.length == 1) {
            Optional<Player> target = proxyServer.getPlayer(args[0]);
            UUID targetID = target.get().getUniqueId();
            String targetIp = target.get().getRemoteAddress().getAddress().getHostAddress();
            Component unBanMessage = MiniMessage.miniMessage().deserialize("<green>Der Spieler wurde entbannt!");
                if (BanCommand.bannedUUIDList.containsKey(targetID) || BanCommand.bannedIpList.contains(targetIp)) {
                    BanCommand.bannedUUIDList.remove(targetID);
                    BanCommand.bannedIpList.remove(targetIp);
                    invocation.source().sendMessage(unBanMessage);
            }
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return SimpleCommand.super.suggest(invocation);
    }
}
