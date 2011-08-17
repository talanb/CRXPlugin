package com.meredith.devtools.intellij.crx.repository;

import javax.jcr.Repository;
import javax.jcr.Session;

/**
 * Created by IntelliJ IDEA.
 * User: talanb
 * Date: 8/16/11
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CrxRepository {
    void initialize(String url, String username, String password);
    Repository getRepository();
    Session getSession();
    boolean isInitialized();
}
