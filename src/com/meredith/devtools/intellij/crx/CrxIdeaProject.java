package com.meredith.devtools.intellij.crx;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: tbreihol
 * Date: 8/4/11
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrxIdeaProject implements ProjectComponent {
    public CrxIdeaProject(Project project) {
        System.out.println("CrxIdeaProject: Constructor");
    }

    public void initComponent() {
        System.out.println("CrxIdeaProject: initComponent");
    }

    public void disposeComponent() {
        System.out.println("CrxIdeaProject: disposeComponent");
    }

    @NotNull
    public String getComponentName() {
        return "CrxIdeaProject";
    }

    public void projectOpened() {
        System.out.println("CrxIdeaProject: projectOpened");
    }

    public void projectClosed() {
        System.out.println("CrxIdeaProject: projectClosed");
    }
}
