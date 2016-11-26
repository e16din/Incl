package com.e16din.incl

open class InclFresco : BaseIncl() {

    companion object {
        const val COMPILE_FRESCO = "compile 'com.facebook.fresco:fresco:0.14.1'"
    }

    override fun name() = "Fresco"

    override fun include() {
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_FRESCO)
    }
}