package cc.outlast.tablist;

import cc.outlast.tablist.listener.TabListener;
import cc.outlast.tablist.playerversion.IPlayerVersion;
import cc.outlast.tablist.playerversion.impl.*;
import cc.outlast.tablist.tabversion.ITabVersion;
import cc.outlast.tablist.tabversion.impl.TabVersionTinyProtocol;
import com.comphenix.tinyprotocol.TinyProtocol;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class OutlastTab {

    private static OutlastTab instance;
    private ITabVersion tablistVersion;
    private IPlayerVersion playerVersion;
    private ITablist tablist;
    private List<Tablist> tablists = new ArrayList<>();
    private TinyProtocol tinyProtocol;
    private JavaPlugin javaPlugin;
    private long startTime;
    private long ticks;

    public OutlastTab(JavaPlugin javaPlugin, ITablist tablist, long ticks) {
        this.javaPlugin = javaPlugin;
        this.tablist = tablist;
        this.startTime = System.currentTimeMillis();
        this.ticks = ticks;

        this.onEnable();
    }

    private void onEnable() {
        instance = this;

        this.tinyProtocol = new TinyProtocol(javaPlugin) {};
        this.tablistVersion = new TabVersionTinyProtocol(this);

        // FFS Why does protocolsupport and stuff have to edit protocol versions :(
        PluginManager pm = Bukkit.getServer().getPluginManager();
        if (pm.getPlugin("ProtocolSupport") != null)
            this.playerVersion = new PlayerVersionProtocolSupport();
        else if (pm.getPlugin("ViaVersion") != null)
            this.playerVersion = new PlayerVersionViaVersion();
        else if (pm.getPlugin("ProtocolLib") != null)
            this.playerVersion = new PlayerVersionProtocolLib();
        else this.playerVersion = new PlayerVersionTinyProtocol();

        pm.registerEvents(new TabListener(this), javaPlugin);

        new BukkitRunnable() {
            @Override
            public void run() {
                int size = tablists.size();
            	for(int i = 0; i < size; i++)tablists.get(i).update();
            }
        }.runTaskTimerAsynchronously(javaPlugin, ticks, ticks);
    }

    public ITabVersion getTablistVersion() {
        return tablistVersion;
    }

    public ITablist getTablist() {
        return tablist;
    }

    public static OutlastTab getInstance() {
        return instance;
    }

    public List<Tablist> getTablists() {
        return tablists;
    }

    public TinyProtocol getTinyProtocol() {
        return tinyProtocol;
    }

    public IPlayerVersion getPlayerVersion() {
        return playerVersion;
    }

    public JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }

    public long getStartTime() {
        return startTime;
    }
}
