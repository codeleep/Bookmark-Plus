package me.codeleep.bookmark.repo.model;

import com.intellij.util.xmlb.annotations.Tag;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.repo.index.PurityIndicator;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: codeleep
 * @createTime: 2024/03/25 23:29
 * @description: 书签
 */
@XmlRootElement
public class Bookmark extends PurityIndicator<Bookmark> implements Comparable<Bookmark>{

    // 书签ID。UUID
    @Tag
    private final String id;

    // 父书签ID。UUID
    @Tag
    private String parentId;

    // 书签标题
    @Tag
    private String title;

    // 书签详细内容
    @Tag
    private String content;

    // 书签类型
    @Tag
    private final BookmarkEnum bookmarkEnum;

    // 文件路径。
    @Tag
    private String filePath;

    // 行号。如果是文件，则为行号；如果是文件夹，则为空。
    @Tag
    private Integer line;

    @Tag
    private Integer order = 0;

    public Bookmark() {
        this.id = UUID.randomUUID().toString();
        this.bookmarkEnum = BookmarkEnum.BOOKMARK;
    }

    public Bookmark(String id, BookmarkEnum bookmarkEnum) {
        this.id = id;
        this.bookmarkEnum = bookmarkEnum;
    }

    public Bookmark(String id) {
        this.id = id;
        this.bookmarkEnum = BookmarkEnum.BOOKMARK;
    }

    public Bookmark(BookmarkEnum bookmarkEnum) {
        this.id = UUID.randomUUID().toString();
        this.bookmarkEnum = bookmarkEnum;
    }

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

    @Override
    public String toString() {
        return title;
    }

    public String getId() {
        return id;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        addDirty(Bookmark::getParentId);
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        addDirty(Bookmark::getTitle);
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        addDirty(Bookmark::getContent);
        this.content = content;
    }

    public BookmarkEnum getBookmarkEnum() {
        return bookmarkEnum;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        addDirty(Bookmark::getFilePath);
        this.filePath = filePath;
    }

    public int getLine() {
        return line;
    }

    public void setLine(Integer line) {
        addDirty(Bookmark::getLine);
        this.line = line;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public int compareTo(@NotNull Bookmark old) {
        if (old.getOrder() == null) {
            return -1;
        }
        return old.getOrder().compareTo(order);
    }
}
