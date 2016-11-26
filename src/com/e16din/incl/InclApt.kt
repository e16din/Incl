package com.e16din.incl

class InclApt : BaseIncl() {

    companion object {
        const val CLASSPATH_APT = "classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'"

        const val PLUGIN_APT = "com.neenbedankt.android-apt"
    }

    override fun name() = "apt"

    override fun include() {
        includeApt()
    }
}