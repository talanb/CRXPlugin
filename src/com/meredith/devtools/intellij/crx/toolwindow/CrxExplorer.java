package com.meredith.devtools.intellij.crx.toolwindow;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.util.ui.tree.TreeUtil;
import com.meredith.devtools.intellij.crx.repository.CrxRepository;
import java.awt.event.MouseAdapter;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.MouseEvent;

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
                }
            );

        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public void dispose() {

    }

}
