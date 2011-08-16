package com.meredith.devtools.intellij.crx.repository;

import com.intellij.openapi.components.ServiceManager;

import javax.jcr.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class CrxRepositoryImpl implements CrxRepository {
    private Session session;
    private String url;
    private String user;
    private String password;
    private boolean initialized;

    public static CrxRepository getInstance() {
        return ServiceManager.getService(CrxRepositoryImpl.class);
    }

    public Session getSession() {
        return session;
    }

    private void dumpClassPath(Object obj) {
        System.out.println("==================================================");
        System.out.println(obj.getClass().getName());
        System.out.println("==================================================");
        ClassLoader classLoader = obj.getClass().getClassLoader();
        try {
            classLoader.loadClass("org.apache.jackrabbit.jcr2dav.Jcr2davRepositoryFactory");
            System.out.println("Found it");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Session initialize(String url, String user, String password) {
        dumpClassPath(this);

        Session session = null;
        try {
            // http://localhost:4502/crx/server/
            Map<String, String> parameters = new HashMap();
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
            System.out.println("versioning? " + session.getRepository().getDescriptor("OPTION_VERSIONING_SUPPORTED"));
            initialized = true;
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return session;
    }

    public void iterate(String path, DefaultMutableTreeNode treeNode) throws RepositoryException {
        iterate(session.getNode(path), session.getRootNode(), treeNode);
    }

    public void iterate(Node node, Node parent, DefaultMutableTreeNode parentTreeNode) throws RepositoryException {
        DefaultMutableTreeNode thisTreeNode = new DefaultMutableTreeNode(node.getName());
        thisTreeNode.setUserObject(node);
        parentTreeNode.add(thisTreeNode);
        System.out.println(node.getName() + " (parent: " + parent.getName() + ")");
        NodeIterator ni = node.getNodes();
        while (ni.hasNext()) {
            iterate(ni.nextNode(), node, thisTreeNode);
        }
    }
//    public  DefaultMutableTreeNode iterate(Node node, DefaultMutableTreeNode treeNode) throws RepositoryException {
//        DefaultMutableTreeNode newMutTreeNode = buildTreeNode(node, treeNode);
//        NodeIterator ni = node.getNodes();
//        while(ni.hasNext()) {
//            return iterate(ni.nextNode(), newMutTreeNode);
//        }
//    }
//
//    private DefaultMutableTreeNode buildTreeNode(Node node, DefaultMutableTreeNode treeNode) throws RepositoryException {
//
//        TreeNode newTreeNode = new TreeNode(node);
//        treeNode.add(new DefaultMutableTreeNode(newTreeNode));
//        System.out.println(node.getName());
//        return treeNode;
//    }


}
