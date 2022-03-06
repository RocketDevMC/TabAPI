package cc.outlast.tablist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class Tablist {

    private Scoreboard scoreboard;
    private Player player;
    private OutlastTab outlastTab;
    private boolean enabled = true;

    protected void update() {
        outlastTab.getTablistVersion().update(player);
    }

    public void destroy() {
        outlastTab.getTablists().remove(this);
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

        for(String entry : scoreboard.getEntries())scoreboard.resetScores(entry);
        for(Objective objective : scoreboard.getObjectives())objective.unregister();
        for(Team team : scoreboard.getTeams())team.unregister();
    }

    public Player getPlayer() {
        return player;
    }
}
