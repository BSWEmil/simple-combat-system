package de.emil.combat.listener;

import de.emil.combat.CombatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final CombatManager combatManager;

    public DeathListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        if (combatManager.isCombatLogged(event.getEntity())) {
            event.setKeepInventory(false);
            event.setDroppedExp(0);
            combatManager.clearCombatLog(event.getEntity());
        }

        combatManager.remove(event.getEntity());
    }
}