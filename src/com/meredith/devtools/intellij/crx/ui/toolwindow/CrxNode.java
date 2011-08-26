package com.meredith.devtools.intellij.crx.ui.toolwindow;

import com.intellij.openapi.vfs.VirtualFile;
import com.meredith.devtools.intellij.crx.vfs.CrxVirtualFile;

import javax.jcr.InvalidItemStateException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.lock.LockException;
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
    private static final List nodeTypes = new ArrayList();

    static {
        nodeTypes.add("cq:AuditEvent");
        nodeTypes.add("cq:BlueprintAction");
        nodeTypes.add("cq:BlueprintSyncConfig");
        nodeTypes.add("cq:CalendarComponent");
        nodeTypes.add("cq:CalendarEvent");
        nodeTypes.add("cq:CalendarRecurrence");
        nodeTypes.add("cq:CalendarRecurrenceRule");
        nodeTypes.add("cq:ClientLibraryFolder");
        nodeTypes.add("cq:Comment");
        nodeTypes.add("cq:Component");
        nodeTypes.add("cq:ContentSyncConfig");
        nodeTypes.add("cq:Dialog");
        nodeTypes.add("cq:DropTargetConfig");
        nodeTypes.add("cq:EditConfig");
        nodeTypes.add("cq:EditListenersConfig");
        nodeTypes.add("cq:Field");
        nodeTypes.add("cq:FrozenReport");
        nodeTypes.add("cq:InplaceEditingConfig");
        nodeTypes.add("cq:LiveSyncAction");
        nodeTypes.add("cq:LiveSyncConfig");
        nodeTypes.add("cq:OrTab");
        nodeTypes.add("cq:Page");
        nodeTypes.add("cq:PageContent");
        nodeTypes.add("cq:Panel");
        nodeTypes.add("cq:Payload");
        nodeTypes.add("cq:PrivilegeAce");
        nodeTypes.add("cq:ProcessStack");
        nodeTypes.add("cq:Rating");
        nodeTypes.add("cq:TabPanel");
        nodeTypes.add("cq:Tag");
        nodeTypes.add("cq:Template");
        nodeTypes.add("cq:Trackback");
        nodeTypes.add("cq:VirtualComponent");
        nodeTypes.add("cq:Wait");
        nodeTypes.add("cq:Widget");
        nodeTypes.add("cq:WidgetCollection");
        nodeTypes.add("cq:WorkItem");
        nodeTypes.add("cq:Workflow");
        nodeTypes.add("cq:WorkflowData");
        nodeTypes.add("cq:WorkflowLauncher");
        nodeTypes.add("cq:WorkflowModel");
        nodeTypes.add("cq:WorkflowNode");
        nodeTypes.add("cq:WorkflowStack");
        nodeTypes.add("cq:WorkflowTransition");
        nodeTypes.add("crx:DeclaredTypeFilter");
        nodeTypes.add("crx:HierarchyFilter");
        nodeTypes.add("crx:ItemFilter");
        nodeTypes.add("crx:NodeTypeFilter");
        nodeTypes.add("crx:NodeTypeRequestMapping");
        nodeTypes.add("crx:OPVValueFilter");
        nodeTypes.add("crx:PathRequestMapping");
        nodeTypes.add("crx:RequestMapping");
        nodeTypes.add("crx:ResourceBundle");
        nodeTypes.add("crx:XPathFilter");
        nodeTypes.add("crx:XmlCharacterData");
        nodeTypes.add("crx:XmlDocument");
        nodeTypes.add("crx:XmlElement");
        nodeTypes.add("crx:XmlNode");
        nodeTypes.add("crx:XmlProcessingInstruction");
        nodeTypes.add("dam:Asset");
        nodeTypes.add("dam:AssetContent");
        nodeTypes.add("nt:activity");
        nodeTypes.add("nt:address");
        nodeTypes.add("nt:childNodeDefinition");
        nodeTypes.add("nt:configuration");
        nodeTypes.add("nt:file");
        nodeTypes.add("nt:folder");
        nodeTypes.add("nt:frozenNode");
        nodeTypes.add("nt:linkedFile");
        nodeTypes.add("nt:nodeType");
        nodeTypes.add("nt:propertyDefinition");
        nodeTypes.add("nt:query");
        nodeTypes.add("nt:resource");
        nodeTypes.add("nt:unstructured");
        nodeTypes.add("nt:version");
        nodeTypes.add("nt:versionHistory");
        nodeTypes.add("nt:versionLabels");
        nodeTypes.add("nt:versionedChild");
        nodeTypes.add("nt:xmlCharacterData");
        nodeTypes.add("nt:xmlDocument");
        nodeTypes.add("nt:xmlElement");
        nodeTypes.add("nt:xmlNode");
        nodeTypes.add("nt:xmlProcessingInstruction");
        nodeTypes.add("rdf:Alt");
        nodeTypes.add("rdf:Bag");
        nodeTypes.add("rdf:Seq");
        nodeTypes.add("rep:ACE");
        nodeTypes.add("rep:ACL");
        nodeTypes.add("rep:AccessControl");
        nodeTypes.add("rep:Activities");
        nodeTypes.add("rep:AuthorizableFolder");
        nodeTypes.add("rep:Configurations");
        nodeTypes.add("rep:DenyACE");
        nodeTypes.add("rep:GrantACE");
        nodeTypes.add("rep:Group");
        nodeTypes.add("rep:Members");
        nodeTypes.add("rep:PrincipalAccessControl");
        nodeTypes.add("rep:Syndicator");
        nodeTypes.add("rep:User");
        nodeTypes.add("rep:nodeTypes");
        nodeTypes.add("rep:root");
        nodeTypes.add("rep:syndicator");
        nodeTypes.add("rep:system");
        nodeTypes.add("rep:versionStorage");
        nodeTypes.add("sling:Folder");
        nodeTypes.add("sling:Mapping");
        nodeTypes.add("sling:MessageEntry");
        nodeTypes.add("sling:OrderedFolder");
        nodeTypes.add("sling:OsgiConfig");
        nodeTypes.add("slingevent:Event");
        nodeTypes.add("slingevent:Job");
        nodeTypes.add("slingevent:TimedEvent");
        nodeTypes.add("vlt:PackageDefinition");
        nodeTypes.add("wiki:Properties");
        nodeTypes.add("wiki:Topic");
        nodeTypes.add("xmp:Property");
        nodeTypes.add("xmp:Simple");
        nodeTypes.add("xmp:Struct");
    }

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

    public void save() throws RepositoryException {
        this.getNode().getSession().save();
    }

    public void addNode(CrxNode newCrxNode) {
        children.add(newCrxNode);
    }
}
