package com.e16din.incl

class InclJava8Compat : BaseIncl() {

    companion object {
        const val GRADLE_JACK_OPTIONS = """        jackOptions {
            enabled = true
        }"""

        const val GRADLE_COMPILE_OPTIONS = """    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }"""
    }

    override fun name() = "java 8 compatibility"

    override fun include() {
        includeJava8Compat()
    }
}