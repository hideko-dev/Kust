package rest.hideko.kust.text

import org.bukkit.entity.Player
import rest.hideko.kust.KReflect.Companion.nms
import rest.hideko.kust.KReflect.Companion.packet
import java.lang.reflect.Constructor

class KTitle {
    companion object {
        fun send(player: Player, title: String?, subtitle: String?, fadeIn: Int = 10, stay: Int = 30, fadeOut: Int = 10) {
            if (!player.isOnline) return
            try {
                var field: Any
                var chatTitle: Any
                var chatSubtitle: Any
                var subtitleConstructor: Constructor<*>
                var titlePacket: Any
                var subtitlePacket: Any
                if (title != null) {
                    field = nms("PacketPlayOutTitle").declaredClasses[0].getField("TIMES").get(null)
                    chatTitle = nms("IChatBaseComponent").declaredClasses[0].getMethod("a", String::class.java).invoke(null, "{\"text\":\"$title\"}")
                    subtitleConstructor = nms("PacketPlayOutTitle").getConstructor(nms("PacketPlayOutTitle").declaredClasses[0], nms("IChatBaseComponent"), Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                    titlePacket = subtitleConstructor.newInstance(field, chatTitle, fadeIn, stay, fadeOut)
                    packet(player, titlePacket)
                    field = nms("PacketPlayOutTitle").declaredClasses[0].getField("TITLE").get(null)
                    chatTitle = nms("IChatBaseComponent").declaredClasses[0].getMethod("a", String::class.java).invoke(null, "{\"text\":\"$title\"}")
                    subtitleConstructor = nms("PacketPlayOutTitle").getConstructor(nms("PacketPlayOutTitle").declaredClasses[0], nms("IChatBaseComponent"))
                    titlePacket = subtitleConstructor.newInstance(field, chatTitle)
                    packet(player, titlePacket)
                }
                if (subtitle != null) {
                    field = nms("PacketPlayOutTitle").declaredClasses[0].getField("TIMES").get(null)
                    chatSubtitle = nms("IChatBaseComponent").declaredClasses[0].getMethod("a", String::class.java).invoke(null, "{\"text\":\"$subtitle\"}")
                    subtitleConstructor = nms("PacketPlayOutTitle").getConstructor(nms("PacketPlayOutTitle").declaredClasses[0], nms("IChatBaseComponent"), Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                    subtitlePacket = subtitleConstructor.newInstance(field, chatSubtitle, fadeIn, stay, fadeOut)
                    packet(player, subtitlePacket)
                    field = nms("PacketPlayOutTitle").declaredClasses[0].getField("SUBTITLE").get(null)
                    chatSubtitle = nms("IChatBaseComponent").declaredClasses[0].getMethod("a", String::class.java).invoke(null, "{\"text\":\"$subtitle\"}")
                    subtitleConstructor = nms("PacketPlayOutTitle").getConstructor(nms("PacketPlayOutTitle").declaredClasses[0], nms("IChatBaseComponent"), Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                    subtitlePacket = subtitleConstructor.newInstance(field, chatSubtitle, fadeIn, stay, fadeOut)
                    packet(player, subtitlePacket)
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
        fun bye(player: Player) {
            send(player, "", "", 0, 0, 0)
        }
    }
}