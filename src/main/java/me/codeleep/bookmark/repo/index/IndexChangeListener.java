package me.codeleep.bookmark.repo.index;

import java.util.List;
import java.util.function.Function;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 09:53
 * @description: 索引变化监听器
 */
public interface IndexChangeListener<T> {

    void add(T t);

    void remove(T t);

    void indexFailure(List<T> list);

    Function<? super T, Object> index();
}
