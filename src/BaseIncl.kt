import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VfsUtil
import java.io.File
import BaseIncl.FileType.*
import BaseIncl.InsertionType.*
import BaseIncl.ClassType.*
import BaseIncl.BlockType.*

abstract class BaseIncl(text: String?) : AnAction(text) {

    enum class FileType {
        FILE_BUILD_GRADLE_PROJECT,
        FILE_BUILD_GRADLE_APP,
        FILE_MANIFEST,
        FILE_RESOURCES,
        FILE_APPLICATION
    }

    enum class InsertionType {
        TYPE_MANIFEST_ACTIVITY,
        TYPE_DEPENDENCIES_CLASSPATH,
        TYPE_ALL_PROJECTS_REPOSITORY,
        TYPE_APPLY_PLUGIN,
        TYPE_PERMISSION
    }

    enum class ClassType {
        TYPE_APPLICATION,
        TYPE_ACTIVITY
    }

    enum class BlockType {
        BLOCK_ON_CREATE,
        BLOCK_IMPORT,
        BLOCK_GRADLE_PROPERTY
    }

    val DEP_COMPILE = "compile"
    val DEP_APT = "apt"
    val DEP_KAPT = "kapt"

    val GRADLE_BLOCK_DEPENDENCIES = "dependencies"
    val GRADLE_BLOCK_DEFAULT_CONFIG = "defaultConfig"
    val GRADLE_BLOCK_ANDROID = "android"

    val RES_INTEGER = "integer"
    val RES_STRING = "string"

    val XML_STRINGS = "strings.xml"
    val XML_IDS = "ids.xml"

    val GRADLE_BUILD = "build.gradle"

    protected var errorAppNotFoundShown = false

    protected var project: Project? = null


    abstract fun include()

    abstract fun name(): String


    override fun actionPerformed(event: AnActionEvent) {
        project = event.getData(PlatformDataKeys.PROJECT)

        include()
        errorAppNotFoundShown = false

        project!!.baseDir.refresh(false, true)

        Messages.showMessageDialog(project, name() + " included.", "Incl", Messages.getInformationIcon())
    }

    protected fun write(runnable: () -> Unit, desc: String? = null) {
        CommandProcessor.getInstance().executeCommand(project, {
            ApplicationManager.getApplication().runWriteAction(runnable)
        }, desc, null)
    }

    protected fun document(docType: FileType,
                           appPackage: String = "",
                           fileName: String? = XML_STRINGS): Document {

        val file: File
        val projectPath = project!!.basePath

        when (docType) {
            FILE_BUILD_GRADLE_PROJECT -> {
                file = File("$projectPath/$GRADLE_BUILD")
            }

            FILE_BUILD_GRADLE_APP -> {
                file = File("$projectPath/app/$GRADLE_BUILD")
            }

            FILE_MANIFEST -> {
                file = File("$projectPath/app/src/main/AndroidManifest.xml")
            }

            FILE_RESOURCES -> {
                file = File("$projectPath/app/src/main/res/values/$fileName")

                if (!file.exists()) {
                    file.writeText("<resources>\n</resources>")
                }
            }

            FILE_APPLICATION -> {
                file = File("$projectPath/app/src/main/java/${packageToPath(appPackage)}/$fileName.java")
            }

        }

        if (!file.exists()) {
            Messages.showMessageDialog(project, "File ${file.path} is not exist.", "Incl", Messages.getErrorIcon())
        }

        val virtualFile = VfsUtil.findFileByIoFile(file, true)

        return FileDocumentManager.getInstance().getDocument(virtualFile!!)!!
    }

    // insert

    //todo: add and check version for classpath for example
    protected fun insertRes(resType: String, fileName: String = XML_STRINGS, key: String, value: String) {
        val doc = document(FILE_RESOURCES, fileName = fileName)

        if (contains(doc, key) && contains(doc, value)) return //todo: check from one xml tag

        val position = positionAfterText(doc, "<resources>")
        val incl: String

        incl = resourceTag(resType, key, value)

        write({
            doc.insertString(position, incl)
        })
    }


    protected fun insertToClass(cls: ClassType, block: BlockType, value: String,
                                className: String = "",
                                withLineSpaceBefore: Boolean = false,
                                withLineSpaceAfter: Boolean = false) {

        when (cls) {
            TYPE_APPLICATION -> {

                //todo: if(!Application.exist()) then create class and add it to name parameter of application in Manifest
                val (appPackage, appClassName) = applicationPackageAndName()
                if (appClassName == null && !errorAppNotFoundShown) {
                    errorAppNotFoundShown = true
                    Messages.showMessageDialog(project, "Not found implementation of Application.class", "Incl", Messages.getErrorIcon())
                    //todo: write log to console
                    return
                }
                val doc = document(FILE_APPLICATION, appPackage, appClassName)

                if (contains(doc, value)) return

                val position = position(block, doc)
                var incl = "\n$value"

                if (withLineSpaceBefore) incl = "\n$incl"
                if (withLineSpaceAfter) incl = "$incl\n"

                write({
                    doc.insertString(position, incl)
                })
            }

            TYPE_ACTIVITY -> {
                //todo: add logic for insert to an activity here
            }
        }
    }

    protected fun insertToGradleBlock(gradleBlockType: String, value: String,
                                      dependencyAddType: String = DEP_COMPILE,
                                      buildGradleFileType: FileType = FILE_BUILD_GRADLE_APP,
                                      contains: String = value): Document {

        val doc = document(buildGradleFileType)

        if (contains(doc, contains)) return doc

        val position = position(BLOCK_GRADLE_PROPERTY, doc, gradleBlockType)
        val incl: String

        when (gradleBlockType) {
            GRADLE_BLOCK_DEPENDENCIES -> {
                incl = "\n    $dependencyAddType '$value'"
            }
            else -> {
                incl = value
            }
        }

        write({
            doc.insertString(position, incl.plus("\n"))
        })

        return doc
    }


    protected fun insert(type: InsertionType, value: String, activityName: String = "") {

        val doc: Document
        var position: Int
        var incl: String

        when (type) {
            TYPE_DEPENDENCIES_CLASSPATH -> {

                doc = document(FILE_BUILD_GRADLE_PROJECT)

                if (contains(doc, value)) return

                position = positionLineEnd(doc, doc.text.lastIndexOf("classpath "))
                incl = "\n        classpath '$value'"
            }

            TYPE_ALL_PROJECTS_REPOSITORY -> {

                doc = document(FILE_BUILD_GRADLE_PROJECT)

                if (contains(doc, value)) return

                val repositoriesPosition = doc.text.indexOf("repositories", doc.text.indexOf("allprojects"))
                position = positionLineEnd(doc, positionLineEnd(doc, repositoriesPosition))
                incl = value
            }

            TYPE_MANIFEST_ACTIVITY -> {

                doc = document(FILE_MANIFEST)

                if (contains(doc, activityName)) return

                position = activityPosition(doc)
                incl = "\n$value"
            }

            TYPE_APPLY_PLUGIN -> {

                doc = document(FILE_BUILD_GRADLE_APP)

                if (contains(doc, value)) return

                position = positionLineEnd(doc, doc.text.lastIndexOf("apply plugin"))
                if (position < 0) {
                    position = 0
                }
                incl = "\napply plugin: '$value'"
            }

            TYPE_PERMISSION -> {

                doc = document(FILE_MANIFEST)

                if (contains(doc, value)) return

                incl = "\n    <uses-permission android:name=\"$value\"/>"

                if (doc.text.contains("uses-permission")) {
                    position = permissionsPosition(doc)
                } else {
                    position = doc.text.indexOf("<application")
                    incl += "\n\n"
                }
                //todo: check space of others permissions
            }
        }

        write({
            doc.insertString(position, incl)
        })
    }

//positions

    protected fun permissionsPosition(doc: Document) = positionLineEnd(doc, doc.text.lastIndexOf("<uses-permission"))

    protected fun activityPosition(doc: Document): Int {
        val startIndex = doc.text.lastIndexOf("<activity")
        val indexQ = doc.text.indexOf(">", startIndex)
        val indexClose = doc.text.indexOf("/>", startIndex)

        if (indexClose >= 0) {
            return if (indexClose < indexQ) {
                indexClose + "/>".length
            } else {
                doc.text.indexOf("</activity>", startIndex) + "</activity>".length
            }
        }

        return -1
    }

    protected fun positionAfterText(doc: Document, text: String) = doc.text.indexOf(text) + text.length

    protected fun positionLineEnd(document: Document, index: Int): Int {
        val lineNumber = document.getLineNumber(index)
        return document.getLineEndOffset(lineNumber)
    }

    protected fun position(block: BlockType, doc: Document, property: String = ""): Int {
        when (block) {
            BLOCK_ON_CREATE -> return positionAfterText(doc, "super.onCreate();")
            BLOCK_IMPORT -> {
                val importIndex = doc.text.lastIndexOf("import")
                if (importIndex < 0)
                    return positionLineEnd(doc, doc.text.indexOf("package"))
                else
                    return positionLineEnd(doc, importIndex)
            }
            BLOCK_GRADLE_PROPERTY -> {

                val indexOfProperty = doc.text.indexOf(property)

                var dept: Int = 0
                var lastIndexStart: Int = doc.text.indexOf("{", indexOfProperty)
                var lastIndexEnd: Int = doc.text.indexOf("}", indexOfProperty)

                lastIndexStart = doc.text.indexOf("{", lastIndexStart + 1)

                while (lastIndexStart < lastIndexEnd && lastIndexStart >= 0) {
                    dept += 1
                    lastIndexStart = doc.text.indexOf("{", lastIndexStart + 1)
                }

                if (dept == 0) return lastIndexEnd

                for (i in 0..dept + 1) {
                    lastIndexEnd = doc.text.indexOf("}", lastIndexEnd + 1)
                }

                return lastIndexEnd
            }
        }
    }

// includes

    protected fun includeApt() {
        insert(TYPE_DEPENDENCIES_CLASSPATH, CLASSPATH_APT.plus(":1.8"))
        insert(TYPE_APPLY_PLUGIN, PLUGIN_APT)
    }


    protected fun includeJitpack() {
        insert(TYPE_ALL_PROJECTS_REPOSITORY, "\n        $REPOSITORY_JITPACK")
    }

    protected fun includeJava8Compat() {
        insertToGradleBlock(GRADLE_BLOCK_DEFAULT_CONFIG, "\n$GRADLE_JACK_OPTIONS\n", "jackOptions")
        val doc = insertToGradleBlock(GRADLE_BLOCK_ANDROID, "\n$GRADLE_COMPILE_OPTIONS\n", "compileOptions")

        write({
            doc.setText(doc.text.replace(" apt ", " annotationProcessor "))
        })
    }

// other

    protected fun resourceTag(tag: String, name: String, value: String) =
            "\n        <$tag name=\"$name\">$value</$tag>"

    protected fun packageToPath(pkg: String) = pkg.replace(".", "/")

    protected fun getValueAfter(text: String, start: String, startIndexForStartText: Int = 0): String {

        val startIndex = text.indexOf(start, startIndexForStartText) + start.length
        val endIndex = text.indexOf("\"", startIndex)

        return text.substring(startIndex, endIndex)
    }

    protected fun description(value: String, fileName: String) = "Add $value to $fileName"

    protected fun contains(document: Document, text: String) = document.text.contains(text)

    private fun applicationPackageAndName(): Pair<String, String?> {
        val doc = document(FILE_MANIFEST)

        val appPackage = getValueAfter(doc.text, "package=\"")

        val paramName = "android:name=\""

        val indexOfApplicationTag = doc.text.indexOf("<application")
        val indexOfParamName = doc.text.indexOf(paramName, indexOfApplicationTag)
        val appClassName: String? =
                if (indexOfParamName > 0 && indexOfParamName < doc.text.indexOf(">", indexOfApplicationTag))
                    getValueAfter(doc.text, paramName, indexOfApplicationTag).split(".").last()
                else null

        return Pair(appPackage, appClassName)
    }
}