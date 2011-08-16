package com.meredith.devtools.intellij.crx.vfs;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.NewVirtualFile;
import com.intellij.openapi.vfs.newvfs.NewVirtualFileSystem;
import com.meredith.devtools.intellij.crx.toolwindow.CrxNode;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

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
public class CrxVirutalFile extends NewVirtualFile {
    private CrxNode crxNode;

    public CrxVirutalFile(CrxNode crxNode) {
        this.crxNode = crxNode;
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

    @NotNull
    @Override
    public NewVirtualFileSystem getFileSystem() {
        throw new UnsupportedOperationException("getFileSystem is not implemented");
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
        return null;
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public NewVirtualFile getParent() {
        try {
            crxNode.getNode().getParent();
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
