package rest.hideko.kust

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

class KItem(private val kmaterial: KMaterial) {
    private val itemStack = kmaterial.item()
    private val itemMeta = itemStack.itemMeta

    inner class KItemHead {
        fun fromBase64(texture: String): KItem {
            if (itemStack.type == Material.SKULL_ITEM && itemMeta is SkullMeta) {
                val profile = GameProfile(UUID.randomUUID(), null)
                val property = Property("textures", texture)
                profile.properties.put("textures", property)
                try {
                    val profileField = itemMeta.javaClass.getDeclaredField("profile")
                    profileField.isAccessible = true
                    profileField.set(itemMeta, profile)
                    itemStack.itemMeta = itemMeta
                } catch (e: Exception) { e.printStackTrace() }
            }
            return this@KItem
        }
        fun fromName(name: String): KItem {
            if (itemStack.type == Material.SKULL_ITEM && itemMeta is SkullMeta) {
                itemMeta.owner = name
                itemStack.itemMeta = itemMeta
            }
            return this@KItem
        }
    }

    enum class KItemHide {
        ENCHANTS,
        POTION_EFFECTS,
        ATTRIBUTES,
        DESTROYS,
        PLACE_ON,
        UNBREAKABLE
    }

    init {
        itemStack.itemMeta = itemMeta
    }

    val head = KItemHead()
    fun displayName(name: String): KItem {
        itemMeta?.displayName = name
        itemStack.itemMeta = itemMeta
        return this
    }
    fun lore(vararg lore: String): KItem {
        itemMeta?.lore = lore.toMutableList()
        itemStack.itemMeta = itemMeta
        return this
    }
    fun hide(vararg hides: KItemHide): KItem {
        for (hide in hides) {
            val flag = ItemFlag.valueOf("HIDE_${hide.name}")
            itemMeta?.addItemFlags(flag)
        }
        itemStack.itemMeta = itemMeta
        return this
    }
    fun glowing(glowing: Boolean): KItem {
        if (glowing) {
            itemMeta?.addEnchant(org.bukkit.enchantments.Enchantment.LUCK, 1, true)
            itemMeta?.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        } else {
            itemMeta?.removeEnchant(org.bukkit.enchantments.Enchantment.LUCK)
            itemMeta?.removeItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
        itemStack.itemMeta = itemMeta
        return this
    }
    fun amount(amount: Int): KItem {
        itemStack.amount = amount
        return this
    }
    fun enchantments(vararg enchantments: Pair<org.bukkit.enchantments.Enchantment, Int>): KItem {
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
