package me.phastixtv.phallysystemvelocity.database;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import me.phastixtv.phallysystemvelocity.PhallySystemVelocity;

import java.util.HashMap;
import java.util.UUID;

public class PlayerTabel {

    private static MySQLTabel tabel;
    public HashMap<String, MySQLDataType> colums = new HashMap<>();

    @Subscribe
    public void onPlayerJoin(PostLoginEvent event) {
        MySQLTabel.Condition condition = new MySQLTabel.Condition("uuid", event.getPlayer().getUniqueId().toString());
        if (!tabel.exists(condition)) {
            set(event.getPlayer().getUniqueId(), event.getPlayer().getUsername());
        }
    }

    public PlayerTabel(PhallySystemVelocity plugin) {
        HashMap<String, MySQLDataType> colums = getColums();
        colums.put("uuid", MySQLDataType.CHAR);
        colums.put("player", MySQLDataType.CHAR);
        colums.put("coins", MySQLDataType.INT);

        tabel = new MySQLTabel(plugin.getConnection(), "player", colums);
    }

    public void set(UUID uuid, String player) {
        MySQLTabel.Condition condition = new MySQLTabel.Condition("uuid", uuid.toString());
        if(tabel.exists(condition)) {
            tabel.set("player", player, condition);
        } else {
            tabel.set("uuid", uuid.toString(), condition);
            tabel.set("player", player, condition);
        }
    }

    public HashMap<String, MySQLDataType> getColums() {
        return colums;
    }
}
