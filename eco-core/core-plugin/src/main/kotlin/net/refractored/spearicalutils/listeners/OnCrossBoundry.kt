package net.refractored.spearicalutils.listeners

import com.sk89q.worldedit.util.Location
import com.sk89q.worldguard.LocalPlayer
import com.sk89q.worldguard.protection.ApplicableRegionSet
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import com.sk89q.worldguard.session.MoveType
import com.sk89q.worldguard.session.Session
import com.sk89q.worldguard.session.handler.Handler
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.PluginManager

class Entry(session: Session?) :
    Handler(session),
    Listener {
    val pm: PluginManager = Bukkit.getPluginManager()

    class Factory : Handler.Factory<Entry?>() {
        override fun create(session: Session?): Entry = Entry(session)
    }

    override fun onCrossBoundary(player: LocalPlayer, from: Location?, to: Location?, toSet: ApplicableRegionSet?, entered: Set<ProtectedRegion?>, left: Set<ProtectedRegion?>, moveType: MoveType?): Boolean {
        if (entered.isEmpty()) return true

        if (player.world) {
            return true
        }
    }

    companion object {
        val factory: Factory = Factory()
    }
}
