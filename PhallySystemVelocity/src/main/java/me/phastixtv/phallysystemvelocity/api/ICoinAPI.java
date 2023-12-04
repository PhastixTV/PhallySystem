package me.phastixtv.phallysystemvelocity.api;

import com.velocitypowered.api.proxy.Player;

import java.util.UUID;

public interface ICoinAPI {

    void set(UUID uuid, int value);
    int get(UUID uuid);
}
