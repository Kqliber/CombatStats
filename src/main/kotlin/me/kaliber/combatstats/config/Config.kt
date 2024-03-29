package me.kaliber.combatstats.config

import me.kaliber.combatstats.CombatStatsPlugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

enum class Config(val path: String, val default: Any)
{

    SAVE_DATA_INTERVAL("settings.save-data-interval", 300),
    STORAGE_METHOD("settings.storage-method", "json"),
    PLAYER_NOT_FOUND("messages.player-not-found", "&7Player not found."),
    PLAYER_COMMAND_ONLY("messages.player-command-only", "&7Only players can execute this command."),
    NO_PERMISSION("messages.no-permission", "&cNo permission."),
    WRONG_USAGE("messages.command-wrong-usage", "&cWrong Usage! &7/cstats help"),
    MAIN_COMMAND("messages.main-command", mainCommand),
    HELP_COMMAND("messages.help-command", helpCommand),
    STATS_COMMAND("messages.stats-command", statsCommand),
    RELOAD_COMMAND("messages.reload-command", "&bConfig reloaded."),
    REWARDS_ENABLED("rewards.enabled", true),
    KILLER_COMMANDS("rewards.killer.commands", killerCommands),
    KILLER_MESSAGES("rewards.killer.messages", killerMessages),
    PLAYER_COMMANDS("rewards.player.commands", playerCommands),
    PLAYER_MESSAGES("rewards.player.messages", playerMessages);

    private var value: Any? = null

    val list: List<String>
        get() = if (value is String) listOf(value as String) else value as List<String>

    val string: String
        get() = value as String

    val boolean: Boolean
        get() = value as Boolean

    val int: Long
        get() = (value as Int).toLong()

    fun load(config: FileConfiguration)
    {
        value = config[path]
    }
}

private val plugin = JavaPlugin.getPlugin(CombatStatsPlugin::class.java)

private val mainCommand = listOf(
    "",
    "&bCombatStats &7version &f${plugin.description.version}",
    "&7Created by: &bKaliber",
    "")

private val helpCommand = listOf(
    "",
    "&9CombatStats Help &8(&7v${plugin.description.version}&8)",
    "&b/combatstats &8- &7Display plugin information",
    "&b/combatstats help &8- &7Display plugin commands",
    "&b/combatstats reload &8- &7Reload configuration file",
    "&b/combatstats stats [player] &8- &7Display player statistics",
    "")

private val statsCommand = listOf(
    "",
    "&9%player_name%'s Stats &8- ",
    "&bKillstreak&7: &f%combatstats_killstreak%",
    "")

private val killerCommands = listOf("give %player_name% diamond 10")
private val killerMessages = listOf("&fYou killed &b%dead%&f!")
private val playerCommands = listOf("eco take %player_name%")
private val playerMessages = listOf("&fYou were killed by &c%killer%")
