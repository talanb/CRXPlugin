package com.meredith.devtools.intellij.crx.ui.toolwindow;

import com.meredith.devtools.intellij.crx.ui.NewFolderDialog;
import org.apache.jackrabbit.core.NodeImpl;

import javax.jcr.Node;
import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by IntelliJ IDEA.
 * User: talanb
 * Date: 8/25/11
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrxTreeContextMenuListener implements ActionListener {
    private CrxNode selectedNode;

    public CrxTreeContextMenuListener(CrxNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        JMenuItem item = (JMenuItem)actionEvent.getSource();
        if (item.getText().equals("New Folder...")) {
            JDialog newFolderDialog = new NewFolderDialog(selectedNode);
            newFolderDialog.setVisible(true);
        }
    }
}
