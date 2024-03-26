package me.codeleep.bookmark.ui.component.tree.character;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.ui.component.tree.BookmarkTree;
import me.codeleep.bookmark.ui.component.tree.BookmarkTreeNode;
import me.codeleep.bookmark.ui.component.tree.PopMenuTools;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * @author: codeleep
 * @createTime: 2024/03/29 23:07
 * @description: 鼠标事件
 */
public class TreeNodeMouseAdapter extends MouseAdapter {

    private final Project project;

    private final BookmarkTree bookmarkTree;

    private final PopMenuTools popMenuTools;

    public TreeNodeMouseAdapter(Project project, BookmarkTree bookmarkTree) {
        this.project = project;
        this.bookmarkTree = bookmarkTree;
        this.popMenuTools = new PopMenuTools(project, bookmarkTree);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if (!SwingUtilities.isRightMouseButton(e)) {
            return;
        }
        int row = bookmarkTree.getClosestRowForLocation(e.getX(), e.getY());
        if (row < 0) {
            return;
        }
        if (!bookmarkTree.isRowSelected(row)) {
            bookmarkTree.setSelectionRow(row);
        }
        BookmarkTreeNode selectedNode = (BookmarkTreeNode) bookmarkTree.getLastSelectedPathComponent();
        this.popMenuTools.getBookmarkPopMenu(selectedNode.getBookmarkEnum(), 0 == row).show(bookmarkTree, e.getX() + 16, e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 双击事件
        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
            TreePath path = bookmarkTree.getSelectionPath();
            if (Objects.isNull(path)) {
                return;
            }
            BookmarkTreeNode selectedNode = (BookmarkTreeNode) path.getLastPathComponent();
            if (selectedNode != null && selectedNode.getBookmarkEnum() == BookmarkEnum.BOOKMARK) {
                OpenFileDescriptor openFileDescriptor = selectedNode.getOpenFileDescriptor();
                if (openFileDescriptor != null) {
                    openFileDescriptor.navigate(true);
                }
            }
        }
    }

}
