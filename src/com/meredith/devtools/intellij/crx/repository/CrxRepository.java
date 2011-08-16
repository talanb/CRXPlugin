package com.meredith.devtools.intellij.crx.repository;

import javax.jcr.Session;

/**
 * Created by IntelliJ IDEA.
 * User: talanb
 * Date: 8/16/11
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CrxRepository {
    Session getSession();
}
