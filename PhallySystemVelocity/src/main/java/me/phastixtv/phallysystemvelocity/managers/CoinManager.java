package me.phastixtv.phallysystemvelocity.managers;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import me.phastixtv.phallysystemvelocity.PhallySystemVelocity;
import me.phastixtv.phallysystemvelocity.api.ICoinAPI;
import me.phastixtv.phallysystemvelocity.database.MySQLDataType;
import me.phastixtv.phallysystemvelocity.database.MySQLTabel;
import me.phastixtv.phallysystemvelocity.database.PlayerTabel;

import java.util.HashMap;
import java.util.UUID;

public class CoinManager implements ICoinAPI {

    private static MySQLTabel tabel;
    PlayerTabel playerTabel;

    @Subscribe
    public void onPlayerJoin(PostLoginEvent event) {
        MySQLTabel.Condition condition = new MySQLTabel.Condition("uuid", event.getPlayer().getUniqueId().toString());
        if (!tabel.exists(condition)) {
            set(event.getPlayer().getUniqueId(), 1000);
        }
    }

    public CoinManager(PhallySystemVelocity plugin) {
        this.playerTabel = new PlayerTabel(plugin);
        HashMap<String, MySQLDataType> colums = playerTabel.getColums();
        tabel = new MySQLTabel(plugin.getConnection(), "player", colums);
    }
    @Override
    public void set(UUID uuid, int value) {
        MySQLTabel.Condition condition = new MySQLTabel.Condition("uuid", uuid.toString());
        if(tabel.exists(condition)) {
            tabel.set("coins", value, condition);
        } else {
            tabel.set("uuid", uuid.toString(), condition);
            tabel.set("coins", value, condition);
        }
    }

    @Override
    public int get(UUID uuid) {
        MySQLTabel.Condition condition = new MySQLTabel.Condition("uuid", uuid.toString());
        if (tabel.exists(condition)) {
            return tabel.getInt("coins", condition);
        }
        set(uuid, 0);
        return 0;
    }
}
