package me.codeleep.bookmark.ui.component.tree;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author: codeleep
 * @createTime: 2024/03/30 00:18
 * @description: 持有者
 */
@Service(Service.Level.PROJECT)
public final class BookmarkTreeHolder {

    private BookmarkTree bookmarkTree;

    public static BookmarkTreeHolder getInstance(Project project) {
        return project.getService(BookmarkTreeHolder.class);
    }

    public BookmarkTree getBookmarkTree() {
        return this.bookmarkTree;
    }

    public void setBookmarkTree(@NotNull BookmarkTree bookmarkTree) {
        this.bookmarkTree = bookmarkTree;
    }


}
