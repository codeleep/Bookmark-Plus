package me.codeleep.bookmark.ui.component.tree;

import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.Tree;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 00:15
 * @description: 书签树
 */
public class BookmarkTree extends Tree {

    private Project project;

    public BookmarkTree(Project project) {
        this.project = project;
    }



}
