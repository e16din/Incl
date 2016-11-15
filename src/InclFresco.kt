open class InclFresco : BaseIncl() {

    override fun name() = "Fresco"

    override fun include() {
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_FRESCO.plus(":0.14.1"))
    }
}