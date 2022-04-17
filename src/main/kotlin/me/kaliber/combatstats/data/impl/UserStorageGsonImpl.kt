package me.kaliber.combatstats.data.impl

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import me.kaliber.combatstats.CombatStatsPlugin
import me.kaliber.combatstats.data.base.UserStorage
import me.kaliber.combatstats.extensions.createDirs
import me.kaliber.combatstats.extensions.createFile
import me.kaliber.combatstats.user.User
import me.kaliber.combatstats.user.OldUser
import java.io.File
import java.util.UUID

class UserStorageGsonImpl(private val plugin: CombatStatsPlugin) : UserStorage
{

    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val folder = plugin.dataFolder.resolve("players").createDirs()

    override fun loadAll()
    {
        val files = folder.listFiles() ?: return
        files.map(::toUUID).forEach(::load)
    }

    override fun load(uuid: UUID)
    {
        val file = folder.resolve("$uuid.json")
        if (!file.exists())
        {
            return plugin.usersHandler.set(uuid, User(uuid))
        }
        try
        {
            plugin.usersHandler[uuid] = gson.fromJson(file.readText(), User::class.java)
        } catch (exception: JsonSyntaxException) // convert old user data to new data
        {
            val old = gson.fromJson(file.readText(), OldUser::class.java)
            val newKills = buildList()
            {
                for(i in 1..old.kills) add(0L)
            }.toMutableList()
            val newUser = User(uuid, old.highestKillstreak, old.killstreak, old.lastKill, newKills, old.deaths)
            plugin.usersHandler[uuid] = newUser
        }
    }

    override fun saveAll()
    {
        plugin.usersHandler.values.forEach(::save)
    }

    override fun save(user: User)
    {
        val file = folder.resolve("${user.uuid}.json").createFile()
        file.writeText(gson.toJson(user, User::class.java))
    }

    private fun toUUID(file: File) = UUID.fromString(file.nameWithoutExtension)

}
