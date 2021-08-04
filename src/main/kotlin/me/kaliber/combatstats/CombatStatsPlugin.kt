package me.kaliber.combatstats

import me.kaliber.combatstats.commands.HelpCommand
import me.kaliber.combatstats.commands.MainCommand
import me.kaliber.combatstats.commands.ReloadCommand
import me.kaliber.combatstats.commands.StatsCommand
import me.kaliber.combatstats.config.Config
import me.kaliber.combatstats.config.ConfigManager
import me.kaliber.combatstats.extensions.adventure
import me.kaliber.combatstats.handlers.LeaderboardHandler
import me.kaliber.combatstats.handlers.UsersHandler
import me.kaliber.combatstats.listeners.PlayerDeathListener
import me.kaliber.combatstats.listeners.PlayerJoinListener
import me.kaliber.combatstats.listeners.PlayerQuitListener
import me.kaliber.combatstats.placeholders.CombatPlaceholders
import me.kaliber.combatstats.tasks.SaveDataTask
import me.kaliber.combatstats.tasks.UpdateLeaderboardTask
import me.mattstudios.mf.base.CommandManager
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin

class CombatStatsPlugin : JavaPlugin()
{

    private val saveData = SaveDataTask(this)
    private val updateLeaderboardTask = UpdateLeaderboardTask(this)

    val conf = ConfigManager(this)
    lateinit var commandManager: CommandManager

    val usersHandler = UsersHandler(this)
    val leaderboardHandler = LeaderboardHandler(this)

    override fun onEnable()
    {
        commandManager = CommandManager(this)
        adventure = BukkitAudiences.create(this)



        loadConfig()
        usersHandler.loadUsers()

        val interval = Config.SAVE_DATA_INTERVAL.int * 20 // convert seconds to ticks
        logger.info("save-data-interval set to every ${interval / 20} seconds")
        saveData.runTaskTimer(this, 0L, interval)
        updateLeaderboardTask.runTaskTimer(this, 0L, interval)
    }

    override fun onDisable()
    {
        server.scheduler.cancelTasks(this)
        saveData.run()
        adventure.close()
    }

    private fun register()
    {
        // commands
        commandManager.register(
            MainCommand(),
            HelpCommand(),
            StatsCommand(),
            ReloadCommand(this)
        )

        // events
        listOf(
            PlayerDeathListener(this),
            PlayerJoinListener(this),
            PlayerQuitListener(this)
        ).forEach { server.pluginManager.registerEvents(it, this) }

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
        conf.reload()
    }
}
