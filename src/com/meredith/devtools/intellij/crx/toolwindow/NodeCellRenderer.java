package com.meredith.devtools.intellij.crx.toolwindow;

import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: talanb
 * Date: 8/11/11
 * Time: 9:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeCellRenderer extends JLabel implements TableCellRenderer {
    private static DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
    public Component getTableCellRendererComponent(JTable jTable,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int col) {
        Property property = (Property)value;
        try {
            if (property != null && property.getDefinition().isProtected()) {
                setForeground(Color.DARK_GRAY);
            } else {
                setForeground(Color.BLACK);
            }


            switch (col) {
                case 0:
                    setText(property.getName());
                    break;
                case 1:
                    switch(property.getType()) {
                        case 1:
                            if (property.isMultiple()) {
                                setText(getMultipleStrings(property));
                            } else {
                                setText(property.getString());
                            }
                            break;
                        case 5:

                            Calendar date = property.getDate();
                            setText(dateFormat.format(date.getTime()));
                            break;
                        case 7:
                            if (property.isMultiple()) {
                                setText(getMultipleStrings(property));
                            } else {
                                setText(property.getString());
                            }
                            break;
                        default:
                            setText(property.getValue().getString());
                    }
                    break;
                case 2:
                    boolean isMult = property.isMultiple();
                    switch (property.getType()) {
                        case 1:
                            setText("String");
                            break;
                        case 3:
                            setText("Long");
                            break;
                        case 5:
                            setText("Date");
                            break;
                        case 7:
                            setText("Name");
                            break;
                        default:
                            setText(String.valueOf(property.getType()));
                    }
                    if (isMult) {
                        setText(getText() + "[]");
                    }
                    break;
                default:
                    setText("<undefined>");
            }

        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return this;
    }

    private String getMultipleStrings(Property property) throws RepositoryException {
        StringBuilder builder = new StringBuilder();
        Value[] values = property.getValues();
        for (Value value : values) {
            builder.append(value.getString());
            builder.append(",");
        }
        String stringValues = builder.toString();
        return stringValues.substring(0, stringValues.length()-1);

    }

}
