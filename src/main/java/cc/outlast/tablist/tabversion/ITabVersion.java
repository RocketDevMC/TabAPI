package cc.outlast.tablist.tabversion;

import org.bukkit.entity.Player;

public interface ITabVersion {

    void setup(Player player);
    void update(Player player);
    int getSlots(Player player);
    void setHeaderAndFooter(Player player);
}
