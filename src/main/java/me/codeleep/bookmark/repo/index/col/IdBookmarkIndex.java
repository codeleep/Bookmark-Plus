package me.codeleep.bookmark.repo.index.col;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.common.Constant;
import me.codeleep.bookmark.repo.index.SingleBookmarkIndex;
import me.codeleep.bookmark.repo.model.Bookmark;

import java.util.List;
import java.util.function.Function;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 14:47
 * @description: 通过ID直接获取
 */
@Service(Service.Level.PROJECT)
public final class IdBookmarkIndex extends SingleBookmarkIndex {

    public static IdBookmarkIndex getInstance(Project project) {
        return project.getService(IdBookmarkIndex.class);
    }

    public Bookmark getById(String id) {
        return index.get(id);
    }

    public Bookmark findRoot() {
        Bookmark bookmark = index.get(Constant.ROOT_NODE);
        if (bookmark == null) {
            return new Bookmark(Constant.ROOT_NODE, BookmarkEnum.BOOKMARK_FOLDER);
        }
        return bookmark;
    }

    public boolean isExist(String id) {
        return index.containsKey(id);
    }

    @Override
    public Function<? super Bookmark, Object> index() {
        return Bookmark::getId;
    }
}
