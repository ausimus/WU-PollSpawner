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
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.CreatureTemplateIds;
import com.wurmonline.server.creatures.Creatures;
import com.wurmonline.server.items.*;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import java.util.Collections;
import java.util.List;

public class PurgeCreatures implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {

    private static short actionID;
    private static ActionEntry actionEntry;

    public PurgeCreatures() {
        actionID = (short) ModActions.getNextActionId();
        actionEntry = ActionEntry.createEntry(actionID, "Purge PollSpawned Creatures", "Purging", new int[]{});
        ModActions.registerAction(actionEntry);
    }

    @Override
    public BehaviourProvider getBehaviourProvider() {
        return this;
    }

    @Override
    public ActionPerformer getActionPerformer() {
        return this;
    }

    /**
     * @return {@link PurgeItems#actionID}
     */
    @Override
    public short getActionId() {
        return actionID;
    }

    /**
     * @param performer The Creature performing the action.
     * @param source    The source Item used to perform the action.
     * @param target    The target Item used to perform the action.
     * @return {@link Collections#singletonList(java.lang.Object) java.lang.Object = actionEntry} / null;
     */
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        if (source.getTemplateId() == ItemList.wandDeity && target.getTemplateId() == ItemList.wandDeity) {
            return Collections.singletonList(actionEntry);
        } else {
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
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
        if (source.getTemplateId() == ItemList.wandDeity && target.getTemplateId() == ItemList.wandDeity && performer.getPower() == MiscConstants.POWER_IMPLEMENTOR) {
            Creature[] crets = Creatures.getInstance().getCreatures();
            for (Creature toPurge : crets) {
                if (purgeArray(toPurge)) {
                    toPurge.destroy();
                    performer.getCommunicator().sendNormalServerMessage("Creatures Purged.", (byte) 3);
                }
            }
        } else {
            performer.getCommunicator().sendNormalServerMessage("Unauthorized.", (byte) 3);
        }
        return true;
    }

    /**
     * @param cret The creature in question represented by {@link CreatureTemplateIds}
     * @return Return above value.
     */
    private boolean purgeArray(Creature cret) {
        return cret.getTemplate().getTemplateId() == CreatureTemplateIds.RIFT_JACKAL_ONE_CID ||
                cret.getTemplate().getTemplateId() == CreatureTemplateIds.RIFT_JACKAL_TWO_CID ||
                cret.getTemplate().getTemplateId() == CreatureTemplateIds.RIFT_JACKAL_THREE_CID ||
                cret.getTemplate().getTemplateId() == CreatureTemplateIds.RIFT_JACKAL_FOUR_CID ||
                cret.getTemplate().getTemplateId() == CreatureTemplateIds.RIFT_JACKAL_CASTER_CID ||
                cret.getTemplate().getTemplateId() == CreatureTemplateIds.RIFT_OGRE_MAGE_CID ||
                cret.getTemplate().getTemplateId() == CreatureTemplateIds.RIFT_JACKAL_SUMMONER_CID;
    }
}