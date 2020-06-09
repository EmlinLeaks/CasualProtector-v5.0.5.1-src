/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.injector.PacketConstructor
 *  com.comphenix.protocol.reflect.StructureModifier
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.PacketConstructor;
import com.comphenix.protocol.reflect.StructureModifier;
import java.util.UUID;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.yooniks.protector.bukkit.wrapper.AbstractPacket;

public class WrapperPlayServerSpawnEntity
extends AbstractPacket {
    public static final PacketType TYPE = PacketType.Play.Server.SPAWN_ENTITY;
    private static PacketConstructor entityConstructor;

    public int getEntityID() {
        WrapperPlayServerSpawnEntity a;
        return ((Integer)a.handle.getIntegers().read((int)0)).intValue();
    }

    public void setEntityID(int a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getIntegers().write((int)0, (Object)Integer.valueOf((int)a));
    }

    public void setObjectData(int a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getIntegers().write((int)7, (Object)Integer.valueOf((int)a));
    }

    public int getObjectData() {
        WrapperPlayServerSpawnEntity a;
        return ((Integer)a.handle.getIntegers().read((int)7)).intValue();
    }

    public double getOptionalSpeedX() {
        WrapperPlayServerSpawnEntity a;
        return (double)((Integer)a.handle.getIntegers().read((int)1)).intValue() / 8000.0;
    }

    public Entity getEntity(World a) {
        WrapperPlayServerSpawnEntity a2;
        return (Entity)a2.handle.getEntityModifier((World)a).read((int)0);
    }

    private static /* synthetic */ PacketContainer fromEntity(Entity a, int a2, int a3) {
        if (entityConstructor != null) return entityConstructor.createPacket((Object[])new Object[]{a, Integer.valueOf((int)a2), Integer.valueOf((int)a3)});
        entityConstructor = ProtocolLibrary.getProtocolManager().createPacketConstructor((PacketType)TYPE, (Object[])new Object[]{a, Integer.valueOf((int)a2), Integer.valueOf((int)a3)});
        return entityConstructor.createPacket((Object[])new Object[]{a, Integer.valueOf((int)a2), Integer.valueOf((int)a3)});
    }

    public WrapperPlayServerSpawnEntity() {
        super((PacketContainer)new PacketContainer((PacketType)TYPE), (PacketType)TYPE);
        WrapperPlayServerSpawnEntity a;
        a.handle.getModifier().writeDefaults();
    }

    public void setPitch(float a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getIntegers().write((int)4, (Object)Integer.valueOf((int)((int)(a * 256.0f / 360.0f))));
    }

    public float getPitch() {
        WrapperPlayServerSpawnEntity a;
        return (float)((Integer)a.handle.getIntegers().read((int)4)).intValue() * 360.0f / 256.0f;
    }

    public void setOptionalSpeedY(double a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getIntegers().write((int)2, (Object)Integer.valueOf((int)((int)(a * 8000.0))));
    }

    public WrapperPlayServerSpawnEntity(Entity a, int a2, int a3) {
        super((PacketContainer)WrapperPlayServerSpawnEntity.fromEntity((Entity)a, (int)a2, (int)a3), (PacketType)TYPE);
        WrapperPlayServerSpawnEntity a4;
    }

    public double getX() {
        WrapperPlayServerSpawnEntity a;
        return ((Double)a.handle.getDoubles().read((int)0)).doubleValue();
    }

    public void setType(int a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getIntegers().write((int)6, (Object)Integer.valueOf((int)a));
    }

    public void setYaw(float a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getIntegers().write((int)5, (Object)Integer.valueOf((int)((int)(a * 256.0f / 360.0f))));
    }

    public Entity getEntity(PacketEvent a) {
        WrapperPlayServerSpawnEntity a2;
        return a2.getEntity((World)a.getPlayer().getWorld());
    }

    public double getOptionalSpeedY() {
        WrapperPlayServerSpawnEntity a;
        return (double)((Integer)a.handle.getIntegers().read((int)2)).intValue() / 8000.0;
    }

    public void setY(double a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getDoubles().write((int)1, (Object)Double.valueOf((double)a));
    }

    public double getY() {
        WrapperPlayServerSpawnEntity a;
        return ((Double)a.handle.getDoubles().read((int)1)).doubleValue();
    }

    public void setOptionalSpeedZ(double a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getIntegers().write((int)3, (Object)Integer.valueOf((int)((int)(a * 8000.0))));
    }

    public UUID getUniqueId() {
        WrapperPlayServerSpawnEntity a;
        return (UUID)a.handle.getUUIDs().read((int)0);
    }

    public double getZ() {
        WrapperPlayServerSpawnEntity a;
        return ((Double)a.handle.getDoubles().read((int)2)).doubleValue();
    }

    public WrapperPlayServerSpawnEntity(PacketContainer a) {
        super((PacketContainer)a, (PacketType)TYPE);
        WrapperPlayServerSpawnEntity a2;
    }

    public void setZ(double a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getDoubles().write((int)2, (Object)Double.valueOf((double)a));
    }

    public float getYaw() {
        WrapperPlayServerSpawnEntity a;
        return (float)((Integer)a.handle.getIntegers().read((int)5)).intValue() * 360.0f / 256.0f;
    }

    public void setUniqueId(UUID a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getUUIDs().write((int)0, (Object)a);
    }

    public void setOptionalSpeedX(double a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getIntegers().write((int)1, (Object)Integer.valueOf((int)((int)(a * 8000.0))));
    }

    public int getType() {
        WrapperPlayServerSpawnEntity a;
        return ((Integer)a.handle.getIntegers().read((int)6)).intValue();
    }

    public double getOptionalSpeedZ() {
        WrapperPlayServerSpawnEntity a;
        return (double)((Integer)a.handle.getIntegers().read((int)3)).intValue() / 8000.0;
    }

    public void setX(double a) {
        WrapperPlayServerSpawnEntity a2;
        a2.handle.getDoubles().write((int)0, (Object)Double.valueOf((double)a));
    }
}

