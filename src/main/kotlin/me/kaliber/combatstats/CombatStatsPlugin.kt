package me.kaliber.combatstats

import me.kaliber.combatstats.user.UsersHandler
import me.kaliber.combatstats.tasks.SaveDataTask
import me.kaliber.combatstats.commands.MainCommand
import me.kaliber.combatstats.commands.HelpCommand
import me.kaliber.combatstats.commands.StatsCommand
import me.kaliber.combatstats.commands.ReloadCommand
import me.kaliber.combatstats.listeners.PlayerJoinListener
import me.kaliber.combatstats.listeners.PlayerQuitListener
import me.kaliber.combatstats.listeners.PlayerDeathListener
import me.kaliber.combatstats.placeholders.CombatPlaceholders

// import me.bristermitten.pdm.SpigotDependencyManager
import me.mattstudios.mf.base.CommandManager
import org.bukkit.plugin.java.JavaPlugin

class CombatStatsPlugin : JavaPlugin()
{

    private val saveData = SaveDataTask(this)
    val usersHandler = UsersHandler(this)

    /**
    override fun onLoad()
    {
        SpigotDependencyManager.of(this).loadAllDependencies().join()
    }
    **/

    override fun onEnable()
    {
        register()

        loadConfig()
        usersHandler.loadUsers()
        saveData.runTaskTimer(this, 0L, config.getLong("save-data-interval") / 20)
    }

    override fun onDisable()
    {
        saveData.cancel()
        saveData.run()
    }

    private fun register()
    {
        // commands
        CommandManager(this).register(
            MainCommand(this),
            HelpCommand(this),
            StatsCommand(this),
            ReloadCommand(this)
        )

        // events
        server.pluginManager.registerEvents(PlayerDeathListener(this), this)
        server.pluginManager.registerEvents(PlayerJoinListener(this), this)
        server.pluginManager.registerEvents(PlayerQuitListener(this), this)

        // placeholders
        CombatPlaceholders(this).register()
    }

    private fun loadConfig()
    {
        val file = dataFolder.resolve("config.yml")

        if (!file.exists())
        {
            file.parentFile.mkdirs()
            file.createNewFile()
            config.options().copyDefaults(true)
        }
        saveConfig()
        reloadConfig()
    }
}
