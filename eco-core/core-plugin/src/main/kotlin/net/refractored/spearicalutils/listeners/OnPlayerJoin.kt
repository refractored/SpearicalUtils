package net.refractored.spearicalutils.listeners

import com.sk89q.worldedit.bukkit.BukkitAdapter
import net.refractored.spearicalutils.SpearicalPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class OnPlayerJoin : Listener {
    @EventHandler
    fun execute(event: PlayerJoinEvent) {
        SpearicalPlugin.instance.worldguard

        // Get the WorldGuard plugin instance
        val worldGuard = WorldGuardPlugin.inst() ?: return emptyList()

        // Get the region manager for the player's world
        val regionContainer = worldGuard.platform.regionContainer
        val regionManager: RegionManager? = regionContainer[BukkitAdapter.adapt(player.world)]
        regionManager ?: return emptyList()

        // Get the ApplicableRegionSet for the player's location
        val location = BukkitAdapter.adapt(player.location)
        val regionSet: ApplicableRegionSet = regionManager.getApplicableRegions(location)

        // Return the list of region IDs
        return regionSet.regions.map { it.id }
    }
}
