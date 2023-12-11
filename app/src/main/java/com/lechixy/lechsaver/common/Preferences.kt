package com.lechixy.lechsaver.common

import com.chibatching.kotpref.KotprefModel

object Preferences : KotprefModel() {
    override val commitAllPropertiesByDefault: Boolean = true

    var instagramSource by intPref(key = "instagramSource", default = 1)
    var tiktokSource by intPref(key = "tiktokSource", default = 1)
    var pinterestSource by intPref(key = "pinterestSource", default = 0)
}