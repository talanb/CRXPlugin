package com.meredith.devtools.intellij.crx.ui.toolwindow;

import com.meredith.devtools.intellij.crx.repository.CrxRepository;

import javax.jcr.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class CrxTreeSnapshot implements TreeModel {
    private CrxRepository repository;
    private CrxNode rootNode;
    public CrxTreeSnapshot(CrxRepository repository) throws RepositoryException {
        this.repository = repository;
        rootNode = new CrxNode(repository.getSession().getRootNode(), null);
        Node startNode = repository.getSession().getNode("/apps");
        //iterate(rootNode.getNode(), null);
    }

    public Object getRoot() {
        return rootNode;
    }

    public Object getChild(Object o, int i) {
        CrxNode node = (CrxNode)o;
        if (!node.hasChildrenLoaded()) {
            getChildren(node);
        }
        return node.getChild(i);
    }

    public int getChildCount(Object o) {
        CrxNode node = (CrxNode)o;
        if (!node.hasChildrenLoaded()) {
            getChildren(node);
        }
        return node.getChildCount();
    }

    public boolean isLeaf(Object o) {
        CrxNode node = (CrxNode)o;
        boolean isLeaf = false;
        try {
            isLeaf = !node.getNode().hasNodes();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return isLeaf;
    }

    public void valueForPathChanged(TreePath treePath, Object o) {
        System.out.println("valueForPathChanged(" + treePath + ", " + o + ")");
    }

    public int getIndexOfChild(Object o, Object o1) {
        System.out.println("getIndexOfChild(" + o + ", " + o1 + ")");
        return 0;
    }

    public void addTreeModelListener(TreeModelListener treeModelListener) {
        System.out.println("addTreeModelListener(" + treeModelListener  + ")");
    }

    public void removeTreeModelListener(TreeModelListener treeModelListener) {
        System.out.println("removeTreeModelListener(" + treeModelListener  + ")");
    }

    public void getChildren(CrxNode crxNode) {
        try {
            NodeIterator nodeIter = crxNode.getNode().getNodes();
            while (nodeIter.hasNext()) {
                Node node = nodeIter.nextNode();
                crxNode.addChildNode(node);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
    public void iterate(Node node, CrxNode parent) throws RepositoryException {
        CrxNode thisCrxNode = parent.addChildNode(node);
        System.out.println(node.getName() + " (parent: " + parent.getNode().getName() + ")");
        NodeIterator ni = node.getNodes();
        while (ni.hasNext()) {
            iterate(ni.nextNode(), thisCrxNode);
        }
    }

}
