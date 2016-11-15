import com.intellij.openapi.ui.Messages
import BaseIncl.InsertionType.*
import BaseIncl.ClassType.*
import BaseIncl.BlockType.*

class InclVkontakte : BaseIncl() {

    override fun name() = "Vkontakte"

    override fun include() {
        includeToDependencies()
        includeToManifest()
        includeToIds()
        includeToApplication()
    }

    private fun includeToDependencies() {
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_VKONTAKTE.plus(":1.6.7"))
    }

    private fun includeToManifest() {
        insert(TYPE_PERMISSION, PERMISSION_INTERNET)
        insert(TYPE_MANIFEST_ACTIVITY, MANIFEST_ACTIVITY_VKONTAKTE, MANIFEST_ACTIVITY_VKONTAKTE_NAME)
    }

    private fun includeToIds() {
        val appId = Messages.showInputDialog("Input your Vkontakte app id", "Include Vkontakte SDK (Incl)",
                Messages.getQuestionIcon(), "YOUR_APP_ID", null)

        insertRes(RES_INTEGER, XML_IDS, RES_ID_VKONTAKTE_NAME, appId!!)
    }

    private fun includeToApplication() {
        val onCreate = arrayOf("", APP_INIT_VKONTAKTE, APP_INIT_VKONTAKTE_FINGERPRINT).joinToString("\n        ")
        insertToClass(TYPE_APPLICATION, BLOCK_ON_CREATE, onCreate)

        val imports = arrayOf("", APP_INIT_VKONTAKTE_IMPORT, APP_INIT_VKONTAKTE_FINGERPRINT_IMPORT).joinToString("\n")
        insertToClass(TYPE_APPLICATION, BLOCK_IMPORT, imports)
    }
}