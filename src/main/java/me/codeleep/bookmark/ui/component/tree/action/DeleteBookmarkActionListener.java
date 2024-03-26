package me.codeleep.bookmark.ui.component.tree.action;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import me.codeleep.bookmark.common.I18N;
import me.codeleep.bookmark.repo.persistence.BookmarkPersistence;
import me.codeleep.bookmark.ui.component.tree.BookmarkTree;
import me.codeleep.bookmark.ui.component.tree.BookmarkTreeNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: codeleep
 * @createTime: 2024/03/29 11:46
 * @description: 删除书签监听器(与书签夹共用)
 */
public class DeleteBookmarkActionListener implements ActionListener {

    private final BookmarkTree tree;

    private final Project project;

    public DeleteBookmarkActionListener(@NotNull Project project, @NotNull BookmarkTree tree) {
        this.tree = tree;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = Messages.showOkCancelDialog(project, I18N.get("bookmark.plus.common.delete_message", ""), I18N.get("bookmark.plus.common.delete_confirm"), I18N.get("bookmark.plus.common.delete"), I18N.get("bookmark.plus.common.cancel"), Messages.getQuestionIcon());
        if (result == Messages.CANCEL) {
            return;
        }
        // 获取选定的节点
        TreePath[] selectionPaths = tree.getSelectionPaths();
        if (selectionPaths == null) {
            return;
        }
        for (TreePath path : selectionPaths) {
            BookmarkTreeNode node = (BookmarkTreeNode) path.getLastPathComponent();
            BookmarkTreeNode parent = (BookmarkTreeNode) node.getParent();
            if (null == parent) {
                continue;
            }
            // 移除数据
            tree.getModel().removeNodeFromParent(node);
            BookmarkPersistence.getInstance(project).removeBookmark(node.getBookmark());
        }
    }
}
