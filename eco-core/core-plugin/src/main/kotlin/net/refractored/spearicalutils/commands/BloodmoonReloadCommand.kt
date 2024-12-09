package net.refractored.spearicalutils.commands

import net.refractored.spearicalutils.SpearicalPlugin
import net.refractored.spearicalutils.exceptions.CommandErrorException
import net.refractored.spearicalutils.registry.BloodmoonRegistry
import net.refractored.spearicalutils.util.MessageUtil.getStringPrefixed
import net.refractored.spearicalutils.util.MessageUtil.miniToComponent
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Optional
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

class BloodmoonReloadCommand {
    @CommandPermission("bloodmoon.admin.reload")
    @Description("Reloads the plugin.")
    @Command("bloodmoon reload")
    @Suppress("UNUSED")
    fun execute(
        actor: BukkitCommandActor,
        @Optional confirm: Boolean = false
    ) {
        if (!confirm && BloodmoonRegistry.getActiveWorlds().isNotEmpty()) {
            throw CommandErrorException(
                SpearicalPlugin.instance.langYml
                    .getStringPrefixed("messages.confirm-reload")
                    .miniToComponent()
            )
        }
        SpearicalPlugin.instance.reload()
        actor.reply(
            SpearicalPlugin.instance.langYml
                .getStringPrefixed("messages.reloaded")
                .miniToComponent()
        )
    }
}
