package me.codeleep.bookmark.ui.component.tree;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.repo.model.Bookmark;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Objects;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 00:11
 * @description: 树节点
 */
public class BookmarkTreeNode extends DefaultMutableTreeNode implements Comparable<BookmarkTreeNode> {

    private final Bookmark bookmark;

    private final Project project;

    public BookmarkTreeNode(Project project, Bookmark bookmark) {
        super(bookmark);
        this.bookmark = bookmark;
        this.project = project;
        // 设置基础属性
        if (bookmark.getBookmarkEnum() == BookmarkEnum.BOOKMARK_FOLDER) {
            setAllowsChildren(true);
        }
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public OpenFileDescriptor getOpenFileDescriptor() {
        if (bookmark == null) {
            return null;
        }
        if (bookmark.getFilePath() == null) {
            return null;
        }
        VirtualFile file = LocalFileSystem.getInstance().findFileByPath(bookmark.getFilePath());
        if (file == null) {
            return null;
        }
        return new OpenFileDescriptor(project, file, bookmark.getLine(), 0);
    }


    public BookmarkEnum getBookmarkEnum() {
        return bookmark.getBookmarkEnum();
    }

    public void switchBloodRelationship(BookmarkTreeNode target) {
        if (target == null) {
            return;
        }
        bookmark.setParentId(target.getBookmark().getId());
    }

    public void setOrderByNode() {
        TreeNode parent = getParent();
        if (parent == null) {
            return;
        }
        int index = parent.getIndex(this);
        this.bookmark.setOrder(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookmarkTreeNode that = (BookmarkTreeNode) o;
        return Objects.equals(bookmark, that.bookmark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookmark);
    }

    @Override
    public int compareTo(@NotNull BookmarkTreeNode o) {
        return bookmark.compareTo(o.bookmark);
    }
}
