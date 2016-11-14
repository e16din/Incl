package windows;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

//created for compat with swing form
public abstract class BaseInclSearchLibraryWindowFactory implements ToolWindowFactory {

    protected JLabel todoLabel;
    protected JButton searchButton;
    protected JPanel panel;
    protected JTextArea resultTextArea;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        //logic in the windows.InclSearchLibraryWindowFactory
    }
}
