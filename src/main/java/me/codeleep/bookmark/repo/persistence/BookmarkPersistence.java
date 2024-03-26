package me.codeleep.bookmark.repo.persistence;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import me.codeleep.bookmark.common.Constant;
import me.codeleep.bookmark.repo.index.DataChangeListener;
import me.codeleep.bookmark.repo.index.col.FilePathBookmarkIndex;
import me.codeleep.bookmark.repo.index.col.IdBookmarkIndex;
import me.codeleep.bookmark.repo.index.col.ParentIdBookmarkIndex;
import me.codeleep.bookmark.repo.model.Bookmark;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author: codeleep
 * @createTime: 2024/03/25 23:28
 * @description: 书签存储。以Uid为主键
 */
@State(
        name = "BookmarkPersistence",
        storages = {@Storage("BookmarkPersistence.xml")}
)
public final class BookmarkPersistence implements PersistentStateComponent<List<Bookmark>> {

    private static final Logger LOG = Logger.getInstance(BookmarkPersistence.class);

    private final Project project;

    private final Set<Bookmark> bookmarks = new HashSet<>();

    private final List<DataChangeListener<Bookmark>> dataChangeListeners = new ArrayList<>();

    public BookmarkPersistence(Project project) {
        this.project = project;
        init(project);
    }



    public static BookmarkPersistence getInstance(Project project) {
        return project.getService(BookmarkPersistence.class);
    }

    public void init(Project project) {
        registerListen(project);
    }

    @Override
    public @Nullable List<Bookmark> getState() {
        return new ArrayList<>(bookmarks);
    }

    @Override
    public void loadState(@NotNull List<Bookmark> state) {
        this.bookmarks.clear();
        this.bookmarks.addAll(state);
        dataChangeListeners.forEach(listener -> listener.onDataReload(state));
    }

    public void saveBookmark(Bookmark bookmark) {
        if (bookmark == null) {
            return;
        }
        if (bookmark.getId() == null) {
            bookmark.setId(UUID.randomUUID().toString());
        }
        if (bookmark.getParentId() == null) {
            bookmark.setParentId(Constant.ROOT_NODE);
        }
        dataChangeListeners.forEach(listener -> listener.onDataChange(bookmark));
        bookmarks.add(bookmark);
    }

    public boolean removeBookmark(Bookmark bookmark) {
        dataChangeListeners.forEach(listener -> listener.onDataRemove(bookmark));
        return bookmarks.remove(bookmark);
    }

    private void registerListen(Project project) {
        dataChangeListeners.add(FilePathBookmarkIndex.getInstance(project));
        dataChangeListeners.add(ParentIdBookmarkIndex.getInstance(project));
        dataChangeListeners.add(IdBookmarkIndex.getInstance(project));
    }
}
