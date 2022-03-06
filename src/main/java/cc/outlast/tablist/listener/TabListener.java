package cc.outlast.tablist.listener;

import cc.outlast.tablist.OutlastTab;
import cc.outlast.tablist.Tablist;
import cc.outlast.tablist.playerversion.impl.PlayerVersionTinyProtocol;
import cc.outlast.tablist.tabversion.impl.TabVersionTinyProtocol;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TabListener implements Listener {

    private OutlastTab outlastTab;

    public TabListener(OutlastTab outlastTab) {
        this.outlastTab = outlastTab;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                outlastTab.getTablistVersion().setup(event.getPlayer());
            }
        }.runTaskLater(outlastTab.getJavaPlugin(), outlastTab.getPlayerVersion() instanceof PlayerVersionTinyProtocol ? 30L : 20L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Tablist tablist = null;
        List<Tablist> tablists = outlastTab.getTablists();
        int size = tablists.size();
        for(int i = 0; i < size; i++)
        	if(tablists.get(i).getPlayer() == event.getPlayer())
        	{
        		tablist = tablists.get(i);
        		break;
        	}
        
        if (tablist != null) tablist.destroy();
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        // To prevent tiny protocol errors
        if(outlastTab.getTablistVersion() instanceof TabVersionTinyProtocol
                && System.currentTimeMillis() - outlastTab.getStartTime() < TimeUnit.SECONDS.toMillis(3L))
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cThe server is still loading.");
    }
}
