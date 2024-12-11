package net.refractored.spearicalutils.listeners

import net.refractored.spearicalutils.SpearicalPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

class OnPlayerTeleport : Listener {
    @EventHandler
    fun execute(event: PlayerTeleportEvent) {
        val strings = SpearicalPlugin.instance.configYml.getStrings("AfkRegions")

        val worldGuard = SpearicalPlugin.instance.worldguard

        val regionContainer = worldGuard.platform.regionContainer
    }
}
