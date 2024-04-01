package me.codeleep.bookmark.repo.persistence;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.common.Constant;
import me.codeleep.bookmark.repo.index.IndexChangeListener;
import me.codeleep.bookmark.repo.index.col.FilePathBookmarkIndex;
import me.codeleep.bookmark.repo.index.col.IdBookmarkIndex;
import me.codeleep.bookmark.repo.index.col.ParentIdBookmarkIndex;
import me.codeleep.bookmark.repo.model.Bookmark;
import me.codeleep.bookmark.repo.model.po.BookmarkRepoPo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: codeleep
 * @createTime: 2024/03/25 23:28
 * @description: 书签存储。以Uid为主键
 */
@Service(Service.Level.PROJECT)
@State(
        name = "BookmarkPersistence",
        defaultStateAsResource = true,
        storages = {@Storage("BookmarkPersistence.xml")}
)
public final class BookmarkPersistence implements PersistentStateComponent<BookmarkRepoPo> {

    private static final Logger LOG = Logger.getInstance(BookmarkPersistence.class);

    private final Project project;

    private BookmarkRepoPo bookmarkRepoPo;

    private final Map<Function<? super Bookmark, Object>,IndexChangeListener<Bookmark>> dataChangeListeners = new HashMap<>();

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

    public void setBookmarkRepoPo(BookmarkRepoPo bookmarkRepoPo) {
        this.bookmarkRepoPo = bookmarkRepoPo;
    }

    @Override
    public @Nullable BookmarkRepoPo getState() {
        if (bookmarkRepoPo.getBookmarks().isEmpty()) {
            // 初始化根节点
            Bookmark bookmark = new Bookmark(Constant.ROOT_NODE, BookmarkEnum.BOOKMARK_FOLDER);
            bookmark.setTitle(project.getName());
            bookmarkRepoPo.addBookmark(bookmark);
        }
        return bookmarkRepoPo;
    }

    @Override
    public void loadState(@NotNull BookmarkRepoPo state) {
        this.bookmarkRepoPo = state;
        dataChangeListeners.values().forEach(listener -> listener.indexFailure(state.getBookmarks()));
    }


    @Override
    public void noStateLoaded() {
        if (this.bookmarkRepoPo == null) {
            this.bookmarkRepoPo = new BookmarkRepoPo(new ArrayList<>());
            this.getState();
        }
    }

    public void saveBookmark(Bookmark bookmark) {
        Bookmark bookmarkById = IdBookmarkIndex.getInstance(project).getById(bookmark.getId());
        if (bookmarkById == null) {
            addBookmark(bookmark);
        }else {
            updateBookmark(bookmark);
        }
    }

    public void addBookmark(Bookmark bookmark) {
        if (bookmark == null) {
            return;
        }
        if (bookmark.getParentId() == null) {
            bookmark.setParentId(Constant.ROOT_NODE);
        }
        for (IndexChangeListener<Bookmark> listener : findListener(bookmark.getDirties())){
            listener.add(bookmark);
        }
        bookmarkRepoPo.addBookmark(bookmark);
    }

    public void updateBookmark(Bookmark bookmark) {
        if (bookmark == null) {
            return;
        }
        if (bookmark.getParentId() == null) {
            bookmark.setParentId(Constant.ROOT_NODE);
        }
        for (IndexChangeListener<Bookmark> listener : findListener(bookmark.getDirties())){
            listener.indexFailure(new ArrayList<>(bookmarkRepoPo.getBookmarks()));
        }
        bookmarkRepoPo.addBookmark(bookmark);
    }

    public boolean removeBookmark(Bookmark bookmark) {
        findListener(bookmark.getDirties()).forEach(listener -> listener.remove(bookmark));
        return bookmarkRepoPo.removeBookmark(bookmark);
    }

    private void registerListen(Project project) {
        dataChangeListeners.put(FilePathBookmarkIndex.getInstance(project).index(), FilePathBookmarkIndex.getInstance(project));
        dataChangeListeners.put(ParentIdBookmarkIndex.getInstance(project).index(), ParentIdBookmarkIndex.getInstance(project));
        dataChangeListeners.put(IdBookmarkIndex.getInstance(project).index(), IdBookmarkIndex.getInstance(project));
    }

    private List<IndexChangeListener<Bookmark>> findListener(Set<Function<? super Bookmark, Object>> fields) {
        return fields.stream().map(dataChangeListeners::get).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
