package com.e16din.incl

import com.e16din.incl.BaseIncl.ClassType.*
import com.e16din.incl.BaseIncl.BlockType.*

open class InclBaseProject : BaseIncl() {

    companion object {
        const val APP_IMPORT_BASE_PROJECT = "import com.e16din.baseproject.BaseProject;"

        const val COMPILE_BASE_PROJECT = "compile 'com.github.e16din:BaseProject:1.0.6'"
    }

    override fun name() = "BaseProject"

    override fun include() {
        includeJitpack()
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_BASE_PROJECT)
        insertToClass(TYPE_APPLICATION, BLOCK_ON_CREATE, "\n        BaseProject.init(this, BuildConfig.DEBUG);")

        insertToClass(TYPE_APPLICATION, BLOCK_IMPORT, "\n".plus(APP_IMPORT_BASE_PROJECT))
    }

}