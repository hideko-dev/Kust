package rest.hideko.kust.inventory

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import rest.hideko.kust.Kust

class KInventory(private val title: String, row: Int): Listener {
    private val inventory = Bukkit.createInventory(null, row * 9, title)
    private val ignoredSlots = mutableSetOf<Int>()
    private val slotActions = mutableMapOf<Int, (InventoryClickEvent) -> Unit>()

    init {
        Kust.plugin.server.pluginManager.registerEvents(this, Kust.plugin)
    }
    fun inventory(setup: (Turn) -> Unit): KInventory {
        val turn = Turn(inventory)
        setup(turn)
        return this
    }
    inner class Turn(private val inv: Inventory) {
        fun set(slot: Int, item: ItemStack, onClick: (InventoryClickEvent) -> Unit) {
            inv.setItem(slot, item)
            slotActions[slot] = onClick
        }
    }
    fun ignore(vararg slots: Int): KInventory {
        ignoredSlots.addAll(slots.toSet())
        return this
    }
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.view.title == title) {
            if (ignoredSlots.contains(event.slot)) return
            event.isCancelled = true
            slotActions[event.slot]?.invoke(event)
        }
    }
    fun open(player: Player): KInventory {
        player.openInventory(inventory)
        return this
    }
}
