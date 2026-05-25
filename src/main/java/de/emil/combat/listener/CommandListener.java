package de.emil.combat.listener;

import de.emil.combat.CombatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {

    private final CombatManager combatManager;

    public CommandListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage().toLowerCase();

        String command = msg.split(" ")[0].replace("/", "");

        List<String> blocked = combatManager.getPlugin()
                .getConfig()
                .getStringList("blocked-commands");

        if (blocked.contains(command)) {
            if (combatManager.isInCombat(event.getPlayer())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("§cDu kannst das im Combat nicht benutzen!");
            }
        }
    }
}