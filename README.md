# CombatStats
###### TO ADD: Short description

### Features:
* [PlaceholderAPI](https://www.spigotmc.org/resources/6245/) support.
* Lots of placeholders.
* 99% Customizable.
* Leaderboards
* Rewards

### Commands:
Command | Permission | Description
--------|------------|------------
/cstats | combatstats.info | Display plugin information
/cstats help | combatstats.help | Display a list of available commands
/cstats stats \[player] | combatstats.stats | Display player statistics
/cstats reload | combatstats.admin | Reload configuration file

### Placeholders:
Placeholder | Description
------------|------------
%combatstats_kills% | returns the player's kills.
%combatstats_deaths% | returns the player's deaths.
%combatstats_kdr% | returns the player's KD ratio.
%combatstats_killstreak% | returns the player's current killstreak
%combatstats_highestkillstreak% | returns the player's highest killstreak.
%combatstats_last_kill% | returns the last player they killed.
%combatstats_kdr_rounded_[decimal-places]% | returns the player's KDR rounded. If there is no decimal place specified, the placeholder will default to 2 decimal places
%combatstats_placement_\<type\>_\<player\>% | returns the player's position in the leaderboard type
%combatstats_top_\<type\>_\<info\>_\<position\>% | returns the name/value who is at the position specified in the leaderboard type


```
<TYPE> - specifies which leaderboard to use: kills/killstreak/highestkillstreak/kdr
<INFO> - specifies which information to display: name/value (value being either the kills, killstreak or highestkillstreak amount or kdr)
<POSITION> - the position of the leaderboard to get

Placeholders with <> - required fields
Placeholders with [] - optional fields
```

### Internal Placeholders:
Placeholder | Description
------------|------------
%killer% | get the killer's name
%dead% | get the player's name who died
