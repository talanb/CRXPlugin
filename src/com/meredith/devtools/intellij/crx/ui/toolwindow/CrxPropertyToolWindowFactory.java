package com.meredith.devtools.intellij.crx.ui.toolwindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;

/**
 * Created by IntelliJ IDEA.
 * User: tbreihol
 * Date: 8/4/11
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrxPropertyToolWindowFactory implements ToolWindowFactory {

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        CrxPropertyView propertyView = new CrxPropertyView(project);
        final ContentManager contentManager = toolWindow.getContentManager();
        final Content content = contentManager.getFactory().createContent(propertyView, "", false);
        contentManager.addContent(content);
        Disposer.register(project, propertyView);
    }
}
