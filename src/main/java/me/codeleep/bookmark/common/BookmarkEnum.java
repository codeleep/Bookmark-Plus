package me.codeleep.bookmark.common;

/**
 * @author: codeleep
 * @createTime: 2024/03/25 23:37
 * @description: 书签类型。
 */
public enum BookmarkEnum {
    // 书签
    BOOKMARK("书签"),
    // 书签目录
    BOOKMARK_FOLDER("书签夹");

    private final String label;

    BookmarkEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
