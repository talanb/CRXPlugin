package com.meredith.devtools.intellij.crx.toolwindow;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.util.ui.tree.TreeUtil;
import com.meredith.devtools.intellij.crx.CrxApplicationComponent;
import com.meredith.devtools.intellij.crx.repository.CrxRepository;
import com.meredith.devtools.intellij.crx.vfs.CrxVirtualFileSystem;
import com.meredith.devtools.intellij.crx.vfs.CrxVirtualFile;
import org.apache.commons.io.IOUtils;

import java.awt.event.MouseAdapter;

import javax.jcr.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: talanb
 * Date: 8/4/11
 * Time: 8:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrxExplorer extends SimpleToolWindowPanel implements DataProvider, Disposable {
    private Project project;
    private JPanel toolWindow;
    private JToolBar toolbar;
    private JScrollPane scrollPane;
    private JTree tree;
    CrxRepository repository;

    public CrxExplorer(Project project, CrxRepository repository) {
        super(true,true);
        this.project = project;
        this.repository = repository;
        setContent(toolWindow);
    }

    public void initTree() {
        TreeUtil.installActions(tree);
        tree.setCellRenderer(new CrxTreeCellRenderer());
        try {
            tree.setModel(new CrxTreeSnapshot(repository));
            tree.setVisible(true);
            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            tree.addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                    final CrxNode crxNode = (CrxNode) tree.getLastSelectedPathComponent();
                    if (crxNode == null) {
                        return;
                    }

                    final Node node = crxNode.getNode();
                    final ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("CRX Property View");

                    toolWindow.activate(new Runnable() {
                        public void run() {
//                            CrxPropertyView crxPropertyView = (CrxPropertyView)toolWindow;
                            CrxPropertyView crxPropertyView = (CrxPropertyView) ((ToolWindowImpl) toolWindow)
                                    .getContentUI()
                                    .getComponent()
                                    .getComponent(0);
                            try {
                                node.getName();
                            } catch (RepositoryException e) {
                                e.printStackTrace();
                            }
                            crxPropertyView.setNode(crxNode);
                        }
                    });
                }
            });
            tree.addMouseListener(
                    new MouseAdapter() {
                public void mouseReleased( MouseEvent e ) {
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem refreshItem = new JMenuItem("Refresh (Not Implemented)");
                    popup.add(refreshItem);
                    if (SwingUtilities.isRightMouseButton(e)) {
                        popup.show( (JComponent)e.getSource(), e.getX(), e.getY() );
                    }
                }

                        @Override
                        public void mousePressed(MouseEvent mouseEvent) {
                            int selRow = tree.getRowForLocation(mouseEvent.getX(), mouseEvent.getY());
                            TreePath selPath = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
                            if (selRow != -1) {
                                if (mouseEvent.getClickCount() == 2) {
                                    CrxNode node = (CrxNode) selPath.getLastPathComponent();
                                    try {
                                        if (node.getNode().getPrimaryNodeType().getName().equals("nt:file")) {
                                            Node jcrContentNode = node.getNode().getNode("jcr:content");
                                            if (jcrContentNode != null) {
                                                Property data = jcrContentNode.getProperty("jcr:data");
                                                if (data != null) {
                                                    Binary binData = data.getBinary();
                                                    if (binData != null) {
                                                        StringWriter sw = new StringWriter();
                                                        IOUtils.copy(binData.getStream(), sw, "UTF-8");
                                                        String fileContents  = sw.toString();
                                                        CrxApplicationComponent comp =
                                                        ApplicationManager.getApplication().getComponent(CrxApplicationComponent.class);

                                                        CrxVirtualFileSystem vfs = (CrxVirtualFileSystem) VirtualFileManager
                                                                .getInstance().getFileSystem("CRX");
                                                        FileEditorManager.getInstance(project).openFile(new CrxVirtualFile(node, fileContents), true);
                                                        Document doc = EditorFactory.getInstance().createDocument(fileContents);
                                                        Editor editor = EditorFactory.getInstance()
                                                                .createEditor(doc, project, StdFileTypes.JSP, false);
                                                        EditorFactory.getInstance().refreshAllEditors();
                                                    }
                                                }
                                            }
                                        }
                                    } catch (RepositoryException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {


                                    }
                                }
                            }
                        }
                    }
            );

        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public void dispose() {

    }

}
