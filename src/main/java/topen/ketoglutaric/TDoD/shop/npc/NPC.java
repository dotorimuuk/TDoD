package topen.ketoglutaric.TDoD.shop.npc;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPC {

    private final ProtocolManager protocolManager;
    private final UUID uuid;
    private final int entityId;
    private final String name;
    private final Location location;


    public NPC(
            ProtocolManager protocolManager,
            UUID uuid,
            int entityId,
            String name,
            Location location) {
        this.protocolManager = protocolManager;
        this.uuid = uuid;
        this.entityId = entityId;
        this.name = name;
        this.location = location;
    }

    public void spawn(Player player) {
        EntityInfoUpdate updateInfo = new EntityInfoUpdate(protocolManager, uuid);
        EntitySpawn spawnEntity = new EntitySpawn(protocolManager, uuid, entityId);
        EntityMetadata metadata = new EntityMetadata(protocolManager);

        updateInfo.playerInfoUpdate(player, name);
        spawnEntity.spawnEntity(player, location);
        metadata.sendMeta(player, entityId);
    }

}