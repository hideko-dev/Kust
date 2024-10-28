package rest.hideko.kust

import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Kust(private val plugin: JavaPlugin) {
    companion object {
        lateinit var plugin: JavaPlugin
    }
    init {
        Companion.plugin = plugin
    }
    fun events(vararg listeners: Listener): Kust {
        for (listener in listeners) {
            plugin.server.pluginManager.registerEvents(listener, plugin)
        }
        return this
    }
    fun commands(vararg commands: Pair<String, CommandExecutor>): Kust {
        for (command in commands) {
            val pluginCommand = plugin.getCommand(command.first)
            pluginCommand.executor = command.second
        }
        return this
    }
}