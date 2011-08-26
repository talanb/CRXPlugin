package com.meredith.devtools.intellij.crx.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;

import javax.jcr.Node;
import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: tbreihol
 * Date: 8/26/11
 * Time: 8:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class NewFolderPanel {
    private JButton oKButton;
    private JButton cancelButton;
    private JTextField folderName;
    private JPanel panel;
    private Node parentNode;

    public NewFolderPanel(Project project, Node parentNode) {
        super(project, true);
        this.parentNode = parentNode;
    }

    @Override
    protected JComponent createCenterPanel() {
        return panel;
    }
}
