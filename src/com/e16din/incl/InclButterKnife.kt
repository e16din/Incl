package com.e16din.incl

import com.e16din.incl.BaseIncl.InsertionType.*

class InclButterKnife : BaseIncl() {

    companion object {
        const val CLASSPATH_BUTTERKNIFE = "classpath 'com.jakewharton:butterknife-gradle-plugin:1.8'"

        const val PLUGIN_BUTTERKNIFE = "com.jakewharton.butterknife"

        const val VERSION_BUTTERKNIFE = """def ver_butterkinife = "8.4.0""""
        const val COMPILE_BUTTERKNIFE = """"compile "com.jakewharton:butterknife:${'$'}{ver_butterkinife}""""
        const val APT_BUTTERKNIFE = """apt "com.jakewharton:butterknife-compiler:${'$'}{ver_butterkinife}""""
    }

    override fun name() = "ButterKnife"

    override fun include() {
        includeApt()

        insert(TYPE_APPLY_PLUGIN, PLUGIN_BUTTERKNIFE)

        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, VERSION_BUTTERKNIFE)
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_BUTTERKNIFE)
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, APT_BUTTERKNIFE)

        insert(TYPE_ALL_PROJECTS_REPOSITORY, "\n        ${REPOSITORY_MAVEN_CENTRAL}")
        insert(TYPE_DEPENDENCIES_CLASSPATH, CLASSPATH_BUTTERKNIFE)
    }

}