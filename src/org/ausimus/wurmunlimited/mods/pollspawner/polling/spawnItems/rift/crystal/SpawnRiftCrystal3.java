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
package org.ausimus.wurmunlimited.mods.pollspawner.polling.spawnItems.rift.crystal;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.*;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.utils.DbUtilities;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import org.ausimus.wurmunlimited.mods.pollspawner.AusLogger;
import org.ausimus.wurmunlimited.mods.pollspawner.configuration.Constants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class SpawnRiftCrystal3
{
    private Random random = new Random();
    private int rtx = random.nextInt(Server.surfaceMesh.getSize());
    private int rty = random.nextInt(Server.surfaceMesh.getSize());

    public SpawnRiftCrystal3()
    {
        float randomQL = (float) 0 + random.nextInt(100);
        float randomROT = (float) 0 + random.nextInt(360);
        byte randomRarity;
        if (Constants.useRandomRarity)
        {
            randomRarity = (byte) random.nextInt(4);
        }
        else
        {
            randomRarity = Constants.itemRarityLevel;
        }
        int tile = Server.surfaceMesh.getTile(rtx, rty);
        short height = Tiles.decodeHeight(tile);
        if (height > 10 && preferedTypes(tile))
        {
            short[] steepness = getTileSteepness(rtx, rty);
            if (steepness[1] >= 21)
            {
                return;
            }
            VolaTile villtile = Zones.getOrCreateTile(rtx, rty, true);
            Village vill = villtile.getVillage();
            if (vill != null)
            {
                return;
            }
            if (Constants.useWorldSizeMath && getNumberOfItems(ItemList.riftCrystal3) < Zones.worldTileSizeX / Constants.worldSizeMathDivider)
            {
                SpawnItem(ItemList.riftCrystal3, randomQL, randomROT, randomRarity);
                AusLogger.WriteLog("C3 Type is " + Tiles.decodeType(tile) + ", " + "Location is " + rtx + ", " + rty + ", " + "Height is " + height + ", Steepness is " + steepness[1], Constants.logDir);
                if (Constants.debugMode)
                {
                    Server.getInstance().broadCastAlert("Type is " + Tiles.decodeType(tile) + ", " + "Location is " + rtx + ", " + rty + ", " + "Height is " + height + ", Steepness is " + steepness[1]);
                }
            }
            else
            {
                if (getNumberOfItems(ItemList.riftCrystal3) < Constants.MaxCount)
                {
                    SpawnItem(ItemList.riftCrystal3, randomQL, randomROT, randomRarity);
                    AusLogger.WriteLog("C3 Type is " + Tiles.decodeType(tile) + ", " + "Location is " + rtx + ", " + rty + ", " + "Height is " + height + ", Steepness is " + steepness[1], Constants.logDir);
                    if (Constants.debugMode)
                    {
                        Server.getInstance().broadCastAlert("Type is " + Tiles.decodeType(tile) + ", " + "Location is " + rtx + ", " + rty + ", " + "Height is " + height + ", Steepness is " + steepness[1]);
                    }
                }
            }
        }
    }

    private boolean preferedTypes(int tile)
    {
        return !Tiles.isRoadType(Tiles.decodeType(tile)) ||
                Tiles.decodeType(tile) != Tiles.Tile.TILE_ROCK.id ||
                Tiles.decodeType(tile) != Tiles.Tile.TILE_HOLE.id ||
                Tiles.decodeType(tile) != Tiles.Tile.TILE_LAVA.id;
    }

    private static short[] getTileSteepness(int tx, int ty)
    {
        short highest = -100;
        short lowest = 32000;

        int x;
        for (x = 0; x <= 1; ++x)
        {
            for (int y = 0; y <= 1; ++y)
            {
                if (tx + x < Zones.worldTileSizeX && ty + y < Zones.worldTileSizeY)
                {
                    short height;
                    height = Tiles.decodeHeight(Server.surfaceMesh.getTile(tx + x, ty + y));
                    if (height > highest)
                    {
                        highest = height;
                    }

                    if (height < lowest)
                    {
                        lowest = height;
                    }
                }
            }
        }

        x = (highest + lowest) / 2;
        return new short[]{(short) x, (short) (highest - lowest)};
    }

    // Yea i know i could have used a for loop to count the items, this is less laggy.
    private int getNumberOfItems(int item)
    {
        Statement stmt = null;
        ResultSet rs = null;
        int getNumItems = 0;
        try
        {
            Connection dbcon = DbConnector.getItemDbCon();
            stmt = dbcon.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM ITEMS WHERE (TEMPLATEID) = " + item);
            if (rs.next())
            {
                getNumItems = rs.getInt(1);
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DbUtilities.closeDatabaseObjects(stmt, rs);
        }
        return getNumItems;
    }

    private void SpawnItem(int item, float ql, float rot, byte rarity)
    {
        try
        {
            Item i = ItemFactory.createItem(item, ql, (float) (rtx << 2) + 2.0F, (float) (rty << 2) + 2.0F, rot, true, rarity, -10L, null);
            i.setName("Rift Crystal");
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }
    }
}