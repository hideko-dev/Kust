package rest.hideko.kust.inventory

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import rest.hideko.kust.Kust

class KInventory(private val title: String, row: Int): Listener {
    private val inventory = Bukkit.createInventory(null, row * 9, title)
    private val ignoredSlots = mutableSetOf<Int>()
    private val slotActions = mutableMapOf<Int, (KInventoryClick) -> Unit>()

    init {
        if (!Kust.instance.inventoryRegisterd) {
            Kust.plugin.server.pluginManager.registerEvents(this, Kust.plugin)
            Kust.instance.inventoryRegisterd = true
        }
    }

    fun inventory(setup: (Turn) -> Unit): KInventory {
        val turn = Turn(inventory)
        setup(turn)
        return this
    }

    inner class Turn(private val inv: Inventory) {
        fun set(slot: Int, item: ItemStack, onClick: (KInventoryClick) -> Unit) {
            inv.setItem(slot, item)
            slotActions[slot] = onClick
        }
    }

    fun ignore(vararg slots: Int): KInventory {
        ignoredSlots.addAll(slots.toSet())
        return this
    }

    // イベントハンドラー
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        // タイトルが一致するインベントリの場合のみ処理
        if (event.view.title == title) {
            // クリックされたスロットが無視されるべきスロットかチェック
            if (ignoredSlots.contains(event.slot)) return

            // クリックイベントを1回だけ処理するためにキャンセル
            event.isCancelled = true

            // KInventoryClickを作成して、対応するアクションを呼び出す
            val click = KInventoryClick(event)
            slotActions[event.slot]?.invoke(click)
        }
    }

    // プレイヤーがインベントリを開けるようにする
    fun open(player: Player): KInventory {
        player.openInventory(inventory)
        return this
    }

    // KInventoryClickクラスを追加
    class KInventoryClick(val event: InventoryClickEvent) {
        val player: Player
            get() = event.whoClicked as Player
    }
}
