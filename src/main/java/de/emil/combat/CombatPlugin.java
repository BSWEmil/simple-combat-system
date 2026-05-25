package de.emil.combat;

import de.emil.combat.listener.*;
import org.bukkit.plugin.java.JavaPlugin;

public class CombatPlugin extends JavaPlugin {

    private CombatManager combatManager;

    @Override
    public void onEnable() {

        saveDefaultConfig(); // 🔥 Config erstellen

        combatManager = new CombatManager(this);

        getServer().getPluginManager().registerEvents(new DamageListener(combatManager), this);
        getServer().getPluginManager().registerEvents(new QuitListener(combatManager), this);
        getServer().getPluginManager().registerEvents(new DeathListener(combatManager), this);
        getServer().getPluginManager().registerEvents(new CommandListener(combatManager), this);
    }
}