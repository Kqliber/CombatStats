package me.kaliber.combatstats.data.base

import me.kaliber.combatstats.user.User
import java.util.UUID

interface UserStorage
{
    fun loadAll()

    fun load(uuid: UUID)

    fun saveAll()

    fun save(user: User)
}
