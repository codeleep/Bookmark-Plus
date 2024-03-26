package me.codeleep.bookmark.ui.component.tree.action;

import com.intellij.openapi.project.Project;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.repo.model.Bookmark;
import me.codeleep.bookmark.ui.component.tree.BookmarkTree;
import me.codeleep.bookmark.ui.component.tree.BookmarkTreeNode;
import me.codeleep.bookmark.ui.component.tree.dialog.BookmarkDialogWrapper;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: codeleep
 * @createTime: 2024/03/29 11:46
 * @description: 修改书签监听器
 */
public class EditBookmarkActionListener implements ActionListener {

    private final BookmarkTree tree;

    private final Project project;

    public EditBookmarkActionListener(@NotNull Project project, @NotNull BookmarkTree tree) {
        this.tree = tree;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BookmarkTreeNode selectedNode = tree.getLastSelectedNode();
        if (selectedNode == null || selectedNode.getBookmarkEnum() != BookmarkEnum.BOOKMARK) {
            return;
        }
        Bookmark bookmark = (Bookmark) selectedNode.getUserObject();
        BookmarkDialogWrapper bookmarkDialogWrapper = new BookmarkDialogWrapper(project, bookmark);
        bookmarkDialogWrapper.show();
    }
}
