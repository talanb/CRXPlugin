package com.meredith.devtools.intellij.crx.toolwindow;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.meredith.devtools.intellij.crx.repository.CrxRepository;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

/**
 * Created by IntelliJ IDEA.
 * User: tbreihol
 * Date: 7/22/11
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class CRXTreeToolWindowFactory implements ToolWindowFactory, TreeSelectionListener {
    private JPanel toolWindowContent;
    private JLabel testLabel;
    private JTree tree;
    private JScrollPane scrollPane;
    CrxRepository repository;
    private Project project;

    public CRXTreeToolWindowFactory() {
        repository = new CrxRepository("http://localhost:4502/crx/server", "admin", "admin");
    }



    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        CrxExplorer explorer = new CrxExplorer(project, repository);
        final ContentManager contentManager = toolWindow.getContentManager();
        final Content content = contentManager.getFactory().createContent(explorer, "", false);
        contentManager.addContent(content);
        explorer.initTree();
        Disposer.register(project, explorer);

//        this.project = project;
//        tree.setCellRenderer(new CrxTreeCellRenderer());
//        try {
//            tree.setModel(new CrxTreeSnapshot(repository));
//            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
//            tree.addTreeSelectionListener(this);
//        } catch (RepositoryException e) {
//            e.printStackTrace();
//        }
//        this.toolWindow = toolWindow;
//
//        testLabel.setText("Testing the tool window");
//        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
//        Content content = contentFactory.createContent(toolWindowContent, "", false);
//        toolWindow.getContentManager().addContent(content);
    }

    public void valueChanged(TreeSelectionEvent event) {
        CrxNode crxNode = (CrxNode) tree.getLastSelectedPathComponent();
        if (crxNode == null) {
            return;
        }

        Node node = crxNode.getNode();
        try {
            ToolWindow propertiesToolWindow = ToolWindowManager.getInstance(project).getToolWindow("CRX Property View");
            propertiesToolWindow.activate(null);
            JComponent component = propertiesToolWindow.getComponent();
            node.getName();

        } catch (RepositoryException e) {
            e.printStackTrace();
        }

    }
}
