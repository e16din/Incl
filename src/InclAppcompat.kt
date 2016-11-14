open class InclAppcompat() : BaseIncl("Include _appcompat") {

    override fun name() = "appcompat"

    override fun include() {
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_APPCOMPAT.plus(":25.0.0"))
    }
}