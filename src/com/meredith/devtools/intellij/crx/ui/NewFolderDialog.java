package com.meredith.devtools.intellij.crx.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.meredith.devtools.intellij.crx.ui.toolwindow.CrxNode;

import javax.jcr.RepositoryException;
import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: tbreihol
 * Date: 8/26/11
 * Time: 8:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class NewFolderDialog extends DialogWrapper {
    private NewFolderPanel panel;
    private CrxNode parentNode;

    public NewFolderDialog(Project project, CrxNode parentNode) {
        super(project, true);
        this.parentNode = parentNode;
        panel = new NewFolderPanel(this);
        setTitle("New Folder");
        init();

    }
    @Override
    protected JComponent createCenterPanel() {
        return panel.getPanel();
    }

    @Override
    protected void doOKAction() {
        try {
            parentNode.getNode().addNode(panel.getFolderName(), "nt:folder");
            parentNode.getNode().getSession().save();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        super.doOKAction();
    }
}
