package plus.antdev.FundamentalChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import plus.antdev.FundamentalChat.event.FundamentalChatListener;
import plus.antdev.FundamentalChat.objects.Canal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Main extends JavaPlugin {

    public static final Map<String, Canal> canals = new HashMap<>();
    public static final Map<Player, ArmorStand> followed = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("[Fundamental] Loaded");
        getServer().getPluginManager().registerEvents(new FundamentalChatListener(this),this);
    }

    @Override
    public void onDisable() {
        getLogger().info("[Fundamental] unloaded");
    }

    public static void switchCanal(Player player, Canal canal) {
        canals.replace(player.getName(), canal);
    }

    public void shout(Player player, String message){
        final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        message = message.substring(1);
        for (Player p : players) {
            if( player.getLocation().distance(p.getLocation()) <= 50.00 ) {
                p.sendMessage(ChatColor.RED + "[" + ChatColor.GRAY + player.getDisplayName() + ChatColor.RED + "] " + ChatColor.GRAY + message);
            }
        }
    }

    public void say(Player player, String message) {
        ArmorStand armorstand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        armorstand.setGravity(false);
        armorstand.setCustomName(ChatColor.WHITE +"*"+message+"*");
        armorstand.setCustomNameVisible(true);
        armorstand.setVisible(false);
        armorstand.setInvulnerable(true);
        followed.put(player, armorstand);
        player.sendMessage(player.getName() + " : " + message);
        new BukkitRunnable() {
            @Override
            public void run() {
                armorstand.setHealth(0.00);
                followed.remove(player);
            }
        }.runTaskLater(this, 400);
    }
}
