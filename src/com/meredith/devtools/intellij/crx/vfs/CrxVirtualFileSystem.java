package com.meredith.devtools.intellij.crx.vfs;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

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
public class CrxVirtualFileSystem extends VirtualFileSystem {
//    private Session session;
//
//    public CrxVirtualFileSystem(Session session) {
//        this.session = session;
//    }

    @Override
    public VirtualFile copyFile(Object o, @NotNull VirtualFile virtualFile, @NotNull VirtualFile virtualFile1, @NotNull String s) throws IOException {
        return null;
    }

    @Override
    public boolean isReadOnly() {
        System.out.println("in isReadOnly");
        return false;
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
        System.out.println("setTimeStamp");
    }

    public boolean isWritable(VirtualFile virtualFile) {
        return false;
    }

    public void setWritable(VirtualFile virtualFile, boolean b) throws IOException {
        System.out.println("setWritable");
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
    public VirtualFile findFileByPath(@NotNull @NonNls String path) {
        System.out.println("in findFileByPath");
        return new CrxVirtualFile(null, null);
    }

    @Override
    public void refresh(boolean asynchronous) {
        System.out.println("in refresh");
    }

    @Override
    public VirtualFile refreshAndFindFileByPath(@NotNull String path) {
        System.out.println("in refreshAndFindFileByPath");
        return null;
    }

    @Override
    public void addVirtualFileListener(@NotNull VirtualFileListener listener) {

    }

    @Override
    public void removeVirtualFileListener(@NotNull VirtualFileListener listener) {
        System.out.println("in removeVirtualFileListener");
    }

    @Override
    public void deleteFile(Object o, @NotNull VirtualFile virtualFile) throws IOException {
        System.out.println("deleteFile");
    }

    @Override
    public void moveFile(Object o, @NotNull VirtualFile virtualFile, @NotNull VirtualFile virtualFile1) throws IOException {
        System.out.println("moveFile");
    }

    @Override
    public void renameFile(Object o, @NotNull VirtualFile virtualFile, @NotNull String s) throws IOException {
        System.out.println("renameFile");
    }

    public void initComponent() {
        System.out.println("initComponent");
    }

    public void disposeComponent() {
        System.out.println("disposeComponent");
    }

    @NotNull
    public String getComponentName() {
        return null;
    }
}
