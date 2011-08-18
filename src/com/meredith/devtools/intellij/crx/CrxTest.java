package com.meredith.devtools.intellij.crx;

import org.apache.jackrabbit.commons.JcrUtils;

import javax.imageio.spi.ServiceRegistry;
import javax.jcr.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Created by IntelliJ IDEA.
 * User: tbreihol
 * Date: 7/22/11
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrxTest {
    public static void main(String[] args) throws Exception {
        CrxTest test = new CrxTest();
        test.run();
    }

    private void run() throws Exception {
        Session session = null;
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("org.apache.jackrabbit.repository.uri", "http://localhost:4502/crx/server");
        System.out.println("Loooping");
        for (RepositoryFactory factory : ServiceLoader.load(RepositoryFactory.class,
                                                            this.getClass().getClassLoader())) {
            System.out.println("Trying " + factory.getClass().getName());
            Repository repo = factory.getRepository(parameters);
            if (repo == null) {
                continue;
            }
            SimpleCredentials creds = new SimpleCredentials("admin", "admin".toCharArray());
            session = repo.login(creds, "crx.default");
            break;
        }

        //List Children
        System.out.println("Workspace: " + session.getWorkspace().getName() + "\n");
        listChildren("", session.getNode("/apps") );

    }
    private  void listChildren(String indent, Node node ) throws RepositoryException {
        dump(indent, node);
        NodeIterator ni = node.getNodes();
        while(ni.hasNext()) {
            listChildren(indent+"  ", ni.nextNode());
        }
    }

    private void dump(String indent, Node node) throws RepositoryException {
        StringBuilder builder = new StringBuilder();
        builder.append(indent);
        builder.append(node.getName());
        builder.append(" (");
        builder.append(node.getPrimaryNodeType().getName());
        builder.append(")");
        System.out.println(builder.toString());
    }

}
