import BaseIncl.InsertionType.*

class InclButterKnife : BaseIncl() {

    override fun name() = "ButterKnife"

    override fun include() {
        includeApt()

        insert(TYPE_APPLY_PLUGIN, PLUGIN_BUTTERKNIFE)
        val version = ":8.4.0"

        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_BUTTERKNIFE.plus(version))
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, APT_BUTTERKNIFE.plus(version), dependencyAddType = DEP_APT)

        insert(TYPE_ALL_PROJECTS_REPOSITORY, "\n        $REPOSITORY_MAVEN_CENTRAL")
        insert(TYPE_DEPENDENCIES_CLASSPATH, CLASSPATH_BUTTERKNIFE.plus(version))
    }

}