class InclJava8Compat : BaseIncl("Include _java8compat") {

    override fun name() = "java 8 compatibility"

    override fun include() {
        includeJava8Compat()
    }
}