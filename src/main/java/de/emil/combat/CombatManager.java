package de.emil.combat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class CombatManager {

    private final CombatPlugin plugin;

    private final HashMap<UUID, Long> combatTagged = new HashMap<>();
    private final HashSet<UUID> combatLogged = new HashSet<>();
    private final int combatTime;

    public CombatManager(CombatPlugin plugin) {
        this.plugin = plugin;
        this.combatTime = plugin.getConfig().getInt("combat.time", 20);
    }

    public CombatPlugin getPlugin() {
        return plugin;
    }

    public void tag(Player player) {
        boolean wasInCombat = isInCombat(player);

        long end = System.currentTimeMillis() + (combatTime * 1000);
        combatTagged.put(player.getUniqueId(), end);

        if (!wasInCombat) {
            player.sendMessage("§cDu bist jetzt im Combat!");
            startActionBar(player);
        }
    }

    public boolean isInCombat(Player player) {
        if (!combatTagged.containsKey(player.getUniqueId())) return false;

        long end = combatTagged.get(player.getUniqueId());
        if (System.currentTimeMillis() > end) {
            combatTagged.remove(player.getUniqueId());
            return false;
        }
        return true;
    }

    public void remove(Player player) {
        combatTagged.remove(player.getUniqueId());
        player.sendActionBar("");
    }

    // 🔥 Combat-Log
    public void markCombatLog(Player player) {
        combatLogged.add(player.getUniqueId());
    }

    public boolean isCombatLogged(Player player) {
        return combatLogged.contains(player.getUniqueId());
    }

    public void clearCombatLog(Player player) {
        combatLogged.remove(player.getUniqueId());
    }

    private void startActionBar(Player player) {
        Bukkit.getScheduler().runTaskTimer(
                plugin,
                task -> {

                    if (!player.isOnline() || !combatTagged.containsKey(player.getUniqueId())) {
                        task.cancel();
                        return;
                    }

                    long end = combatTagged.get(player.getUniqueId());
                    long secondsLeft = (end - System.currentTimeMillis()) / 1000;

                    if (secondsLeft <= 0) {
                        combatTagged.remove(player.getUniqueId());
                        player.sendActionBar("§aDu bist nicht mehr im Combat");
                        task.cancel();
                        return;
                    }

                    player.sendActionBar("§cCombat: " + secondsLeft + "s");
                },
                0L,
                20L
        );
    }
}