package me.codeleep.bookmark.repo.model;

import me.codeleep.bookmark.common.BookmarkEnum;

import java.util.Objects;

/**
 * @author: codeleep
 * @createTime: 2024/03/25 23:29
 * @description: 书签
 */
public class Bookmark {

    // 书签ID。UUID
    private String id;

    // 父书签ID。UUID
    private String parentId;

    // 书签标题
    private String title;

    // 书签详细内容
    private String content;

    // 书签类型
    private BookmarkEnum bookmarkEnum;

    // 文件路径。
    private String filePath;

    // 行号。如果是文件，则为行号；如果是文件夹，则为空。
    private int line;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return Objects.equals(id, bookmark.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BookmarkEnum getBookmarkEnum() {
        return bookmarkEnum;
    }

    public void setBookmarkEnum(BookmarkEnum bookmarkEnum) {
        this.bookmarkEnum = bookmarkEnum;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
