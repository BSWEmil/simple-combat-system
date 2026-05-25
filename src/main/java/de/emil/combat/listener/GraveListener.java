package de.emil.combat.listener;

import de.emil.combat.CombatManager;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GraveListener implements Listener {

    private final CombatManager m;

    public GraveListener(CombatManager m) {
        this.m = m;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {

        if (m.isCombatLogged(e.getEntity())) {

            e.setKeepInventory(false);

            e.getDrops().clear();

            e.getEntity().getInventory().forEach(item -> {
                if (item != null) {
                    e.getEntity().getWorld().dropItemNaturally(
                            e.getEntity().getLocation(), item);
                }
            });

            m.clearCombatLog(e.getEntity());
        }
    }
}