package me.codeleep.bookmark.ui.component.tree.character;

import com.intellij.icons.AllIcons;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.ui.component.tree.BookmarkTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @author: codeleep
 * @createTime: 2024/03/29 11:18
 * @description: 简单单元格渲染器
 */
public class SimpleTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean isLeaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, isLeaf, row, hasFocus);
        BookmarkTreeNode node = (BookmarkTreeNode) value;
        Icon icon = null;
        if (0 == row) {
            icon = AllIcons.Nodes.Module;
        } else if (row > 0) {
            icon = node.getBookmarkEnum() == BookmarkEnum.BOOKMARK ? AllIcons.Nodes.Bookmark : AllIcons.Nodes.BookmarkGroup;
        }
        setIcon(icon);
        return this;
    }

}
