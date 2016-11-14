import BaseIncl.ClassType.*
import BaseIncl.BlockType.*

open class InclDataManager : BaseIncl("Include _DataManager") {

    override fun name() = "DataManager"

    override fun include() {
        includeJitpack()
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_DATA_MANAGER.plus(":0.2.0"))
        insertToClass(TYPE_APPLICATION, BLOCK_ON_CREATE, "\n        DataManager.init(this);")
    }

}