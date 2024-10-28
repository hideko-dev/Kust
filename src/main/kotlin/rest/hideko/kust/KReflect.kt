package rest.hideko.kust

import org.bukkit.entity.Player

class KReflect {
    companion object {
        fun packet(player: Player, packet: Any?) {
            try {
                val handle = player.javaClass.getMethod("getHandle").invoke(player)
                val playerConnection = handle.javaClass.getField("playerConnection")[handle]
                playerConnection.javaClass.getMethod("sendPacket", Class.forName("net.minecraft.server.v1_8_R3.Packet"))
                    .invoke(playerConnection, packet)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        fun nms(name: String): Class<*> {
            return Class.forName("net.minecraft.server.v1_8_R3.${name}")
        }
    }
}