package com.meredith.devtools.intellij.crx.toolwindow;

import com.intellij.openapi.vfs.VirtualFile;
import com.meredith.devtools.intellij.crx.vfs.CrxVirtualFile;
import com.meredith.devtools.intellij.crx.vfs.CrxVirtualFileSystem;
import org.apache.jackrabbit.util.ChildrenCollector;

import javax.jcr.InvalidItemStateException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.lock.LockException;
import javax.jcr.query.qom.ChildNode;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionManager;
import java.util.ArrayList;
import java.util.List;

public class CrxNode {
    private Node node;
    private List<CrxNode> children;
    private CrxNode parentNode;
    private VirtualFile virtualFile;

    public CrxNode(Node node, CrxNode parentNode) {
        this.node = node;
        this.parentNode = parentNode;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public VirtualFile getVirtualFile() {
        if (virtualFile == null) {
            virtualFile = createVirtualFile();
        }
        return virtualFile;
    }

    private VirtualFile createVirtualFile() {
        return new CrxVirtualFile(this, "");
    }

    public CrxNode addChildNode(Node node) {
        if (children == null) {
            children = new ArrayList<CrxNode>(20);
        }
        CrxNode crxNode = new CrxNode(node, this);
        children.add(crxNode);
        return crxNode;
    }
    public boolean hasChildrenLoaded() {
        return !(children == null);
    }
    public int getChildCount() {
        return children == null ? 0 : children.size();
    }

    public CrxNode getChild(int i) {
        return children == null ? null : children.get(i);
    }

    public String toString() {
        String name = null;
        try {
            name = getNode().getName();
            if (name.equals("")) {
                name = "/";
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return name;
    }

    public void checkout() {
        try {
            getNode().getSession().getWorkspace().getVersionManager().checkout(getFullPath());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public void checkin() {
        try {
            Version version = getNode().getSession().getWorkspace().getVersionManager().checkin(getFullPath());
            System.out.println(version);
        } catch (VersionException e) {
            e.printStackTrace();
        } catch (LockException e) {
            e.printStackTrace();
        } catch (InvalidItemStateException e) {
            e.printStackTrace();
        } catch (UnsupportedRepositoryOperationException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public String getFullPath() throws RepositoryException {
        return getNode().getPath();
    }

    public VersionManager getVersionManager() throws RepositoryException {
        return getNode().getSession().getWorkspace().getVersionManager();
    }
}
