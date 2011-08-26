package com.meredith.devtools.intellij.crx.ui.toolwindow;

import com.intellij.openapi.util.IconLoader;

import javax.jcr.RepositoryException;
import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: talanb
 * Date: 8/3/11
 * Time: 11:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrxTreeCellRenderer extends DefaultTreeCellRenderer {
    Icon unstructuredIcon;
    Icon closedFolderIcon;
    Icon fileIcon;
    Icon pageIcon;
    Icon dialogIcon;


    public CrxTreeCellRenderer() {
        unstructuredIcon = IconLoader.getIcon("/unstructured.png");
        closedFolderIcon = IconLoader.getIcon("/folder.png");
        fileIcon = IconLoader.getIcon("/file.png");
        pageIcon = IconLoader.getIcon("/page.png");
        dialogIcon = IconLoader.getIcon("/dialog.png");

    }

    @Override
    public Component getTreeCellRendererComponent(JTree jTree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component comp = super.getTreeCellRendererComponent(jTree,
                                                  value,
                                                  selected,
                                                  expanded,
                                                  leaf,
                                                  row,
                                                  hasFocus);

        if (value instanceof CrxNode) {
            CrxNode crxNode = (CrxNode)value;
            try {
                String nodeType = crxNode.getNode().getPrimaryNodeType().getName();
                if (nodeType.equals("cq:Page")) {
                    setIcon(pageIcon);
                } else if (nodeType.equals("cq:Dialog")) {
                    setIcon(dialogIcon);
                } else if (nodeType.equals("nt:file")) {
                    setIcon(fileIcon);
                } else if (nodeType.equals("nt:folder") || nodeType.equals("sling:Folder") ||
                        nodeType.equals("sling:OrderedFolder") || nodeType.equals("rep:AuthorizableFolder")) {
                    setIcon(closedFolderIcon);
                } else {
                    setIcon(unstructuredIcon);
                }
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

/** Returns an ImageIcon, or null if the path was invalid. */
private ImageIcon createImageIcon(String path,
                                           String description) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
}

}
