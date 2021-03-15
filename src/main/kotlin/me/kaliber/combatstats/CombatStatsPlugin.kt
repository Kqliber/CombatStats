package me.kaliber.combatstats

import me.kaliber.combatstats.user.UsersHandler
import me.kaliber.combatstats.tasks.SaveDataTask
import me.kaliber.combatstats.commands.MainCommand
import me.kaliber.combatstats.commands.HelpCommand
import me.kaliber.combatstats.commands.StatsCommand
import me.kaliber.combatstats.commands.ReloadCommand
import me.kaliber.combatstats.handlers.LeaderboardHandler
import me.kaliber.combatstats.listeners.PlayerJoinListener
import me.kaliber.combatstats.listeners.PlayerQuitListener
import me.kaliber.combatstats.listeners.PlayerDeathListener
import me.kaliber.combatstats.placeholders.CombatPlaceholders
import me.kaliber.combatstats.tasks.UpdateLeaderboardTask

// import me.bristermitten.pdm.SpigotDependencyManager
import me.mattstudios.mf.base.CommandManager
import org.bukkit.plugin.java.JavaPlugin

class CombatStatsPlugin : JavaPlugin()
{

    private val saveData = SaveDataTask(this)
    private val updateLeaderboardTask = UpdateLeaderboardTask(this)

    val usersHandler = UsersHandler(this)
    val userData = dataFolder.resolve("players")
    val leaderboardHandler = LeaderboardHandler(this)

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

        val interval = config.getLong("save-data-interval") / 20
        saveData.runTaskTimer(this, 0L, interval)
        updateLeaderboardTask.runTaskTimer(this, 0L, interval)
    }

    override fun onDisable()
    {
        saveData.cancel()
        saveData.run()
        updateLeaderboardTask.cancel()
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
        listOf(PlayerDeathListener(this),
            PlayerJoinListener(this),
            PlayerQuitListener(this))
            .forEach { server.pluginManager.registerEvents(it, this) }

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
