package me.codeleep.bookmark.ui.component.tree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 00:11
 * @description: 树节点
 */
public class BookmarkTreeNode extends DefaultMutableTreeNode {

    private BookmarkTreeNodeEnum bookmarkTreeNodeEnum;

    public BookmarkTreeNodeEnum getBookmarkTreeNodeEnum() {
        return bookmarkTreeNodeEnum;
    }

    public void setBookmarkTreeNodeEnum(BookmarkTreeNodeEnum bookmarkTreeNodeEnum) {
        this.bookmarkTreeNodeEnum = bookmarkTreeNodeEnum;
    }
}
