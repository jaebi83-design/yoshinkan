package com.yoshinkan.utility

import com.yoshinkan.model.Attack
import com.yoshinkan.model.Technique
import com.yoshinkan.model.EnergyFlow

object DataInitializer {
    fun getInitialAttacks(): List<Attack> {
        return listOf(
            Attack(1, "Kata Mochi", "Kata Mochi"),
            Attack(2, "Katate Ayagyaku Mochi", "Katate Ayagyaku Mochi"),
            Attack(3, "Katate Ayajun Mochi", "Katate Ayajun Mochi"),
            Attack(4, "Katate Mochi", "Katate Mochi"),
            Attack(5, "Mune Mochi", "Mune Mochi"),
            Attack(6, "RyoKata Mochi", "RyoKata Mochi"),
            Attack(7, "RyoSode Mochi", "RyoSode Mochi"),
            Attack(8, "Ryote Mochi", "Ryote Mochi"),
            Attack(9, "Shomen Uchi", "Shomen Uchi"),
            Attack(10, "Sode Mochi", "Sode Mochi"),
            Attack(11, "Suigetsu Zuki", "Suigetsu Zuki"),
            Attack(12, "Ushiro Waza Eri Mochi", "Ushiro Waza Eri Mochi"),
            Attack(13, "Ushiro Waza Katate Eri Mochi", "Ushiro Waza Katate Eri Mochi"),
            Attack(14, "Ushiro Waza RyoHiji Mochi", "Ushiro Waza RyoHiji Mochi"),
            Attack(15, "Ushiro Waza Ryote Mochi", "Ushiro Waza Ryote Mochi"),
            Attack(16, "Yokomen Uchi", "Yokomen Uchi")
        )
    }

    fun getInitialTechniques(): List<Technique> {
        return listOf(
            Technique(1, "Hiji Shime", "Hiji Shime"),
            Technique(2, "Ikkajo Osae", "Ikkajo Osae"),
            Technique(3, "Irimi Tsuki", "Irimi Tsuki"),
            Technique(4, "Nikajo Osae", "Nikajo Osae"),
            Technique(5, "Sankajo Osae", "Sankajo Osae"),
            Technique(6, "Yonkajo Osae", "Yonkajo Osae"),
            Technique(7, "Irimi Nage", "Irimi Nage"),
            Technique(8, "Kotegaeshi Osae", "Kotegaeshi Osae"),
            Technique(9, "Hiji Ate", "Hiji Ate"),
            Technique(10, "Shiho Nage", "Shiho Nage"),
            Technique(11, "Sokumen Irimi Nage", "Sokumen Irimi Nage"),
            Technique(12, "TenChi Nage", "TenChi Nage")
        )
    }

    fun getInitialEnergyFlows(): List<EnergyFlow> {
        return listOf(
            EnergyFlow(1, "Ichi", "Ichi", "First energy flow"),
            EnergyFlow(2, "Ni", "Ni", "Second energy flow")
        )
    }
}
