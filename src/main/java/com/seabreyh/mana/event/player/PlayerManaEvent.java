package com.seabreyh.mana.event.player;

import com.seabreyh.mana.ManaMod;
import com.seabreyh.mana.mana_stat.PlayerManaStatProvider;
import com.seabreyh.mana.networking.ManaMessages;
import com.seabreyh.mana.networking.packet.ManaStatSyncS2CPacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerManaEvent {
    private static boolean hasMana;
    private static boolean fullMana;

    public static boolean consumeMana(Player player, int mana) {

        player.getCapability(PlayerManaStatProvider.PLAYER_MANA_STAT).ifPresent(mana_stat -> {
            if (mana_stat.getManaValue() > 0) {
                hasMana = true;
            } else {
                hasMana = false;
            }

            mana_stat.subMana(mana);
            ManaMod.LOGGER.debug("###SERVER_SUBTRACT_MANA " + mana);

            // Send packet with new player mana data to all the clients
            ManaMessages.sendToPlayer(new ManaStatSyncS2CPacket(mana_stat.getManaValue()),
                    ((ServerPlayer) player));

        });

        return hasMana;
    }

    public static boolean regenMana(Player player, int mana) {
        player.getCapability(PlayerManaStatProvider.PLAYER_MANA_STAT).ifPresent(mana_stat -> {

            if (mana_stat.isFull()) {
                fullMana = true;
            } else {
                fullMana = false;
            }

            mana_stat.addMana(mana);

            ManaMod.LOGGER.debug("###SERVER_ADD_MANA " + mana);

            // Send packet with new player mana data to all the clients
            ManaMessages.sendToPlayer(new ManaStatSyncS2CPacket(mana_stat.getManaValue()),
                    ((ServerPlayer) player));
        });

        return fullMana;
    }
}