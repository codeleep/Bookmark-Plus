package me.codeleep.bookmark.repo.model.po;

import com.intellij.util.xmlb.annotations.XCollection;
import me.codeleep.bookmark.repo.model.Bookmark;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 22:52
 * @description: 持久化对象
 */
@XmlRootElement
public class BookmarkRepoPo {

    @XCollection(style = XCollection.Style.v2, propertyElementName = "bookmarks", elementTypes = {Bookmark.class})
    private List<Bookmark> bookmarks;

    public BookmarkRepoPo() {

    }

    public BookmarkRepoPo(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public boolean removeBookmark(Bookmark bookmark) {
        if (this.bookmarks != null) {
            return this.bookmarks.remove(bookmark);
        }
        return false;
    }


    public void addBookmark(Bookmark bookmark) {
        if (bookmarks == null) {
            bookmarks = new ArrayList<>();
        }
        bookmarks.add(bookmark);
        this.bookmarks = this.bookmarks.stream().distinct().collect(Collectors.toList());
    }
}
