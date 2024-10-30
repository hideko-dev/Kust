package rest.hideko.kust

//import com.mojang.authlib.GameProfile
//import com.mojang.authlib.properties.Property
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

class KItemStack(private val kmaterial: KMaterial) {
    private val itemStack = kmaterial.item()
    private val itemMeta = itemStack.itemMeta
    inner class Head {
//        fun base64(texture: String): KItemStack {
//            if (itemStack.type == Material.SKULL_ITEM && itemMeta is SkullMeta) {
//                val profile = GameProfile(UUID.randomUUID(), null)
//                val property = Property("textures", texture)
//                profile.properties.put("textures", property)
//                try {
//                    val profileField = itemMeta.javaClass.getDeclaredField("profile")
//                    profileField.isAccessible = true
//                    profileField.set(itemMeta, profile)
//                    itemStack.itemMeta = itemMeta
//                } catch (e: Exception) { e.printStackTrace() }
//            }
//            return this@KItemStack
//        }
        fun ownerName(name: String): KItemStack {
            if (itemStack.type == Material.SKULL_ITEM && itemMeta is SkullMeta) {
                itemMeta.owner = name
                itemStack.itemMeta = itemMeta
            }
            return this@KItemStack
        }
    }

    init {
        itemStack.itemMeta = itemMeta
    }
    val head = Head()
    fun displayName(name: String): KItemStack {
        itemMeta?.displayName = name
        itemStack.itemMeta = itemMeta
        return this
    }
    fun lore(vararg lore: String): KItemStack {
        itemMeta?.lore = lore.toMutableList()
        itemStack.itemMeta = itemMeta
        return this
    }
    fun isGlowing(glowing: Boolean): KItemStack {
        if (glowing) {
            itemMeta?.addEnchant(org.bukkit.enchantments.Enchantment.LUCK, 1, true)
            itemMeta?.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS)
        } else {
            itemMeta?.removeEnchant(org.bukkit.enchantments.Enchantment.LUCK)
            itemMeta?.removeItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS)
        }
        itemStack.itemMeta = itemMeta
        return this
    }
    fun amount(amount: Int): KItemStack {
        itemStack.amount = amount
        return this
    }
    fun enchantments(vararg enchantments: Pair<org.bukkit.enchantments.Enchantment, Int>): KItemStack {
        for ((enchantment, level) in enchantments) {
            itemMeta?.addEnchant(enchantment, level, true)
        }
        itemStack.itemMeta = itemMeta
        return this
    }
    fun build(): ItemStack {
        return itemStack
    }
}
