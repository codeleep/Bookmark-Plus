package me.codeleep.bookmark.repo.index;

import java.util.List;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 09:53
 * @description: 数据变化监听器
 */
public interface DataChangeListener<T> {

    public void onDataChange(T data);

    public void onDataRemove(T data);

    public void onDataReload(List<T> list);

}
