package me.codeleep.bookmark;

import java.util.function.Function;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 14:38
 * @description: 索引
 */
public interface Index<T> {

    Function<? super T, String> index();

}
