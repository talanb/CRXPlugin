package com.meredith.devtools.intellij.crx;

import com.intellij.openapi.components.ApplicationComponent;
import com.meredith.devtools.intellij.crx.repository.CrxRepository;
import com.meredith.devtools.intellij.crx.repository.CrxRepositoryImpl;
import com.meredith.devtools.intellij.crx.vfs.CrxVirtualFileSystem;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: talanb
 * Date: 8/16/11
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrxApplicationComponent implements ApplicationComponent {
    private CrxRepository repository;

    public CrxRepository getRepository() {
        return repository;
    }


    public void initComponent() {
        repository = new CrxRepositoryImpl();
        repository.initialize("http://localhost:4502/crx/server", "admin", "admin");
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "CrxApplicationComponent";
    }
}
