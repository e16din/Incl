class InclApt(text: String = "Include _apt") : BaseIncl(text) {

    override fun name() = "apt"

    override fun include() {
        includeApt()
    }
}