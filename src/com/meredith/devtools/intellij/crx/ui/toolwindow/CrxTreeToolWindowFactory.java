package com.meredith.devtools.intellij.crx.ui.toolwindow;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.meredith.devtools.intellij.crx.CrxApplicationComponent;
import com.meredith.devtools.intellij.crx.repository.CrxRepository;

public class CrxTreeToolWindowFactory implements ToolWindowFactory {
    CrxRepository repository;

    public CrxTreeToolWindowFactory() {
    }



    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        CrxApplicationComponent appComp = ApplicationManager.getApplication()
                .getComponent(CrxApplicationComponent.class);

        repository = appComp.getRepository();
        if (!repository.isInitialized()) {
            repository.initialize("http://localhost:4502/crx/server", "admin", "admin");
        }
        CrxExplorer explorer = new CrxExplorer(project, repository);
        final ContentManager contentManager = toolWindow.getContentManager();
        final Content content = contentManager.getFactory().createContent(explorer, "", false);
        contentManager.addContent(content);
        explorer.initTree();
        Disposer.register(project, explorer);
    }
}
