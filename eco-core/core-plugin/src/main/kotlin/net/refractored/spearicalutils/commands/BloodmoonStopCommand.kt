package net.refractored.spearicalutils.commands

import net.refractored.spearicalutils.SpearicalPlugin
import net.refractored.spearicalutils.events.BloodmoonStopEvent.StopCause
import net.refractored.spearicalutils.exceptions.CommandErrorException
import net.refractored.spearicalutils.registry.BloodmoonRegistry
import net.refractored.spearicalutils.types.BloodmoonWorld
import net.refractored.spearicalutils.util.MessageUtil.getStringPrefixed
import net.refractored.spearicalutils.util.MessageUtil.miniToComponent
import org.bukkit.World
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Optional
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission
import revxrsal.commands.bukkit.player

class BloodmoonStopCommand {
    @CommandPermission("bloodmoon.admin.bloodmoon.stop")
    @Description("Stops a bloodmoon.")
    @Command("bloodmoon stop")
    @Suppress("UNUSED")
    fun execute(
        actor: BukkitCommandActor,
        @Optional world: World = actor.player.world
    ) {
        val bloodmoonWorld =
            BloodmoonRegistry.getWorld(world.name) ?: throw CommandErrorException(
                SpearicalPlugin.instance.langYml
                    .getStringPrefixed("messages.not-a-bloodmoon-world")
                    .miniToComponent()
            )
        if (bloodmoonWorld.status == BloodmoonWorld.BloodmoonStatus.INACTIVE) {
            throw CommandErrorException(
                SpearicalPlugin.instance.langYml
                    .getStringPrefixed("messages.bloodmoon-not-active")
                    .miniToComponent()
            )
        }
        bloodmoonWorld.deactivate(StopCause.COMMAND)
        actor.reply(
            SpearicalPlugin.instance.langYml
                .getStringPrefixed("messages.bloodmoon-deactivated")
                .miniToComponent()
        )
    }
}
