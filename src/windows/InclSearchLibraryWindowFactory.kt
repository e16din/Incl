package windows

import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.Gson
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.ContentFactory
import model.Result
import java.net.URL

open class InclSearchLibraryWindowFactory : BaseInclSearchLibraryWindowFactory() {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val factory = ContentFactory.SERVICE.getInstance()
        val content = factory.createContent(panel, "", false)
        toolWindow.contentManager.addContent(content)

        searchButton!!.addActionListener {
            val result = Gson().fromJson(search("Dagger2"), Result::class.java)
            result!!.items!!.forEach { resultTextArea!!.text += it.full_name.plus("\n") }
        }

        //todo: search libraries by library name
        //todo: search libraries by user name
        //todo: add check boxes for parameters &sort=stars &order=currentDesc
    }

    fun search(string: String) =
            URL("https://api.github.com/search/repositories?q=$string+language:java").readText()

}