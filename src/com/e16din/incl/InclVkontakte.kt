package com.e16din.incl

import com.intellij.openapi.ui.Messages
import com.e16din.incl.BaseIncl.InsertionType.*
import com.e16din.incl.BaseIncl.ClassType.*
import com.e16din.incl.BaseIncl.BlockType.*

class InclVkontakte : BaseIncl() {

    companion object {
        const val MANIFEST_ACTIVITY_VKONTAKTE_NAME = "com.vk.sdk.VKServiceActivity"
        const val MANIFEST_ACTIVITY_VKONTAKTE = "        <activity\n" +
                "            android:name=\"" + MANIFEST_ACTIVITY_VKONTAKTE_NAME + "\"\n" +
                "            android:label=\"ServiceActivity\"\n" +
                "            android:theme=\"@style/VK.Transparent\"/>\n"


        const val APP_INIT_VKONTAKTE_IMPORT = "import com.vk.sdk.VKSdk;"
        const val APP_INIT_VKONTAKTE = "VKSdk.initialize(this);"

        const val APP_INIT_VKONTAKTE_FINGERPRINT_IMPORT = "import com.vk.sdk.util.VKUtil;"
        const val APP_INIT_VKONTAKTE_FINGERPRINT = "VKUtil.getCertificateFingerprint(this, this.getPackageName());"


        const val RES_ID_VKONTAKTE_NAME = "com_vk_sdk_AppId"

        const val COMPILE_VKONTAKTE = "compile 'com.vk:androidsdk:1.6.7'"
    }


    override fun name() = "Vkontakte"

    override fun include() {
        includeToDependencies()
        includeToManifest()
        includeToIds()
        includeToApplication()
    }

    private fun includeToDependencies() {
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, COMPILE_VKONTAKTE)
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