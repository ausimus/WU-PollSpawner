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
package org.ausimus.wurmunlimited.mods.pollspawner.configuration;

@SuppressWarnings/*Who are you to tell me how to spell penus.*/("SpellCheckingInspection")
public class Constants {
    public static boolean debugMode;

    public static String logDir = "mods/PollSpawner/log.txt";

    // Items, also creature spawning shares some of this.
    public static boolean spawnRiftItems;
    public static int pollTime;
    public static boolean useWorldSizeMath;
    public static int MaxCount;
    public static int worldSizeMathDivider;
    public static boolean useRandomRarity;
    public static byte itemRarityLevel;

    // Creatures
    public static boolean spawnRiftCreatures;
    public static String RJ1Name = "Rift Beast";
    public static String RJ2Name = "Rift Jackal";
    public static String RJ3Name = "Rift Ogre";
    public static String RJ4Name = "Rift Warmaster";
    public static String RJ5Name = "Rift Caster";
    public static String RJ6Name = "Rift Ogre Mage";
    public static String RJ7Name = "Rift Summoner";
}
