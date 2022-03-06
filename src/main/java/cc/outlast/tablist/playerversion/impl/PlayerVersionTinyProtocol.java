package cc.outlast.tablist.playerversion.impl;

import cc.outlast.tablist.OutlastTab;
import cc.outlast.tablist.playerversion.IPlayerVersion;
import org.bukkit.entity.Player;

public class PlayerVersionTinyProtocol implements IPlayerVersion {

    @Override
    public int getProtocolVersion(Player player) {
        return OutlastTab.getInstance().getTinyProtocol().getProtocolVersion(player);
    }
}
