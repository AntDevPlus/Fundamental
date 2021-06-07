package plus.antdev.FundamentalChat.objects;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import plus.antdev.FundamentalChat.Main;

public enum Canal {
    GLOBAL(ChatColor.DARK_AQUA+""),
    GROUP(ChatColor.AQUA+""),
    SHOUT(ChatColor.RED+""),
    SAY(ChatColor.WHITE+"");

    private final String color;
    Canal(String color) {
        this.color = color;
    }
    public String getColor() {
        return color;
    }
}