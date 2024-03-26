package me.codeleep.bookmark.ui.component.tree.action;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.common.Constant;
import me.codeleep.bookmark.common.I18N;
import me.codeleep.bookmark.repo.model.Bookmark;
import me.codeleep.bookmark.repo.persistence.BookmarkPersistence;
import me.codeleep.bookmark.ui.component.tree.BookmarkTree;
import me.codeleep.bookmark.ui.component.tree.BookmarkTreeNode;
import org.jetbrains.annotations.NotNull;
import org.jsoup.internal.StringUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: codeleep
 * @createTime: 2024/03/29 11:46
 * @description: 创建书签夹监听器
 */
public class CreatBookmarkFolderActionListener implements ActionListener {

    private final BookmarkTree tree;

    private final Project project;

    public CreatBookmarkFolderActionListener(@NotNull Project project, @NotNull BookmarkTree tree) {
        this.tree = tree;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取选定的节点
        BookmarkTreeNode selectedNode = tree.getLastSelectedNode();
        if (selectedNode == null || selectedNode.getBookmarkEnum() != BookmarkEnum.BOOKMARK_FOLDER) {
            return;
        }
        String folderName = Messages.showInputDialog(I18N.get("bookmark.plus.bookmark.folder.placeholder"), I18N.get("bookmark.plus.bookmark.folder"), null, null, Constant.INPUT_VALIDATOR_EX);
        if (StringUtil.isBlank(folderName)) {
            return;
        }
        BookmarkTreeNode parent = selectedNode.getBookmarkEnum() == BookmarkEnum.BOOKMARK_FOLDER ? selectedNode : (BookmarkTreeNode) selectedNode.getParent();
        Bookmark bookmark = new Bookmark(BookmarkEnum.BOOKMARK_FOLDER);
        bookmark.setTitle(folderName);
        // 新的分组节点
        BookmarkPersistence.getInstance(project).addBookmark(bookmark);
        BookmarkTreeNode groupNode = new BookmarkTreeNode(project, bookmark);
        tree.getModel().insertNodeInto(groupNode, parent, 0);
    }
}
