package me.phastixtv.phallysystemvelocity.managers;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import me.phastixtv.phallysystemvelocity.PhallySystemVelocity;
import me.phastixtv.phallysystemvelocity.api.ICoinAPI;
import me.phastixtv.phallysystemvelocity.database.MySQLDataType;
import me.phastixtv.phallysystemvelocity.database.MySQLTabel;
import me.phastixtv.phallysystemvelocity.util.UUIDConverter;

import java.util.HashMap;
import java.util.UUID;

public class CoinManager implements ICoinAPI {

    private static MySQLTabel tabel;
    public HashMap<String, MySQLDataType> colums = new HashMap<>();
    private UUIDConverter uuidConverter;

    @Subscribe
    public void onPlayerJoin(PostLoginEvent event) {
        MySQLTabel.Condition condition = new MySQLTabel.Condition("uuid", event.getPlayer().getUniqueId().toString());
        if (!tabel.exists(condition)) {
            set(event.getPlayer().getUniqueId(), 1000);
        }
    }

    public CoinManager(PhallySystemVelocity plugin) {
        HashMap<String, MySQLDataType> colums = getColums();
        colums.put("uuid", MySQLDataType.CHAR);
        colums.put("player", MySQLDataType.CHAR);
        colums.put("coins", MySQLDataType.INT);

        tabel = new MySQLTabel(plugin.getConnection(), "coins", colums);
        uuidConverter = new UUIDConverter(plugin);
    }
    @Override
    public void set(UUID uuid, int value) {
        MySQLTabel.Condition condition = new MySQLTabel.Condition("uuid", uuid.toString());
        if(tabel.exists(condition)) {
            tabel.set("coins", value, condition);
            tabel.set("player", uuidConverter.getUsername(uuid) , condition);
        } else
            tabel.set("uuid", uuid.toString(), condition);
            tabel.set("player", uuidConverter.getUsername(uuid) , condition);
            tabel.set("coins", value, condition);
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

    public HashMap<String, MySQLDataType> getColums() {
        return colums;
    }

}
