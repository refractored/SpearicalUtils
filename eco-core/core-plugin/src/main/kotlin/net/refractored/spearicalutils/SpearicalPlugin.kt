package net.refractored.spearicalutils

import com.sk89q.worldguard.WorldGuard
import com.willfp.libreforge.loader.LibreforgePlugin
import net.refractored.spearicalutils.commands.BloodmoonInfoCommand
import net.refractored.spearicalutils.commands.BloodmoonReloadCommand
import net.refractored.spearicalutils.commands.BloodmoonStartCommand
import net.refractored.spearicalutils.commands.BloodmoonStopCommand
import net.refractored.spearicalutils.exceptions.CommandErrorHandler
import net.refractored.spearicalutils.listeners.OnPlayerJoin
import net.refractored.spearicalutils.listeners.OnPlayerTeleport
import org.black_ixx.playerpoints.PlayerPoints
import org.black_ixx.playerpoints.PlayerPointsAPI
import revxrsal.commands.bukkit.BukkitCommandHandler

class SpearicalPlugin : LibreforgePlugin() {
    lateinit var handler: BukkitCommandHandler
    lateinit var worldguard: WorldGuard
    lateinit var ppAPI: PlayerPointsAPI

    override fun handleEnable() {
        instance = this

        worldguard = WorldGuard.getInstance()

        ppAPI = PlayerPoints.getInstance().getAPI()

        handler = BukkitCommandHandler.create(this)

        handler.setExceptionHandler(CommandErrorHandler())

        handler.register(BloodmoonStartCommand())
        handler.register(BloodmoonStopCommand())
        handler.register(BloodmoonReloadCommand())
        handler.register(BloodmoonInfoCommand())
    }

    override fun handleAfterLoad() {
        // Registered after to prevent issues.
        eventManager.registerListener(OnPlayerTeleport())
        eventManager.registerListener(OnPlayerJoin())
    }

    private var registeredBrigadier = false

    override fun handleReload() {
        // Its done this way cause handle reload is called after handleEnable.
        if (!registeredBrigadier) {
            handler.registerBrigadier()
            registeredBrigadier = true
        }
    }

    override fun handleDisable() {
        if (this::handler.isInitialized) {
            handler.unregisterAllCommands()
        }
    }

    companion object {
        @JvmStatic
        lateinit var instance: SpearicalPlugin
            private set
    }
}
