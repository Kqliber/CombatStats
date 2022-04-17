package me.kaliber.combatstats.user

import java.util.UUID

/**
 * This class represents an old User, see [User] for the new User details.
 * it is used to convert old user data into new user data.
 * This class will be removed when users convert their data.
 */
data class OldUser(
val uuid: UUID,
var killstreak: Int = 0,
var highestKillstreak: Int = 0,
var lastKill: String = "",
var kills: Int = 0,
var deaths: Int = 0
)
