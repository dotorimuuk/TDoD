package topen.ketoglutaric.TDoD.shop.npc;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPC {

    private final ProtocolManager protocolManager;
    private final UUID uuid;
    private final int entityId;
    private final String displayname;
    private final Location location;
    private final String skinPlayer;

    public int getEntityId() {
        return entityId;
    }

    public Location getLocation() {
        return location;
    }

    public String getDisplayName() {
        return displayname;
    }
    
    public UUID getUuid() {
        return uuid;
    }


    public NPC(
            ProtocolManager protocolManager,
            UUID uuid,
            int entityId,
            String displayname,
            String skinPlayer,
            Location location) {
        this.protocolManager = protocolManager;
        this.uuid = uuid;
        this.entityId = entityId;
        this.displayname = displayname;
        this.skinPlayer = skinPlayer;
        this.location = location;
    }

    public void spawn(Player player) {
        EntityInfoUpdate updateInfo = new EntityInfoUpdate(protocolManager, uuid);
        EntitySpawn spawnEntity = new EntitySpawn(protocolManager, uuid, entityId);
        EntityMetadata metadata = new EntityMetadata(protocolManager);

        updateInfo.playerInfoUpdate(player, displayname, skinPlayer);
        spawnEntity.spawnEntity(player, location);
        metadata.sendMeta(player, entityId);
    }

}