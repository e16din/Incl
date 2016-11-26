package com.e16din.incl


class InclDBFlow : BaseIncl() {

    companion object {
        const val VERSION_DBFLOW = """def ver_dbflow = "4.0.0-beta2""""
        const val APT_DBFLOW_PROCESSOR = """apt "com.github.Raizlabs.DBFlow:dbflow-processor:${'$'}{ver_dbflow}""""
        const val COMPILE_DBFLOW_CORE = """compile "com.github.Raizlabs.DBFlow:dbflow-core:${'$'}{ver_dbflow}""""
        const val COMPILE_DBFLOW = """compile "com.github.Raizlabs.DBFlow:dbflow:${'$'}{ver_dbflow}""""

        const val COMPILE_DBFLOW_KOTLIN = """com.github.Raizlabs.DBFlow:dbflow-kotlinextensions:${'$'}{sqlcipher_version}@aar""""
        const val COMPILE_DBFLOW_SQLCIPHER = """compile "com.github.Raizlabs.DBFlow:dbflow-sqlcipher:${'$'}{ver_dbflow}""""
        const val COMPILE_ZETERIC_SQLCIPHER = """compile "net.zetetic:android-database-sqlcipher:${'$'}{sqlcipher_version}@aar""""
    }

    override fun name() = "DBFlow"

    override fun include() {
        includeApt()
        includeKapt(commented = true)
        includeJitpack()


        val dependencies = arrayOf("", VERSION_DBFLOW, APT_DBFLOW_PROCESSOR, COMPILE_DBFLOW_CORE, COMPILE_DBFLOW)
                .joinToString("\n    ")
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, dependencies)

        val commentedDependencies = arrayOf("(optional)", COMPILE_DBFLOW_KOTLIN, COMPILE_DBFLOW_SQLCIPHER, COMPILE_ZETERIC_SQLCIPHER)
                .joinToString("\n    ")
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, commentedDependencies, commented = true)
    }
}