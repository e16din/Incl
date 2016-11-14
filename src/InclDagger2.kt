class InclDagger2 : BaseIncl("Include _Dagger2") {

    override fun name() = "Dagger2"

    override fun include() {
        includeApt()

        val version = ":2.7"

        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_DAGGER2.plus(version))
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, APT_DAGGER2.plus(version), dependencyAddType = DEP_APT)
    }

}