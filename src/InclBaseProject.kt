import BaseIncl.ClassType.*
import BaseIncl.BlockType.*

open class InclBaseProject : BaseIncl() {

    override fun name() = "BaseProject"

    override fun include() {
        includeJitpack()
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_BASE_PROJECT.plus(":1.0.5.4"))
        insertToClass(TYPE_APPLICATION, BLOCK_ON_CREATE, "\n        BaseProject.init(this, BuildConfig.DEBUG);")

        insertToClass(TYPE_APPLICATION, BLOCK_IMPORT, "\n".plus(APP_BASE_PROJECT_IMPORT))
    }

}