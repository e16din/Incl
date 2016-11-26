package com.e16din.incl

class InclDagger2 : BaseIncl() {

    companion object {
        const val COMPILE_DAGGER2 = "compile 'com.google.dagger:dagger:2.7'"
        const val APT_DAGGER2 = "apt 'com.google.dagger:dagger-compiler:2.7'"
    }

    override fun name() = "Dagger2"

    override fun include() {
        includeApt()

        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_DAGGER2)
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, APT_DAGGER2)
    }

}