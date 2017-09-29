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
package org.ausimus.wurmunlimited.mods.pollspawner.polling.spawncreatures;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.*;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.CreatureTemplateIds;
import com.wurmonline.server.creatures.CreatureTypes;
import com.wurmonline.server.utils.DbUtilities;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import org.ausimus.wurmunlimited.mods.pollspawner.AusLogger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import static org.ausimus.wurmunlimited.mods.pollspawner.configuration.Constants.*;

/*Super duper copyPasta*/
@SuppressWarnings/*Who are you to tell me how to spell penus.*/("SpellCheckingInspection")
public class SpawnRiftCreature7 {
    private Random random = new Random();
    private int rtx = random.nextInt(Server.surfaceMesh.getSize());
    private int rty = random.nextInt(Server.surfaceMesh.getSize());

    public SpawnRiftCreature7() {
        int max = 1;
        int min = 0;
        byte rSex = (byte) (random.nextInt(max - min + 1) + min);

        float randomROT = (float) 0 + random.nextInt(360);
        byte randomType = (byte) random.nextInt(CreatureTypes.C_MOD_DISEASED);
        int tile = Server.surfaceMesh.getTile(rtx, rty);
        short height = Tiles.decodeHeight(tile);
        if (height > 10 && preferedTypes(tile)) {
            short[] steepness = getTileSteepness(rtx, rty);
            if (steepness[1] >= 21) {
                return;
            }
            VolaTile villtile = Zones.getOrCreateTile(rtx, rty, true);
            Village vill = villtile.getVillage();
            if (vill != null) {
                return;
            }
            if (useWorldSizeMath && getNumberofCreatures() < Zones.worldTileSizeX / worldSizeMathDivider) {
                SpawnCreature(CreatureTemplateIds.RIFT_JACKAL_SUMMONER_CID, randomType, randomROT, rSex, RJ7Name);
                AusLogger.WriteLog("Cret7 Type is " + Tiles.decodeType(tile) + ", " + "Location is " + rtx + ", " + rty + ", " + "Height is " + height + ", Steepness is " + steepness[1], logDir);
                if (debugMode) {
                    Server.getInstance().broadCastAlert("Cret7 Type is " + Tiles.decodeType(tile) + ", " + "Location is " + rtx + ", " + rty + ", " + "Height is " + height + ", Steepness is " + steepness[1]);
                }
            } else {
                if (getNumberofCreatures() < MaxCount) {
                    SpawnCreature(CreatureTemplateIds.RIFT_JACKAL_SUMMONER_CID, randomType, randomROT, rSex, RJ7Name);
                    AusLogger.WriteLog("Cret7 Type is " + Tiles.decodeType(tile) + ", " + "Location is " + rtx + ", " + rty + ", " + "Height is " + height + ", Steepness is " + steepness[1], logDir);
                    if (debugMode) {
                        Server.getInstance().broadCastAlert("Cret7 Type is " + Tiles.decodeType(tile) + ", " + "Location is " + rtx + ", " + rty + ", " + "Height is " + height + ", Steepness is " + steepness[1]);
                    }
                }
            }
        }
    }

    /**
     * @param tile The tile to call.
     * @return the return statement for prefered spawn tile type.
     */
    private boolean preferedTypes(int tile) {
        return !Tiles.isRoadType(Tiles.decodeType(tile)) ||
                Tiles.decodeType(tile) != Tiles.Tile.TILE_ROCK.id ||
                Tiles.decodeType(tile) != Tiles.Tile.TILE_HOLE.id ||
                Tiles.decodeType(tile) != Tiles.Tile.TILE_LAVA.id;
    }

    /**
     * @param tx TileX
     * @param ty TileY
     * @return new short[]{(short) x, (short) (highest - lowest)};
     */
    private static short[] getTileSteepness(int tx, int ty) {
        short highest = -100;
        short lowest = 32000;
        int x;
        for (x = 0; x <= 1; ++x) {
            for (int y = 0; y <= 1; ++y) {
                if (tx + x < Zones.worldTileSizeX && ty + y < Zones.worldTileSizeY) {
                    short height;
                    height = Tiles.decodeHeight(Server.surfaceMesh.getTile(tx + x, ty + y));
                    if (height > highest) {
                        highest = height;
                    }

                    if (height < lowest) {
                        lowest = height;
                    }
                }
            }
        }
        x = (highest + lowest) / 2;
        return new short[]{(short) x, (short) (highest - lowest)};
    }

    /**
     * Yea i know i could have used a for loop to count the items, this is less laggy.
     *
     * @return getNumCreatures
     */
    private int getNumberofCreatures() {
        Statement stmt = null;
        ResultSet rs = null;
        int getNumCreatures = 0;
        try {
            Connection dbcon = DbConnector.getCreatureDbCon();
            stmt = dbcon.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM CREATURES WHERE NAME = \"Rift Summoner\"");
            if (rs.next()) {
                getNumCreatures = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtilities.closeDatabaseObjects(stmt, rs);
        }
        return getNumCreatures;
    }

    /**
     * @param cret The creature to create.
     * @param type Creature type.
     * @param rot  Rotation.
     * @param sex  Gender.
     * @param name Name.
     */
    private void SpawnCreature(int cret, byte type, float rot, byte sex, String name) {
        try {
            Creature.doNew(cret, type, (float) (rtx << 2) + 2.0F, (float) (rty << 2) + 2.0F, rot, 0, name, sex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}