package topen.ketoglutaric.TDoD.shop.npc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

import com.comphenix.protocol.ProtocolManager;

public class NPCManager {
    private final ProtocolManager manager;
    private int entityId = 0;
    private Map<Integer, NPC> npcMap = new HashMap<>();

    public NPCManager(ProtocolManager manager) {
        this.manager = manager;
    }

    public int createNPCPlayer(String display, String skin, Location location) {
        NPC npc = new NPC(manager, UUID.randomUUID(), entityId, display, skin, location);
        npcMap.put(entityId, npc);
        return entityId++;
    }

    public void deleteNPC(int id) {
        npcMap.remove(id);
    }

    public Map<Integer, NPC> getNpcMap() {
        return npcMap;
    }
}
