package com.meredith.devtools.intellij.crx.vfs;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.NewVirtualFile;
import com.intellij.openapi.vfs.newvfs.NewVirtualFileSystem;
import com.meredith.devtools.intellij.crx.toolwindow.CrxNode;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: talanb
 * Date: 8/15/11
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrxVirtualFile extends NewVirtualFile {
    private CrxNode crxNode;
    private String contents;

    public CrxVirtualFile(CrxNode crxNode, String contents) {
        this.crxNode = crxNode;
        this.contents = contents;
    }

    @NotNull
    @Override
    public String getName() {
        String name = null;
        try {
            name = crxNode.getNode().getName();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return name;
    }

    public String getContents() {
        return contents;
    }

    @NotNull
    @Override
    public NewVirtualFileSystem getFileSystem() {
        return ApplicationManager.getApplication().getComponent(CrxVirtualFileSystem.class);
    }

    @NotNull
    @Override
    public byte[] contentsToByteArray() throws IOException {
        return contents.getBytes("UTF-8");
    }

    @Override
    public boolean isValid() {
        boolean valid = false;
            if (crxNode != null && crxNode.getNode() != null) {
                try {
                    valid = crxNode.getNode().getPrimaryNodeType().getName().equals("nt:file");
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }
            }
        return valid;
    }

    @Override
    public String getPath() {
        String path = null;
        try {
            path = crxNode.getNode().getPath();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return path;
    }

    @NotNull
    @Override
    public String getUrl() {
        String url = "";
        try{
            url = crxNode.getNode().getPath();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public NewVirtualFile getParent() {
        try {
            crxNode.getNode().getParent();
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public VirtualFile[] getChildren() {
        return new VirtualFile[0];
    }

    @Override
    public NewVirtualFile findChild(@NotNull @NonNls String s) {
        return null;
    }

    @NotNull
    @Override
    public OutputStream getOutputStream(Object o, long l, long l1) throws IOException {
        return null;
    }

    @Override
    public long getTimeStamp() {
        return 0;
    }

    @Override
    public long getLength() {
        return 0;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public NewVirtualFile refreshAndFindChild(String s) {
        return null;
    }

    @Override
    public NewVirtualFile findChildIfCached(String s) {
        return null;
    }

    @Override
    public void setTimeStamp(long l) throws IOException {
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public NewVirtualFile findChildById(int i) {
        return null;
    }

    @Override
    public NewVirtualFile findChildByIdIfCached(int i) {
        return null;
    }

    @Override
    public void setWritable(boolean b) throws IOException {
    }

    @Override
    public void markDirty() {
    }

    @Override
    public void markDirtyRecursively() {
    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public void markClean() {
    }

    @Override
    public Collection<VirtualFile> getCachedChildren() {
        return null;
    }

    @Override
    public Iterable<VirtualFile> iterInDbChildren() {
        return null;
    }

    @Override
    public void setFlag(int i, boolean b) {
    }

    @Override
    public boolean getFlag(int i) {
        return false;
    }
}
