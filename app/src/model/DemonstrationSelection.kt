package com.yoshinkan.model

data class DemonstrationSelection(
    val attack: Attack? = null,
    val technique: Technique? = null,
    val energyFlow: EnergyFlow? = null
) {
    fun isComplete(): Boolean {
        return attack != null && technique != null && energyFlow != null
    }

    fun getVideoFileName(): String? {
        if (!isComplete()) return null

        val attackName = attack!!.name.uppercase().replace(" ", "_")
        val techniqueName = technique!!.name.uppercase().replace(" ", "_")
        val energyName = energyFlow!!.name.uppercase()

        return "${attackName}_${techniqueName}_${energyName}.mp4"
    }
}
