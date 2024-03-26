package me.codeleep.bookmark.ui.component.tree;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.JBMenuItem;
import com.intellij.openapi.ui.JBPopupMenu;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.common.I18N;
import me.codeleep.bookmark.ui.component.tree.action.CreatBookmarkFolderActionListener;
import me.codeleep.bookmark.ui.component.tree.action.DeleteBookmarkActionListener;
import me.codeleep.bookmark.ui.component.tree.action.EditBookmarkActionListener;
import me.codeleep.bookmark.ui.component.tree.action.EditBookmarkFolderActionListener;
import org.jetbrains.annotations.NotNull;

/**
 * @author: codeleep
 * @createTime: 2024/03/29 22:11
 * @description: 弹出菜单工具
 */
public class PopMenuTools {

    private final Project project;

    private final BookmarkTree tree;

    private BookmarkEnum bookmarkEnum;

    private Boolean isRoot;

    private JBPopupMenu popupMenu;

    public PopMenuTools(Project project, BookmarkTree tree){
        this.project = project;
        this.tree = tree;
    }

    public JBPopupMenu getBookmarkPopMenu(@NotNull BookmarkEnum bookmarkEnum, boolean isRoot) {
        if (bookmarkEnum == this.bookmarkEnum && isRoot == this.isRoot && popupMenu != null) {
            return this.popupMenu;
        }
        this.popupMenu = new JBPopupMenu();
        // 根目录
        if (isRoot) {
            popupMenu.add(creatBookmarkFolderMenu());
            return this.popupMenu;
        }
        // 非根
        if (bookmarkEnum == BookmarkEnum.BOOKMARK_FOLDER) {
            this.popupMenu.add(creatBookmarkFolderMenu());
            this.popupMenu.add(editBookmarkFolderMenu());
            this.popupMenu.add(deleteBookmarkMenu());
        }else {
            this.popupMenu.add(editBookmarkMenu());
            this.popupMenu.add(deleteBookmarkMenu());
        }
        return this.popupMenu;
    }

    public JBMenuItem creatBookmarkFolderMenu() {
        JBMenuItem addBookmarkFolderMenu = new JBMenuItem(I18N.get("bookmark.plus.bookmark.folder.add"));
        addBookmarkFolderMenu.addActionListener(new CreatBookmarkFolderActionListener(project, tree));
        return addBookmarkFolderMenu;
    }

    public JBMenuItem editBookmarkFolderMenu() {
        JBMenuItem editBookmarkFolderMenu = new JBMenuItem(I18N.get("bookmark.plus.bookmark.folder.edit"));
        editBookmarkFolderMenu.addActionListener(new EditBookmarkFolderActionListener(project, tree));
        return editBookmarkFolderMenu;
    }

    public JBMenuItem deleteBookmarkMenu() {
        JBMenuItem deleteBookmarkMenu = new JBMenuItem(I18N.get("bookmark.plus.bookmark.del"));
        deleteBookmarkMenu.addActionListener(new DeleteBookmarkActionListener(project, tree));
        return deleteBookmarkMenu;
    }

    public JBMenuItem editBookmarkMenu() {
        JBMenuItem editBookmarkMenu = new JBMenuItem(I18N.get("bookmark.plus.bookmark.edit"));
        editBookmarkMenu.addActionListener(new EditBookmarkActionListener(project, tree));
        return editBookmarkMenu;
    }


}
