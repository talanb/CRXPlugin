package com.meredith.devtools.intellij.crx.vfs;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.NewVirtualFileSystem;
import org.jetbrains.annotations.NotNull;

import javax.jcr.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: talanb
 * Date: 8/15/11
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrxVirtualFileSystem extends NewVirtualFileSystem {
    private Session session;

    public CrxVirtualFileSystem(Session session) {
        this.session = session;
    }

    @Override
    public boolean isCaseSensitive() {
        return false;
    }

    @Override
    protected String extractRootPath(@NotNull String s) {
        return null;
    }

    @Override
    public int getRank() {
        return 0;
    }

    @Override
    public VirtualFile copyFile(Object o, @NotNull VirtualFile virtualFile, @NotNull VirtualFile virtualFile1, @NotNull String s) throws IOException {
        return null;
    }

    @NotNull
    public byte[] contentsToByteArray(VirtualFile virtualFile) throws IOException {
        return new byte[0];
    }

    @NotNull
    public InputStream getInputStream(VirtualFile virtualFile) throws IOException {
        return null;
    }

    @NotNull
    public OutputStream getOutputStream(VirtualFile virtualFile, Object o, long l, long l1) throws IOException {
        return null;
    }

    public long getLength(VirtualFile virtualFile) {
        return 0;
    }

    public boolean exists(VirtualFile virtualFile) {
        return false;
    }

    public String[] list(VirtualFile virtualFile) {
        return new String[0];
    }

    public boolean isDirectory(VirtualFile virtualFile) {
        return false;
    }

    public long getTimeStamp(VirtualFile virtualFile) {
        return 0;
    }

    public void setTimeStamp(VirtualFile virtualFile, long l) throws IOException {
    }

    public boolean isWritable(VirtualFile virtualFile) {
        return false;
    }

    public void setWritable(VirtualFile virtualFile, boolean b) throws IOException {
    }

    @NotNull
    @Override
    public VirtualFile createChildDirectory(Object o, @NotNull VirtualFile virtualFile, @NotNull String s) throws IOException {
        return null;
    }

    @Override
    public VirtualFile createChildFile(Object o, @NotNull VirtualFile virtualFile, @NotNull String s) throws IOException {
        return null;
    }

    @NotNull
    @Override
    public String getProtocol() {
        return "CRX";
    }

    @Override
    public void deleteFile(Object o, @NotNull VirtualFile virtualFile) throws IOException {
    }

    @Override
    public void moveFile(Object o, @NotNull VirtualFile virtualFile, @NotNull VirtualFile virtualFile1) throws IOException {
    }

    @Override
    public void renameFile(Object o, @NotNull VirtualFile virtualFile, @NotNull String s) throws IOException {
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return null;
    }
}
