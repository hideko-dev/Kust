package rest.hideko.kust.text

import org.bukkit.entity.Player
import rest.hideko.kust.KReflect.Companion.nms
import rest.hideko.kust.KReflect.Companion.packet

class KActionBar {
    companion object {
        fun send(ply: Player, message: String) {
            if (!ply.isOnline) return
            try {
                var packet: Any
                val packetPlayOutChatClass = nms("PacketPlayOutChat")
                val chatComponentTextClass = nms("ChatComponentText")
                val iChatBaseComponentClass = nms("IChatBaseComponent")
                try {
                    val chatClass = nms("ChatMessageType")
                    val chatTypes: Array<out Any>? = chatClass.getEnumConstants()
                    var chatType: Any? = null
                    if (chatTypes != null) for (o in chatTypes) if (o.toString() == "GAME_INFO") chatType = o
                    val chatComponentText = chatComponentTextClass.getConstructor(*arrayOf<Class<*>>(String::class.java)).newInstance(message)
                    packet = packetPlayOutChatClass.getConstructor(*arrayOf(iChatBaseComponentClass, chatClass)).newInstance(chatComponentText, chatType)
                } catch (e: ClassNotFoundException) {
                    val chatComponentText = chatComponentTextClass.getConstructor(*arrayOf<Class<*>>(String::class.java)).newInstance(message)
                    packet = packetPlayOutChatClass.getConstructor(*arrayOf(iChatBaseComponentClass, Byte::class.javaPrimitiveType)).newInstance(chatComponentText, 2.toByte())
                }
                packet(ply, packet)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        fun bye(player: Player) {
            send(player, "")
        }
    }
}