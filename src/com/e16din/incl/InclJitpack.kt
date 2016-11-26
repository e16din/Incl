package com.e16din.incl

open class InclJitpack : BaseIncl() {

    override fun name() = "jitpack"

    override fun include() {
        includeJitpack()
    }

}