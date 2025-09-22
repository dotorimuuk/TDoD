package topen.ketoglutaric.TDoD;

import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import topen.ketoglutaric.TDoD.shop.npc.NPCCommand;
import topen.ketoglutaric.TDoD.shop.npc.NPCManager;

public final class TDoD extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("npc").setExecutor(new NPCCommand(new NPCManager(ProtocolLibrary.getProtocolManager())));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
