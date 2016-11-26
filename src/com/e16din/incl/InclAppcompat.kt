package com.e16din.incl

open class InclAppcompat : BaseIncl() {

    companion object {
        const val COMPILE_APPCOMPAT = "compile 'com.android.support:appcompat-v7:25.0.1'"
    }

    override fun name() = "appcompat"

    override fun include() {
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_APPCOMPAT)
    }
}