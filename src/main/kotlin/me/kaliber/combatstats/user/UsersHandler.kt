package me.kaliber.combatstats.user

import me.kaliber.combatstats.CombatStatsPlugin
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.UUID

class UsersHandler(plugin: CombatStatsPlugin)
{

    private val users = mutableMapOf<UUID, User>()
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val userData = plugin.dataFolder.resolve("players")

    operator fun get(uuid: UUID): User
    {
        return users.getOrPut(uuid)
        {
            User(uuid, 0, "")
        }
    }

    operator fun get(player: OfflinePlayer): User
    {
        return get(player.uniqueId)
    }

    operator fun get(name: String): User
    {
        return get(Bukkit.getOfflinePlayer(name))
    }

    fun getKills(): List<User>
    {
        return users.values.distinct().sortedByDescending { it.kills() }
    }

    fun getKillstreaks(): List<User>
    {
        return users.values.distinct().sortedByDescending { it.killstreak }
    }

    /**
     * Loads user data from file and puts into map
     */
    internal fun loadUsers()
    {
        if (!userData.exists())
        {
            userData.mkdirs()
            userData.createNewFile()
        }

        userData.listFiles()?.forEach()
        {
            val uuid = UUID.fromString(it.nameWithoutExtension)
            users[uuid] = gson.fromJson(it.readText(), User::class.java)
        }
    }

    internal fun load(uuid: UUID)
    {
        val file = userData.resolve("$uuid.json")

        if (!file.exists())
        {
            users[uuid] = User(uuid, 0, "")
            return
        }

        users[uuid] = gson.fromJson(file.readText(), User::class.java)
    }

    /**
     * Stores users from map into file
     */
    internal fun saveUsers()
    {
        users.keys.forEach(::save)
    }

    internal fun save(uuid: UUID)
    {
        val file = userData.resolve("$uuid.json")
        if (!file.exists())
        {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        file.writeText(gson.toJson(users[uuid], User::class.java))
    }
}
