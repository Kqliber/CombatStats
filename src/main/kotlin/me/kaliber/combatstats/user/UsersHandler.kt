package me.kaliber.combatstats.user

import me.kaliber.combatstats.CombatStatsPlugin
import com.google.gson.GsonBuilder
import org.bukkit.OfflinePlayer
import java.util.UUID

class UsersHandler(private val plugin: CombatStatsPlugin)
{
    private val users = mutableMapOf<UUID, User>()
    private val gson = GsonBuilder().setPrettyPrinting().create()

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

    /**
     * Loads user data from file and puts into map
     */
    internal fun loadUsers()
    {
        val file = plugin.dataFolder.resolve("players")
        if (!file.exists())
        {
            file.mkdirs()
            file.createNewFile()
        }

        file.listFiles()?.forEach()
        {
            val uuid = UUID.fromString(it.nameWithoutExtension)
            users[uuid] = gson.fromJson(it.readText(), User::class.java)
        }
    }

    internal fun load(uuid: UUID)
    {
        val file = plugin.dataFolder.resolve("players").resolve("$uuid.json")

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
        val file = plugin.dataFolder.resolve("players").resolve("$uuid.json")
        if (!file.exists())
        {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        file.writeText(gson.toJson(users[uuid], User::class.java))
    }
}
