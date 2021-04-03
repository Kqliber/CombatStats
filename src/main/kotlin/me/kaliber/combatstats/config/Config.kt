package me.kaliber.combatstats.config

import org.bukkit.configuration.file.FileConfiguration

enum class Config(val path: String, val default: Any)
{

    STATS_COMMAND("stats-command", listOf("&9%player_name%'s Stats &8- &bKillstreak&7: &f%combatstats_killstreak%")),
    SAVE_DATA_INTERVAL("save-data-interval", 300),
    REWARDS_ENABLED("rewards.enabled", true),
    KILLER_COMMANDS("rewards.killer.commands", listOf("give %player_name% diamond 10")),
    KILLER_MESSAGES("rewards.killer.messages", listOf("&fYou killed &b%dead%&f!")),
    PLAYER_COMMANDS("rewards.player.commands", listOf("eco take %player_name%")),
    PLAYER_MESSAGES("rewards.player.messages", listOf("&fYou were killed by &c%killer%"));

    private var value: Any? = null

    val list: List<String>
        get() = value as List<String>

    val boolean: Boolean
        get() = value as Boolean

    val int: Long
        get() = (value as Int).toLong()

    fun load(config: FileConfiguration)
    {
        value = config[path]
    }
}
