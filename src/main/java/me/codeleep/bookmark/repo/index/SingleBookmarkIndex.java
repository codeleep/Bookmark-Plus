package me.codeleep.bookmark.repo.index;

import me.codeleep.bookmark.Index;
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
public abstract class SingleBookmarkIndex implements DataChangeListener<Bookmark>, Index<Bookmark> {

    protected final Map<String, Bookmark> index = new HashMap<>();

    @Override
    public void onDataChange(Bookmark data) {
        index.put(index().apply(data), data);
    }

    @Override
    public void onDataRemove(Bookmark data) {
        index.remove(index().apply(data));
    }

    @Override
    public void onDataReload(List<Bookmark> list) {
        index.clear();
        index.putAll(list.stream().collect(Collectors.toMap(index(), Function.identity())));
    }

}
