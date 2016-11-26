package com.e16din.incl

open class InclGson : BaseIncl() {

    companion object {
        const val COMPILE_BASE_PROJECT = "compile 'com.google.code.gson:gson:2.8.0'"
    }

    override fun name() = "Gson"

    override fun include() {
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_BASE_PROJECT)
    }
}