package me.phastixtv.phallysystemvelocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.managers.CoinManager;
import me.phastixtv.phallysystemvelocity.util.ConstantsGER;
import me.phastixtv.phallysystemvelocity.util.IntegerVerifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CoinCommand implements SimpleCommand {

    private final ProxyServer server;
    private final ConstantsGER constantsGER = new ConstantsGER();

    public CoinCommand(ProxyServer server) {
        this.server = server;
    }


    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();
        CommandSource sender = invocation.source();
        Player player = (Player) invocation.source();
        Optional<Player> target = null;
        if (args.length == 0) {
            getCoin(sender, player);
            return;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(invocation);
            }
        }
        if (args.length == 3) {
            target = server.getPlayer(args[1]);
            if (args[0].equalsIgnoreCase("pay")) {
                if (target != null) {
                    if (!IntegerVerifier.isInt(args[2])) {
                        Component isNotIntMessage = MiniMessage.miniMessage().deserialize(constantsGER.coinPrefix  + "<red>Der eingegebene Wert " + args[2] + "ist keine gültige Nummer!");
                        player.sendMessage(isNotIntMessage);
                        return;
                    }
                    if (CoinManager.get(player.getUniqueId())-Integer.parseInt(args[2]) < 0) {
                        Component notEnoughtCoins = MiniMessage.miniMessage().deserialize(ConstantsGER.coinPrefix + "<red>Du hast nicht genügend Coins!");
                        player.sendMessage(notEnoughtCoins);
                        return;
                    }
                    payCoin(invocation, player, target, Integer.parseInt(args[2]));
                }
            }
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        if (invocation.arguments().length == 2) {
            return server.getAllPlayers()
                    .stream()
                    .map(player -> player.getUsername())
                    .filter(string -> string.startsWith(invocation.arguments()[0])).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private void getCoin(CommandSource sender, Player player) {
        if (sender.equals(player.getUsername())) {
            Component getCoinMSG = MiniMessage.miniMessage().deserialize("<green>Du hast " + CoinManager.get(player.getUniqueId())  + " Coins!");
            sender.sendMessage(getCoinMSG);
        } else {
            Component getCoinMSG = MiniMessage.miniMessage().deserialize("<green>Der Spieler " + player.getUsername() + " hat " + CoinManager.get(player.getUniqueId())  + " Coins!");
            sender.sendMessage(getCoinMSG);
        }
    }

    private void setCoin(Invocation invocation, Player target, int value) {
        CoinManager.set(target.getUniqueId(), value);
        Player sender = (Player) invocation.source();
        if (sender.getUsername().equalsIgnoreCase(target.getUsername())) {
            Component setPlayerCoinMSG = MiniMessage.miniMessage().deserialize("<green>Du hast nun " + value + " Coins");
            sender.sendMessage(setPlayerCoinMSG);
        } else {
            Component setPlayerCoinMSG = MiniMessage.miniMessage().deserialize("<green>Der Spieler " + sender.getUsername() + "hat nun " + value + " Geld.");
            Component setTargetCoinMSG = MiniMessage.miniMessage().deserialize("<green>Du hast nun " + value + " Coins");
            target.sendMessage(setTargetCoinMSG);
            sender.sendMessage(setPlayerCoinMSG);
        }
    }

    private void payCoin(Invocation invocation, Player from, Optional<Player> target, int value) {
        Player sender = (Player) invocation.source();
        if (sender.getUsername().equalsIgnoreCase(from.getUsername())) {
            Component payCoinSenderMSG = MiniMessage.miniMessage().deserialize(
                    "<green>Du hast " + target.get().getUsername() + " " + value + " gegeben!");
            Component payCoinTargerMSG = MiniMessage.miniMessage().deserialize(
                    "<green>Der Spieler " + from.getUsername() + " hat dir " + value + " gegeben!");
            sender.sendMessage(payCoinSenderMSG);
            target.get().sendMessage(payCoinTargerMSG);
            CoinManager.set(from.getUniqueId(), CoinManager.get(from.getUniqueId()) - value);
            CoinManager.set(target.get().getUniqueId(), CoinManager.get(target.get().getUniqueId()) + value);
        } else {
            Component payCoinSenderMSG = MiniMessage.miniMessage().deserialize(
                    "<green>Der Spieler " + from.getUsername() + " hat " + target.get().getUsername() + " " + value + " gegeben!");
            Component payCoinTargerMSG = MiniMessage.miniMessage().deserialize(
                    "<green>Der Spieler " + from.getUsername() + " hat dir " + value + " gegeben!");
            Component payCoinFromMSG = MiniMessage.miniMessage().deserialize(
                    "<green>Der Spieler " + from.getUsername() + " hat dir " + value + " gegeben!");
            sender.sendMessage(payCoinSenderMSG);
            target.get().sendMessage(payCoinTargerMSG);
            from.sendMessage(payCoinFromMSG);
            CoinManager.set(from.getUniqueId(), CoinManager.get(from.getUniqueId()) - value);
            CoinManager.set(target.get().getUniqueId(), CoinManager.get(target.get().getUniqueId()) + value);
        }
    }

    private void sendHelp(Invocation invocation) {

    }
}