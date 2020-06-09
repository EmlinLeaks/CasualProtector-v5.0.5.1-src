/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.PacketContainer
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Objects;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;

public abstract class AbstractPacket {
    public PacketContainer handle;

    public AbstractPacket(PacketContainer a, PacketType a2) {
        AbstractPacket a3;
        if (a == null) {
            throw new IllegalArgumentException((String)"Packet handle cannot be NULL.");
        }
        if (!Objects.equal((Object)a.getType(), (Object)a2)) {
            throw new IllegalArgumentException((String)(a.getHandle() + " is not a packet of type " + (Object)a2));
        }
        a3.handle = a;
    }

    public PacketContainer getHandle() {
        AbstractPacket a;
        return a.handle;
    }

    public void receivePacket(Player a3) {
        try {
            AbstractPacket a2;
            ProtocolLibrary.getProtocolManager().recieveClientPacket((Player)a3, (PacketContainer)a2.getHandle());
            return;
        }
        catch (Exception a3) {
            throw new RuntimeException((String)"Cannot receive packet.", (Throwable)a3);
        }
    }

    @Deprecated
    public void recievePacket(Player a3) {
        try {
            AbstractPacket a2;
            ProtocolLibrary.getProtocolManager().recieveClientPacket((Player)a3, (PacketContainer)a2.getHandle());
            return;
        }
        catch (Exception a3) {
            throw new RuntimeException((String)"Cannot recieve packet.", (Throwable)a3);
        }
    }

    public void broadcastPacket() {
        AbstractPacket a;
        ProtocolLibrary.getProtocolManager().broadcastServerPacket((PacketContainer)a.getHandle());
    }

    public void sendPacket(Player a3) {
        try {
            AbstractPacket a2;
            ProtocolLibrary.getProtocolManager().sendServerPacket((Player)a3, (PacketContainer)a2.getHandle());
            return;
        }
        catch (InvocationTargetException a3) {
            throw new RuntimeException((String)"Cannot send packet.", (Throwable)a3);
        }
    }
}

