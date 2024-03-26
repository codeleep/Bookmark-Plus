package me.codeleep.bookmark.repo.index;

import me.codeleep.bookmark.Index;
import me.codeleep.bookmark.repo.model.Bookmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 09:54
 * @description: 索引分组。所以所查到的值是list情况
 */
public abstract class GroupBookmarkIndex implements DataChangeListener<Bookmark>, Index<Bookmark> {
    
    protected final Map<String, List<Bookmark>> index = new HashMap<>();

    @Override
    public void onDataChange(Bookmark data) {

    }

    @Override
    public void onDataRemove(Bookmark data) {
        if (data == null) {
            return;
        }
        List<Bookmark> bookmarks = index.get(data.getFilePath());
        if (bookmarks != null&& !bookmarks.isEmpty()) {
            bookmarks.remove(data);
        }
    }

    @Override
    public void onDataReload(List<Bookmark> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        index.clear();
        // 以filepath分组
        Map<String, List<Bookmark>> group = list.stream().collect(Collectors.groupingBy(index()));
        index.putAll(group);
    }


}
