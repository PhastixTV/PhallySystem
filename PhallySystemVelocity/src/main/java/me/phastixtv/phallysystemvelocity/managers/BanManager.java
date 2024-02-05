package me.phastixtv.phallysystemvelocity.managers;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import me.phastixtv.phallysystemvelocity.PhallySystemVelocity;
import me.phastixtv.phallysystemvelocity.database.MySQLDataType;
import me.phastixtv.phallysystemvelocity.database.MySQLTabel;
import me.phastixtv.phallysystemvelocity.util.ConstantsGER;
import me.phastixtv.phallysystemvelocity.util.UUIDConverter;

import java.util.HashMap;
import java.util.UUID;

public class BanManager {
    private static MySQLTabel tabel;
    public HashMap<String, MySQLDataType> colums = new HashMap<>();
    private static UUIDConverter uuidConverter;
    static ProxyServer proxyServer;

    public BanManager(PhallySystemVelocity plugin) {
        HashMap<String, MySQLDataType> colums = getColums();
        colums.put("uuid", MySQLDataType.CHAR);
        colums.put("player", MySQLDataType.CHAR);
        tabel = new MySQLTabel(plugin.getConnection(), "banned-players", colums);
        uuidConverter = new UUIDConverter(plugin);
    }

    @Subscribe
    public void onPlayerJoin(PostLoginEvent event) {
        MySQLTabel.Condition condition = new MySQLTabel.Condition("uuid", event.getPlayer().getUniqueId().toString());
        if (tabel.exists(condition)) {
            proxyServer.getPlayer(event.getPlayer().getUniqueId()).get().disconnect(ConstantsGER.noPerm);
        }
    }

    public static void set(UUID uuid) {
        MySQLTabel.Condition condition = new MySQLTabel.Condition("uuid", uuid.toString());
        if(tabel.exists(condition)) {
            tabel.set("player", uuidConverter.getUsername(uuid) , condition);
        } else {
            tabel.set("uuid", uuid.toString(), condition);
            tabel.set("player", uuidConverter.getUsername(uuid), condition);
        }
    }


    public static int get(UUID uuid) {
        MySQLTabel.Condition condition = new MySQLTabel.Condition("uuid", uuid.toString());
        if (tabel.exists(condition)) {
            return tabel.getInt("player", condition);
        }
        set(uuid);
        return 0;
    }

    public HashMap<String, MySQLDataType> getColums() {
        return colums;
    }

}

