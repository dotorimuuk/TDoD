package topen.ketoglutaric.TDoD.shop.npc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EntityMetadata {

    ProtocolManager protocolManager;

    public EntityMetadata(ProtocolManager protocolManager) {
        this.protocolManager = protocolManager;
    }

    /**
     * Sends an EntityMetadata packet to a player.
     * This example sets the entity on fire.
     * @param player The player to send the packet to.
     * @param entityId The ID of the entity to modify.
     */
    public void sendMeta(Player player, int entityId){
        var packetContainer = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packetContainer.getIntegers().write(0, entityId);

        List<WrappedDataValue> wrappedDataValues = new ArrayList<>();
        var byteSerializer = WrappedDataWatcher.Registry.get((Type) Byte.class);

        wrappedDataValues.add(new WrappedDataValue(17, byteSerializer, (byte) 127));
        packetContainer.getDataValueCollectionModifier().write(0, wrappedDataValues);

        protocolManager.sendServerPacket(player, packetContainer);
    }

}