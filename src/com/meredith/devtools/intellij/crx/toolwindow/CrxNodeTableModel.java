package com.meredith.devtools.intellij.crx.toolwindow;

import javax.jcr.*;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionManager;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CrxNodeTableModel extends AbstractTableModel {
    private CrxNode crxNode;
    private List<Property> properties;
    private static DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();

    public CrxNodeTableModel(CrxNode crxNode) {
        this.crxNode = crxNode;
        properties = new ArrayList <Property>();
        Node node = this.crxNode.getNode();

        try {
            PropertyIterator propertyIterator = node.getProperties();
            while (propertyIterator.hasNext()) {
                Property property = propertyIterator.nextProperty();
                properties.add(property);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public int getRowCount() {
        return properties.size();
    }

    public int getColumnCount() {
        return 3;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        try {
            Property property = properties.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    value = property.getName();
                    break;
                case 1:
                    switch(property.getType()) {
                        case 1:
                            if (property.isMultiple()) {
                                value = getMultipleStrings(property);
                            } else {
                                value = property.getString();
                            }
                            break;
                        case 5:

                            Calendar date = property.getDate();
                            value = dateFormat.format(date.getTime());
                            break;
                        case 7:
                            if (property.isMultiple()) {
                                value = getMultipleStrings(property);
                            } else {
                                value = property.getString();
                            }
                            break;
                        default:
                            value = property.getValue().getString();
                    }
                    break;
                case 2:
                    boolean isMult = property.isMultiple();
                    switch (property.getType()) {
                        case 1:
                            value = "String";
                            break;
                        case 5:
                            value = "Date";
                            break;
                        case 7:
                            value = "Name";
                            break;
                        default:
                            value = String.valueOf(property.getType());
                    }
                    if (isMult) {
                        value = value + "[]";
                    }
                    break;
                default:
                    value = "<undefined>";
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        try {
            Property prop = properties.get(row);

            if (!prop.getDefinition().isProtected() && col == 1) {
                return true;
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setValueAt(Object o, int row, int col) {
        VersionManager vm = null;
        try {
            vm = crxNode.getVersionManager();
            vm.checkout(crxNode.getFullPath());
        Property prop = properties.get(row);
        switch(prop.getType()) {
            case 1:
                if (!prop.isMultiple()) {
                    prop.setValue((String) o);
                    crxNode.getNode().setProperty(prop.getName(), prop.getString());
                    crxNode.getNode().getSession().save();
                }
                break;
            default:
                System.out.println("Unknown type: " + prop.getType());
        }
        } catch (ValueFormatException e) {
            e.printStackTrace();
        } catch (LockException e) {
            e.printStackTrace();
        } catch (VersionException e) {
            e.printStackTrace();
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        } finally {
            if (vm != null)
                try {
                    vm.checkin(crxNode.getFullPath());
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }
        }
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
