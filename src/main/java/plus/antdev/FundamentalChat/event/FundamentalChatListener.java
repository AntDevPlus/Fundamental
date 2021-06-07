package plus.antdev.FundamentalChat.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import plus.antdev.FundamentalChat.Main;
import plus.antdev.FundamentalChat.objects.Canal;

public class FundamentalChatListener implements Listener {

    Main plugin;
    public FundamentalChatListener(Main main) {
        this.plugin = main;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Main.canals.put(player.getName(),Canal.GLOBAL);
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        switch (message.charAt(0)) {
            case '*':
                message = message.substring(1);
                Bukkit.broadcastMessage(Canal.GLOBAL.getColor()+"[" + ChatColor.AQUA + "GLOBAL" + Canal.GLOBAL.getColor() + "] " + player.getDisplayName() +" : " + message);
                break;
            case '!':
                Main.switchCanal(player, Canal.SHOUT);
                plugin.shout(player, message);
                break;
            case '$':
                Main.switchCanal(player, Canal.GROUP);
                break;
            default:
                Main.switchCanal(player, Canal.SAY);
                if(Main.followed.containsKey(player)) {
                    event.setCancelled(true);
                    player.sendMessage("Attendez !");
                } else {
                    plugin.say(player, message);
                    break;
                }
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location location = event.getTo();

        if(Main.followed.containsKey(player)) {
            Main.followed .get(player).teleport(location);
        }
    }

}
