// classpath

const val CLASSPATH_APT = "com.neenbedankt.gradle.plugins:android-apt"


// plugin

const val PLUGIN_APT = "com.neenbedankt.android-apt"

// repository

const val REPOSITORY_JITPACK = "maven { url \"https://jitpack.io\" }"

// lib

const val COMPILE_APPCOMPAT = "com.android.support:appcompat-v7"

const val COMPILE_VKONTAKTE = "com.vk:androidsdk"

const val COMPILE_DAGGER2 = "com.google.dagger:dagger"
const val APT_DAGGER2 = "com.google.dagger:dagger-compiler"

const val COMPILE_DATA_MANAGER = "com.github.e16din:DataManager"

// permission

const val PERMISSION_INTERNET = "android.permission.INTERNET"


// manifestDocument activity

const val MANIFEST_ACTIVITY_VKONTAKTE_NAME = "com.vk.sdk.VKServiceActivity"
const val MANIFEST_ACTIVITY_VKONTAKTE = "        <activity\n" +
        "            android:name=\"" + MANIFEST_ACTIVITY_VKONTAKTE_NAME + "\"\n" +
        "            android:label=\"ServiceActivity\"\n" +
        "            android:theme=\"@style/VK.Transparent\"/>\n"


// resources

const val RES_ID_VKONTAKTE_NAME = "com_vk_sdk_AppId"


// app init

const val APP_INIT_VKONTAKTE_IMPORT = "import com.vk.sdk.VKSdk;"
const val APP_INIT_VKONTAKTE = "VKSdk.initialize(this);"

const val APP_INIT_VKONTAKTE_FINGERPRINT_IMPORT = "import com.vk.sdk.util.VKUtil;"
const val APP_INIT_VKONTAKTE_FINGERPRINT = "VKUtil.getCertificateFingerprint(this, this.getPackageName());"

// gradle block

const val GRADLE_JACK_OPTIONS = """        jackOptions {
            enabled = true
        }"""

const val GRADLE_COMPILE_OPTIONS = """    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }"""

