package me.phastixtv.phallysystemvelocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.PhallySystemVelocity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.net.Inet4Address;
import java.util.*;
import java.util.stream.Collectors;

public class BanCommand implements SimpleCommand {

    String permission = "phally.cmd.ban";
    public static Map<UUID, String> bannedUUIDList;
    public static Set<String> bannedIpList;
    private final PhallySystemVelocity plugin;
    private final ProxyServer server;

    public BanCommand(PhallySystemVelocity plugin, ProxyServer server) {
        bannedUUIDList = new HashMap<>();
        bannedIpList = new HashSet<>();
        this.plugin = plugin;
        this.server = server;
    }

    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();
        String banReasonMessage = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        if (args.length >=2) {
            Optional<Player> target = server.getPlayer(args[0]);
            UUID targetID = target.get().getUniqueId();
            String targetIp = target.get().getRemoteAddress().getAddress().getHostAddress();
            if (target.isPresent()) {
                if (!bannedUUIDList.containsKey(targetID)) {
                    bannedUUIDList.put(targetID, banReasonMessage);
                    bannedIpList.add(targetIp);
                    target.get().disconnect(Component.text(banReasonMessage, NamedTextColor.RED));
                }
            }
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        if (invocation.arguments().length == 1) {
            if (invocation.source().hasPermission("phally.cmd.ban")) {
                return server.getAllPlayers()
                        .stream()
                        .map(player -> player.getUsername())
                        .filter(s -> s.startsWith(invocation.arguments()[0])).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
