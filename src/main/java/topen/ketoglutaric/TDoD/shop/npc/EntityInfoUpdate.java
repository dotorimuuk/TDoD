package topen.ketoglutaric.TDoD.shop.npc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EntityInfoUpdate {
    
    private final ProtocolManager protocolManager;
    private final UUID uuid;

    public EntityInfoUpdate(ProtocolManager protocolManager, UUID uuid) {
        this.protocolManager = protocolManager;
        this.uuid = uuid;
    }

    public void playerInfoUpdate(Player player, String nameString, String skiString) {

        PacketContainer npc = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
        Set<EnumWrappers.PlayerInfoAction> playerInfoActionSet = new HashSet<>();

        WrappedGameProfile wrappedGameProfile = new WrappedGameProfile(uuid, nameString);
        WrappedSignedProperty property = getProperty(skiString);
        wrappedGameProfile.getProperties().clear();
        wrappedGameProfile.getProperties().put("textures", property);

        PlayerInfoData playerInfoData = new PlayerInfoData(
            wrappedGameProfile, 
            0, 
            EnumWrappers.NativeGameMode.CREATIVE, 
            WrappedChatComponent.fromText(nameString));
        List<PlayerInfoData> playerInfoDataList = Arrays.asList(playerInfoData);

        playerInfoActionSet.add(EnumWrappers.PlayerInfoAction.ADD_PLAYER);

        npc.getPlayerInfoActions()
                .write(0, playerInfoActionSet);
        
        npc.getPlayerInfoDataLists().write(1, playerInfoDataList);

        protocolManager.sendServerPacket(player, npc);
    }

    private WrappedSignedProperty getProperty(String name){
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        UUID uniqueId = player.getUniqueId();

        try {
            URL url = new URI(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", uniqueId.toString())).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null)  {
                stringBuffer.append(inputLine);
            }
            bufferedReader.close();

            String response = stringBuffer.toString();
            JsonObject object = JsonParser.parseString(response).getAsJsonObject();
            JsonObject properties = object.get("properties").getAsJsonArray().get(0).getAsJsonObject();
            return new WrappedSignedProperty(
                properties.get("name").getAsString(), 
                properties.get("value").getAsString(), 
                properties.get("signature").getAsString());

        } catch (IOException | URISyntaxException e) {
            return new WrappedSignedProperty("textures", 
            "ewogICJ0aW1lc3RhbXAiIDogMTc1ODUwNTc2MzYxMSwKICAicHJvZmlsZUlkIiA6ICI4MWU0OTUxZmUxZWU0ZGVlOTcxZDY4NjEyOTQwNWZmNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJ0b3BlbjAzMzAiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzBmOTcxOWYxMWU0YWI0MDZkNGVjNDFjZjJhOGM2N2Y5NjQ0NzA5NTVjMWZiOThjMzUwNGJiMTM0ZGJiOGMwZiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9LAogICAgIkNBUEUiIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzIzNDBjMGUwM2RkMjRhMTFiMTVhOGIzM2MyYTdlOWUzMmFiYjIwNTFiMjQ4MWQwYmE3ZGVmZDYzNWNhN2E5MzMiCiAgICB9CiAgfQp9", 
            "rANicuDTp3Nnwg8QkVvvQjmiGT89eBxlJlMkoHW1sS/IMqJIbRmp092/JZWeL9bOtCwAoM6AyRF2cnMBCcBM4otQKobPSt8rfoMycdIBIsPKtfG4q8iJQ+ZnqUI913cdupvTX/93k/0PZElTnS8Q5tSTvrcfUR1n4/aj7bligCx2bGFF1Qs7MljkwXJJGCf/9iAwWv62eGBR8OIxayw+Gre0P3UCOIIODrrURi2dhEREeCCavf2I07BhkGUJY3qtn8GzG7qtIiQ1IrjC3uEnfqWVFClebKwIu7V+Jpbb94wgwv77vKhkIU+5lsuadbBn04R00ulVwDEHgQqWJx8GvzqtD8+emJZVwQPhSDQ5QQ9BvMujBtEnnvHFfmV7cL7iTYL7QXf19OaWihQJporCtyOMRIxODD7zgKmCiS9PQ73HSO9vBW0P2p+xzm/ImsLeykOwDSMvh2x6y7iwA7R78V7TwOtHWbaR58KtO2lj2Qxm+4+6CfJXmv3iIyEEVDXyoYUbUYVPeU9f1Pgbo0eUpK15Y0R8PgYx0s5NAi7IHS798tAg+iVj9GAPFD3es+DneJq4VLqiPBblJbTThJ2ZwHtAAfBmbPF2h8546baNkNwMM435a/Fx2TFK4usbKaFDj4V+WTorp3viz1TViqD4PJe4vRm6m9sTCix+8OZEbnk=");
        
        }
        
    }
}
