package topen.ketoglutaric.TDoD.shop.npc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final NPCManager manager;
    public PlayerJoin(NPCManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        for (NPC values : manager.getNpcMap().values()) {
            values.spawn(event.getPlayer());
        }

    }

}