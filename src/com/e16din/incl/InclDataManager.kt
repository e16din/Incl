package com.e16din.incl

import com.e16din.incl.BaseIncl.ClassType.*
import com.e16din.incl.BaseIncl.BlockType.*

open class InclDataManager : BaseIncl() {

    companion object{
        const val COMPILE_DATA_MANAGER = "compile 'com.github.e16din:DataManager:0.2.0'"

        const val APP_IMPORT_DATA_MANGER = "import com.e16din.datamanager.DataManager;"
    }

    override fun name() = "DataManager"

    override fun include() {
        includeJitpack()
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_DATA_MANAGER)
        insertToClass(TYPE_APPLICATION, BLOCK_ON_CREATE, "\n        DataManager.init(this);")
        insertToClass(TYPE_APPLICATION, BLOCK_IMPORT, "\n".plus(APP_IMPORT_DATA_MANGER))
    }

}