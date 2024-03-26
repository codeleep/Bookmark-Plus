package me.codeleep.bookmark.repo.index;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 19:48
 * @description: 纯洁指示器
 */
public abstract class PurityIndicator<T> {

    // 是否被玷污
    protected boolean virgin = true;

    // 玷污的范围
    protected Set<Function<? super T, Object>> dirties = new HashSet<>();

    protected void addDirty(Function<? super T, Object> dirty) {
        virgin = false;
        dirties.add(dirty);
    }

    protected void rebirth() {
        virgin = true;
        dirties.clear();
    }

    public boolean isVirgin() {
        return virgin;
    }

    public Set<Function<? super T, Object>> getDirties() {
        return dirties;
    }

}
