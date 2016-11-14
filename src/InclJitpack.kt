open class InclJitpack(text: String = "Include _jitpack") : BaseIncl(text) {

    override fun name() = "jitpack"

    override fun include() {
        includeJitpack()
    }

}