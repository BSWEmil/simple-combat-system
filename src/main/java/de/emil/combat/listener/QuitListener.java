package de.emil.combat.listener;

import de.emil.combat.CombatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final CombatManager combatManager;

    public QuitListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (combatManager.isInCombat(player)) {
            combatManager.markCombatLog(player);
            player.setHealth(0.0);
        }
    }
}