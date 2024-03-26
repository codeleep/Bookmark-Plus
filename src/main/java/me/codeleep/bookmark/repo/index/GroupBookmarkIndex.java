package me.codeleep.bookmark.repo.index;

import me.codeleep.bookmark.common.Constant;
import me.codeleep.bookmark.repo.model.Bookmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 09:54
 * @description: 索引分组。所以所查到的值是list情况
 */
public abstract class GroupBookmarkIndex implements IndexChangeListener<Bookmark> {
    
    protected final Map<Object, List<Bookmark>> index = new HashMap<>();

    @Override
    public void add(Bookmark bookmark) {

    }

    @Override
    public void remove(Bookmark data) {
        if (data == null) {
            return;
        }
        List<Bookmark> bookmarks = index.get(data.getFilePath());
        if (bookmarks != null&& !bookmarks.isEmpty()) {
            bookmarks.remove(data);
        }
    }

    @Override
    public void indexFailure(List<Bookmark> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        index.clear();
        // 以filepath分组
        Map<Object, List<Bookmark>> group = list.stream().collect(Collectors.groupingBy(item -> {
            Object apply = index().apply(item);
            if (apply == null) {
                return Constant.GROUP_NULL;
            }
            return apply;
        }));
        index.putAll(group);
    }

}
