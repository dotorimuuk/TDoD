package topen.ketoglutaric.TDoD.shop.npc;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.t;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;

public class NPCCommand implements CommandExecutor{

    NPCManager manager;

    public NPCCommand(NPCManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String @NotNull [] args) {
        
        Player player = null;
        Location location = null;

        if (sender instanceof Player) {
            player = (Player) sender;
            location = player.getLocation();
        }
        if (args.length == 0) {
            return false;
        }
        switch (args[0]) {
            case "list" -> {
                Map<Integer, NPC> map = manager.getNpcMap();
                sender.sendMessage(Component.text("=============현재 NPC 목록============="));
                for (Entry<Integer,NPC> entrySet : map.entrySet()) {
                    Integer id = entrySet.getKey();
                    NPC npc = entrySet.getValue();
                    sender.sendMessage(Component.text(String.format("ID : %d | Name : %s | Location : [%d %d %d]",
                    id, npc.getDisplayName(), npc.getLocation().getX(), npc.getLocation().getY(), npc.getLocation().getZ())));
                }
                sender.sendMessage(Component.text("====================================="));
            }
            case "add" -> {
                //args 1 : displayname
                //args 2 : skin player name
                //args 3, 4, 5 : location
                switch (args.length) {
                    case 1 -> {
                        sender.sendMessage("NPC의 이름을 적어 주세요!");
                        return true;
                    }
                    case 2 -> {
                        sender.sendMessage("NPC의 스킨을 가져올 플레이어의 이름을 적어 주세요!");
                        return true;
                    }
                    case 3 -> {
                        if (location == null) {
                            sender.sendMessage("위치를 제대로 적어 주세요!");
                            return true;
                        }
                    }
                    case 4, 5 -> {
                        sender.sendMessage("위치를 제대로 적어 주세요!");
                        return true;
                    }
                }
                if (location == null) location = new Location(Bukkit.getWorld("world"), 
                Integer.parseInt(args[3]), 
                Integer.parseInt(args[4]), 
                Integer.parseInt(args[5]));
                int npcPlayer = manager.createNPCPlayer(args[1], args[2], location);
                sender.sendMessage("ID " + npcPlayer + " 의 NPC가 생성되었습니다.");
            }
            case "remove" -> {
                if (args.length == 1) {
                    sender.sendMessage("없앨 NPC의 ID를 적어 주세요.");
                    return true;
                }
                manager.deleteNPC(Integer.parseInt(args[1]));
                sender.sendMessage("NPC ID "+ args[1] + "을 삭제했습니다.");
            }
            default -> {
                return false;
            }
        }



        return true;
    }
    
}
