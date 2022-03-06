package cc.outlast.tablist.tabversion.impl;

import cc.outlast.tablist.OutlastTab;
import cc.outlast.tablist.tabversion.ITabVersion;
import com.comphenix.tinyprotocol.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

public class TabVersionTinyProtocol implements ITabVersion {

    private Class<?> headerFooter = Reflection.getMinecraftClass("PacketPlayOutPlayerListHeaderFooter");
    private Class<?> chatComponentText = Reflection.getMinecraftClass("ChatComponentText");
    private OutlastTab outlastTab;

    public TabVersionTinyProtocol(OutlastTab outlastTab) {
        this.outlastTab = outlastTab;
    }

    @Override
    public void setup(Player player) {
        update(player);
    }

    @Override
    public void update(Player player) {
        //TODO: Find a better way for this
        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    setHeaderAndFooter(player);
                }
            }
        }.runTaskTimerAsynchronously(outlastTab.getJavaPlugin(), 0L, 30L);
    }

    @Override
    public void setHeaderAndFooter(Player player) {
        if(getSlots(player) == 80) {
            try {
                if(Reflection.VERSION.contains("v1_13") || Reflection.VERSION.contains("v1_14")) {
                    player.getClass().getMethod("setPlayerListHeader", String.class).invoke(player,
                            ChatColor.translateAlternateColorCodes('&', outlastTab.getTablist().getHeader(player)));
                    player.getClass().getMethod("setPlayerListFooter", String.class).invoke(player,
                            ChatColor.translateAlternateColorCodes('&', outlastTab.getTablist().getFooter(player)));
                    return;
                }
                    Object headerFooterPacket = headerFooter.newInstance();
                    Field a = headerFooterPacket.getClass().getDeclaredField("a");
                    Field b = headerFooterPacket.getClass().getDeclaredField("b");

                    a.setAccessible(true);
                    b.setAccessible(true);

                    Object chatComponentHeader = chatComponentText.getConstructors()[0].newInstance(ChatColor.
                            translateAlternateColorCodes('&', outlastTab.getTablist().getHeader(player)));
                    Object chatComponentFooter = chatComponentText.getConstructors()[0].newInstance(ChatColor.
                            translateAlternateColorCodes('&', outlastTab.getTablist().getFooter(player)));

                    a.set(headerFooterPacket, chatComponentHeader);
                    b.set(headerFooterPacket, chatComponentFooter);

                    outlastTab.getTinyProtocol().sendPacket(player, headerFooterPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getSlots(Player player) {
        return outlastTab.getPlayerVersion().getProtocolVersion(player) >= 47 ? 80 : 60;
    }
}
