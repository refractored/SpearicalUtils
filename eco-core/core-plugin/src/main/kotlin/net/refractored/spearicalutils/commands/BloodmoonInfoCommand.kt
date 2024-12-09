package net.refractored.spearicalutils.commands

import net.refractored.spearicalutils.SpearicalPlugin
import net.refractored.spearicalutils.exceptions.CommandErrorException
import net.refractored.spearicalutils.registry.BloodmoonRegistry
import net.refractored.spearicalutils.types.BloodmoonWorld
import net.refractored.spearicalutils.types.DaysBloodmoon
import net.refractored.spearicalutils.types.NoneBloodmoon
import net.refractored.spearicalutils.types.TimedBloodmoon
import net.refractored.spearicalutils.util.MessageUtil.getStringPrefixed
import net.refractored.spearicalutils.util.MessageUtil.miniToComponent
import org.bukkit.World
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Optional
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission
import revxrsal.commands.bukkit.player
import java.time.Duration

class BloodmoonInfoCommand {
    @CommandPermission("bloodmoon.command.info")
    @Description("Info about a bloodmoon.")
    @Command("bloodmoon info")
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
        if (bloodmoonWorld.status != BloodmoonWorld.BloodmoonStatus.INACTIVE) {
            actor.reply(
                SpearicalPlugin.instance.langYml
                    .getStringPrefixed("messages.bloodmoon-info-active")
                    .replace("%world%", world.name)
                    .miniToComponent()
            )
            return
        }
        when (bloodmoonWorld) {
            is TimedBloodmoon -> {
                val timeframe = Duration.ofMillis(bloodmoonWorld.millisUntilActivation - System.currentTimeMillis())
                actor.reply(
                    SpearicalPlugin.instance.langYml
                        .getStringPrefixed("messages.bloodmoon-info-time")
                        .replace("%world%", world.name)
                        .replace("%status%", bloodmoonWorld.status.toString())
                        .replace("%hours%", timeframe.toHours().toString())
                        .replace("%minutes%", timeframe.toMinutesPart().toString())
                        .replace("%seconds%", timeframe.toSecondsPart().toString())
                        .miniToComponent()
                )
                return
            }
            is DaysBloodmoon -> {
                actor.reply(
                    SpearicalPlugin.instance.langYml
                        .getStringPrefixed("messages.bloodmoon-info-days")
                        .replace("%world%", world.name)
                        .replace("%status%", bloodmoonWorld.status.toString())
                        .replace("%days%", bloodmoonWorld.dayCount.toString())
                        .miniToComponent()
                )
                return
            }
            is NoneBloodmoon -> {
                actor.reply(
                    SpearicalPlugin.instance.langYml
                        .getStringPrefixed("messages.bloodmoon-info-none")
                        .replace("%world%", world.name)
                        .replace("%status%", bloodmoonWorld.status.toString())
                        .miniToComponent()
                )
                return
            }
        }
    }
}
