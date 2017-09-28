/*
     ___          ___          ___                     ___          ___          ___
    /\  \        /\__\        /\  \         ___       /\__\        /\__\        /\  \
   /::\  \      /:/  /   2   /::\  \    0  /\  \  1  /::|  |  7   /:/  /       /::\  \
  /:/\:\  \    /:/  /       /:/\ \  \      \:\  \   /:|:|  |     /:/  /       /:/\ \  \
 /::\~\:\  \  /:/  /  ___  _\:\~\ \  \     /::\__\ /:/|:|__|__  /:/  /  ___  _\:\~\ \  \
/:/\:\ \:\__\/:/__/  /\__\/\ \:\ \ \__\ __/:/\/__//:/ |::::\__\/:/__/  /\__\/\ \:\ \ \__\
\/__\:\/:/  /\:\  \ /:/  /\:\ \:\ \/__//\/:/  /   \/__/~~/:/  /\:\  \ /:/  /\:\ \:\ \/__/
     \::/  /  \:\  /:/  /  \:\ \:\__\  \::/__/          /:/  /  \:\  /:/  /  \:\ \:\__\
     /:/  /    \:\/:/  /    \:\/:/  /   \:\__\         /:/  /    \:\/:/  /    \:\/:/  /
    /:/  /      \::/  /      \::/  /     \/__/        /:/  /      \::/  /      \::/  /
    \/__/        \/__/        \/__/                   \/__/        \/__/        \/__/
*/
package org.ausimus.wurmunlimited.mods.pollspawner;

import com.wurmonline.server.TimeConstants;
import org.ausimus.wurmunlimited.mods.pollspawner.actions.PurgeItems;
import org.ausimus.wurmunlimited.mods.pollspawner.configuration.Constants;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.crystal.SpawnRiftCrystal1;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.crystal.SpawnRiftCrystal2;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.crystal.SpawnRiftCrystal3;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.crystal.SpawnRiftCrystal4;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.foliage.SpawnRiftPlant1;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.foliage.SpawnRiftPlant2;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.foliage.SpawnRiftPlant3;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.foliage.SpawnRiftPlant4;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.stone.SpawnRiftStone1;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.stone.SpawnRiftStone2;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.stone.SpawnRiftStone3;
import org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.stone.SpawnRiftStone4;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerPollListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Properties;

public class Initiator implements WurmServerMod, Configurable, ServerPollListener, PreInitable {

    private long getLastPollItem1 = 0;
    private long getLastPollItem2 = 0;
    private long getLastPollItem3 = 0;
    private long getLastPollItem4 = 0;

    private long getLastPollItem5 = 0;
    private long getLastPollItem6 = 0;
    private long getLastPollItem7 = 0;
    private long getLastPollItem8 = 0;

    private long getLastPollItem9 = 0;
    private long getLastPollItem10 = 0;
    private long getLastPollItem11 = 0;
    private long getLastPollItem12 = 0;

    @Override
    public void configure(Properties properties) {
        Constants.debugMode = Boolean.parseBoolean(properties.getProperty("debugMode", Boolean.toString(Constants.debugMode)));
        Constants.pollTime = Integer.parseInt(properties.getProperty("pollTime", Integer.toString(Constants.pollTime)));
        Constants.useWorldSizeMath = Boolean.parseBoolean(properties.getProperty("useWorldSizeMath", Boolean.toString(Constants.useWorldSizeMath)));
        Constants.MaxCount = Integer.parseInt(properties.getProperty("MaxCount", Integer.toString(Constants.MaxCount)));
        Constants.spawnRiftItems = Boolean.parseBoolean(properties.getProperty("spawnRiftItems", Boolean.toString(Constants.spawnRiftItems)));
        Constants.worldSizeMathDivider = Integer.parseInt(properties.getProperty("worldSizeMathDivider", Integer.toString(Constants.worldSizeMathDivider)));
        Constants.useRandomRarity = Boolean.parseBoolean(properties.getProperty("useRandomRarity", Boolean.toString(Constants.useRandomRarity)));
        Constants.itemRarityLevel = Byte.parseByte(properties.getProperty("itemRarityLevel", Byte.toString(Constants.itemRarityLevel)));
    }

    @Override
    public void onServerPoll() {
        long ItemPollTime1;
        if (Constants.debugMode) {
            ItemPollTime1 = 1;
        } else {
            ItemPollTime1 = TimeConstants.SECOND_MILLIS * Constants.pollTime;
        }
        long ItemPollTime2 = ItemPollTime1 + Constants.pollTime;
        long ItemPollTime3 = ItemPollTime2 + Constants.pollTime;
        long ItemPollTime4 = ItemPollTime3 + Constants.pollTime;

        long ItemPollTime5 = ItemPollTime4 + Constants.pollTime;
        long ItemPollTime6 = ItemPollTime5 + Constants.pollTime;
        long ItemPollTime7 = ItemPollTime6 + Constants.pollTime;
        long ItemPollTime8 = ItemPollTime7 + Constants.pollTime;

        long ItemPollTime9 = ItemPollTime8 + Constants.pollTime;
        long ItemPollTime10 = ItemPollTime9 + Constants.pollTime;
        long ItemPollTime11 = ItemPollTime10 + Constants.pollTime;
        long ItemPollTime12 = ItemPollTime11 + Constants.pollTime;

        if (Constants.spawnRiftItems) {
            if (System.currentTimeMillis() - ItemPollTime1 > getLastPollItem1) {
                new SpawnRiftCrystal1();
                getLastPollItem1 = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - ItemPollTime2 > getLastPollItem2) {
                new SpawnRiftCrystal2();
                getLastPollItem2 = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - ItemPollTime3 > getLastPollItem3) {
                new SpawnRiftCrystal3();
                getLastPollItem3 = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - ItemPollTime4 > getLastPollItem4) {
                new SpawnRiftCrystal4();
                getLastPollItem4 = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - ItemPollTime5 > getLastPollItem5) {
                new SpawnRiftPlant1();
                getLastPollItem5 = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - ItemPollTime6 > getLastPollItem6) {
                new SpawnRiftPlant2();
                getLastPollItem6 = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - ItemPollTime7 > getLastPollItem7) {
                new SpawnRiftPlant3();
                getLastPollItem7 = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - ItemPollTime8 > getLastPollItem8) {
                new SpawnRiftPlant4();
                getLastPollItem8 = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - ItemPollTime9 > getLastPollItem9) {
                new SpawnRiftStone1();
                getLastPollItem9 = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - ItemPollTime10 > getLastPollItem10) {
                new SpawnRiftStone2();
                getLastPollItem10 = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - ItemPollTime11 > getLastPollItem11) {
                new SpawnRiftStone3();
                getLastPollItem11 = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - ItemPollTime12 > getLastPollItem12) {
                new SpawnRiftStone4();
                getLastPollItem12 = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void preInit() {
        ModActions.init();
        ModActions.registerAction(new PurgeItems());
    }
}