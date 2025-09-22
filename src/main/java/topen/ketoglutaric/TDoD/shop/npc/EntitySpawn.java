package topen.ketoglutaric.TDoD.shop.npc;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

public class EntitySpawn {
    private final ProtocolManager protocolManager;
    private final UUID uuid;
    private final int entityId;

    public EntitySpawn(
        ProtocolManager protocolManager,
        UUID uuid,
        int entityId) {
            this.protocolManager = protocolManager;
            this.uuid = uuid;
            this.entityId = entityId;
    }

    public void spawnEntity(Player player, Location location) {
        
        PacketContainer npc = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        npc.getIntegers()
                .write(0, entityId) //the second value it's entity id, it must be unique!
                .writeSafely(1, 122); //the second value it's same entity id, but it uses for only spawn entity

        npc.getUUIDs()
                .write(0, uuid); //uuid must be like uuid in player info packet!

        npc.getEntityTypeModifier()
                .writeSafely(0, EntityType.PLAYER); //Entity Type, nothing complicated.

        npc.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ()); //Spawn location

        npc.getBytes()
                .write(0, (byte) location.getYaw())
                .write(1, (byte) location.getPitch()); //rotate location

        /*
        In the first method arguments, we need to put player to who that send packet
        In the second method arguments, we need to put packet that sends.
         */
        protocolManager.sendServerPacket(player, npc);
    }
}
