package com.meredith.devtools.intellij.crx.toolwindow;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: talanb
 * Date: 8/4/11
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrxPropertyView extends SimpleToolWindowPanel implements DataProvider, Disposable{
    private JPanel toolWindow;
    private JToolBar toolbar;
    private JTable table;
    private JScrollPane scrollPane;
    private Project project;

    public CrxPropertyView(Project project) {
        super(true, true);
        table.setShowGrid(true);
        table.setGridColor(Color.lightGray);
        setContent(toolWindow);


    }

    public void setNode(CrxNode node) {
        table.setModel(new CrxNodeTableModel(node));

        TableColumn col1 = table.getColumnModel().getColumn(0);
        col1.setPreferredWidth(200);
        col1.setHeaderValue("Property Name");
        col1.setCellRenderer(new NodeCellRenderer());
        TableColumn col2 = table.getColumnModel().getColumn(1);
        col2.setPreferredWidth(500);
        col2.setHeaderValue("Property Value");
        col2.setCellRenderer(new NodeCellRenderer());
        TableColumn col3 = table.getColumnModel().getColumn(2);
        col3.setCellRenderer(new NodeCellRenderer());
        col3.setHeaderValue("Data Type");

    }
    public void dispose() {
    }

}
