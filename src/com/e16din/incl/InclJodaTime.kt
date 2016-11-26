package com.e16din.incl

import com.e16din.incl.BaseIncl.ClassType.*
import com.e16din.incl.BaseIncl.BlockType.*

open class InclJodaTime : BaseIncl() {

    companion object {
        const val APP_IMPORT_JODA_TIME = "import net.danlew.android.joda.JodaTimeAndroid;"

        const val COMPILE_JODA_TIME = "compile 'net.danlew:android.joda:2.9.5'"
    }

    override fun name() = "JodaTime"

    override fun include() {
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_JODA_TIME)
        insertToClass(TYPE_APPLICATION, BLOCK_ON_CREATE, "\n        JodaTimeAndroid.init(this);")

        insertToClass(TYPE_APPLICATION, BLOCK_IMPORT, "\n".plus(APP_IMPORT_JODA_TIME))
    }

}