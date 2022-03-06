package cc.outlast.tablist.playerversion.impl;

import cc.outlast.tablist.playerversion.IPlayerVersion;
import org.bukkit.entity.Player;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolSupportAPI;

public class PlayerVersionProtocolSupport implements IPlayerVersion {

    @Override
    public int getProtocolVersion(Player player) {
        return ProtocolSupportAPI.getProtocolVersion(player).getId();
    }
}
