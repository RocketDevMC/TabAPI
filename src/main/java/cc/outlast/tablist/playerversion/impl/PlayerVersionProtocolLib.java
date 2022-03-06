package cc.outlast.tablist.playerversion.impl;

import cc.outlast.tablist.playerversion.IPlayerVersion;
import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.entity.Player;

public class PlayerVersionProtocolLib implements IPlayerVersion {

    @Override
    public int getProtocolVersion(Player player) {
        return ProtocolLibrary.getProtocolManager().getProtocolVersion(player);
    }
}
