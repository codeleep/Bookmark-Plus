package me.codeleep.bookmark.repo.index;

import me.codeleep.bookmark.repo.model.Bookmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 14:36
 * @description: 查询是单个的索引
 */
public abstract class SingleBookmarkIndex implements IndexChangeListener<Bookmark> {

    protected final Map<Object, Bookmark> index = new HashMap<>();

    @Override
    public void add(Bookmark bookmark) {
        index.put(index().apply(bookmark), bookmark);
    }

    @Override
    public void remove(Bookmark bookmark) {
        index.remove(index().apply(bookmark));
    }

    @Override
    public void indexFailure(List<Bookmark> bookmarks) {
        index.clear();
        if (bookmarks == null) {
            return;
        }
        Map<Object, Bookmark> bookmarkMap = bookmarks.stream().collect(Collectors.toMap(index(), Function.identity()));
        index.putAll(bookmarkMap);
    }
}
