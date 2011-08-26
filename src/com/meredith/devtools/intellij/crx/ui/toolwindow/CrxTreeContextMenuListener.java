package com.meredith.devtools.intellij.crx.ui.toolwindow;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.ProjectManager;
import com.meredith.devtools.intellij.crx.CrxIdeaProject;
import com.meredith.devtools.intellij.crx.ui.NewFolderDialog;
import com.meredith.devtools.intellij.crx.ui.NewFolderPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

            NewFolderDialog newFolderDialog = new NewFolderDialog(ProjectManager.getInstance().getDefaultProject(), selectedNode);
            newFolderDialog.show();
        }
    }
}
