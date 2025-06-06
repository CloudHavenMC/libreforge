package com.willfp.libreforge.effects.impl

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.NoCompileData
import com.willfp.libreforge.arguments
import com.willfp.libreforge.effects.Effect
import com.willfp.libreforge.getDoubleFromExpression
import com.willfp.libreforge.getIntFromExpression
import com.willfp.libreforge.plugin
import com.willfp.libreforge.triggers.TriggerData
import com.willfp.libreforge.triggers.TriggerParameter

object EffectCreateExplosion : Effect<NoCompileData>("create_explosion") {
    override val parameters = setOf(
        TriggerParameter.LOCATION
    )

    override val arguments = arguments {
        require("amount", "You must specify the amount of explosions!")
        require("power", "You must specify the explosion power!")
    }

    override fun onTrigger(config: Config, data: TriggerData, compileData: NoCompileData): Boolean {
        val location = data.location ?: return false
        val world = location.world ?: return false

        val amount = config.getIntFromExpression("amount", data)
        val power = config.getDoubleFromExpression("power", data)
        val setFire = config.getBool("set_fire")
        val destroysBlocks = config.getBool("destroys_blocks")

        for (i in 1..amount) {
            plugin.scheduler.runLater(i.toLong()) {
                world.createExplosion(location.x, location.y, location.z, power.toFloat(), setFire, destroysBlocks)
            }
        }

        return true
    }
}
