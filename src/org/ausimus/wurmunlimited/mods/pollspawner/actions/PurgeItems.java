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
package org.ausimus.wurmunlimited.mods.pollspawner.actions;
import com.wurmonline.server.Items;
import com.wurmonline.server.items.*;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import java.util.Collections;
import java.util.List;

public class PurgeItems implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer
{

    private static short actionID;
    private static ActionEntry actionEntry;

    public PurgeItems()
    {
        actionID = (short) ModActions.getNextActionId();
        actionEntry = ActionEntry.createEntry(actionID, "Purge PollSpawned Items", "Purging", new int[]{});
        ModActions.registerAction(actionEntry);
    }

    @Override
    public BehaviourProvider getBehaviourProvider()
    {
        return this;
    }

    @Override
    public ActionPerformer getActionPerformer()
    {
        return this;
    }

    /**
     * @return {@link PurgeItems#actionID}
     */
    @Override
    public short getActionId()
    {
        return actionID;
    }

    /**
     * @param performer The Creature performing the action.
     * @param source    The source Item used to perform the action.
     * @param target    The target Item used to perform the action.
     * @return {@link Collections#singletonList(java.lang.Object) java.lang.Object = actionEntry} / null;
     */
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target)
    {
        if (source.getTemplateId() == ItemList.wandDeity && target.getTemplateId() == ItemList.wandDeity)
        {
            return Collections.singletonList(actionEntry);
        }
        else
        {
            return null;
        }
    }

    /**
     * @param act       The action.
     * @param performer The Creature performing the action.
     * @param source    The source Item used to perform the action.
     * @param target    The target Item used to perform the action.
     * @param action    Action id.
     * @param counter   Used for timer, there isn't one so it's ignored.
     * @return boolean.
     */
    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter)
    {
        if (source.getTemplateId() == ItemList.wandDeity && target.getTemplateId() == ItemList.wandDeity && performer.getPower() == MiscConstants.POWER_IMPLEMENTOR)
        {
            Item[] items = Items.getAllItems();
            for (Item toPurge : items)
            {
                if (purgeArray(toPurge))
                {
                    Items.destroyItem(toPurge.getWurmId());
                    performer.getCommunicator().sendNormalServerMessage("Items Purged.", (byte) 3);
                }
            }
        }
        else
        {
            performer.getCommunicator().sendNormalServerMessage("Unauthorized.", (byte) 3);
        }
        return true;
    }

    private boolean purgeArray(Item item)
    {
        return item.getTemplateId() == ItemList.riftCrystal1 ||
                item.getTemplateId() == ItemList.riftCrystal2 ||
                item.getTemplateId() == ItemList.riftCrystal3 ||
                item.getTemplateId() == ItemList.riftCrystal4 ||
                item.getTemplateId() == ItemList.riftPlant1 ||
                item.getTemplateId() == ItemList.riftPlant2 ||
                item.getTemplateId() == ItemList.riftPlant3 ||
                item.getTemplateId() == ItemList.riftPlant4 ||
                item.getTemplateId() == ItemList.riftStone1 ||
                item.getTemplateId() == ItemList.riftStone2 ||
                item.getTemplateId() == ItemList.riftStone3 ||
                item.getTemplateId() == ItemList.riftStone4;
    }
}