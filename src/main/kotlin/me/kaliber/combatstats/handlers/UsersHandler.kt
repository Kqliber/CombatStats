package me.kaliber.combatstats.handlers

import me.kaliber.combatstats.leaderboard.LeaderboardType
import me.kaliber.combatstats.extensions.getPlayer
import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.user.User
import com.google.gson.GsonBuilder
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
            User(uuid)
        }
    }

    operator fun get(player: OfflinePlayer): User
    {
        return get(player.uniqueId)
    }

    operator fun get(name: String): User?
    {
        return name.getPlayer()?.let { get(it) }
    }

    fun sortLeaderboard(type: LeaderboardType): List<User>
    {
        return LeaderboardSorter().sort(type, users.values.toSet())
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

        val files = userData.listFiles() ?: return
        files.forEach()
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
            users[uuid] = User(uuid)
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
