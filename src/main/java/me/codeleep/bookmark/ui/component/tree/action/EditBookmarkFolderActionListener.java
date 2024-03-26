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
 * @description: 修改书签夹监听器
 */
public class EditBookmarkFolderActionListener implements ActionListener {

    private final BookmarkTree tree;

    private final Project project;

    public EditBookmarkFolderActionListener(@NotNull Project project, @NotNull BookmarkTree tree) {
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
        String folderName = Messages.showInputDialog(I18N.get("bookmark.plus.bookmark.folder.placeholder"), I18N.get("bookmark.plus.bookmark.folder"), null, bookmark.getTitle(), Constant.INPUT_VALIDATOR_EX);
        if (StringUtil.isBlank(folderName)) {
            return;
        }
        bookmark.setTitle(folderName);
        BookmarkPersistence.getInstance(project).updateBookmark(bookmark);
        tree.updateUI();
    }
}
