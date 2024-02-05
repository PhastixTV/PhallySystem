package me.phastixtv.phallysystemvelocity.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ConstantsGER {

    String[] args;

    public static final String prefix = "<light_purple><bold>Phally</bold> <grey>| <reset>",
    banPrefix = "<light_purple><bold>Phally<red>Ban</bold> <grey>| <reset>",
    coinPrefix = "<light_purple><bold>Phally<green>Coins</bold> <grey>| <reset>",
    noPermString = prefix + "<red>Dazu hast du keine Rechte!";

    public static final Component noPerm = MiniMessage.miniMessage().deserialize(noPermString),
    kickMessage = MiniMessage.miniMessage().deserialize(banPrefix + "<red>Du wurdest gekickt!");

}
