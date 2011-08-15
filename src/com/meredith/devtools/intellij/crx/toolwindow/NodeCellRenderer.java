package com.meredith.devtools.intellij.crx.toolwindow;

import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: talanb
 * Date: 8/11/11
 * Time: 9:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeCellRenderer extends JLabel implements TableCellRenderer {
    public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        CrxNode node = (CrxNode)value;
        try {
            PropertyIterator propIter = node.getNode().getProperties();
            int i = 0;
            Property prop = null;
            while (propIter.hasNext()) {
                prop = propIter.nextProperty();
                if (i == row) {
                    break;
                }
            }
            if (prop != null && prop.getDefinition().isProtected()) {
                setForeground(Color.LIGHT_GRAY);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return this;
    }
}
