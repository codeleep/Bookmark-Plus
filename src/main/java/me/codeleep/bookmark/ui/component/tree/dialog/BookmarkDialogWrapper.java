package me.codeleep.bookmark.ui.component.tree.dialog;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import me.codeleep.bookmark.common.Constant;
import me.codeleep.bookmark.common.I18N;
import me.codeleep.bookmark.repo.model.Bookmark;
import me.codeleep.bookmark.repo.persistence.BookmarkPersistence;
import me.codeleep.bookmark.ui.component.special.BookmarkEditView;
import me.codeleep.bookmark.ui.component.tree.BookmarkTree;
import me.codeleep.bookmark.ui.component.tree.BookmarkTreeHolder;
import me.codeleep.bookmark.ui.component.tree.BookmarkTreeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author: codeleep
 * @createTime: 2024/03/29 23:18
 * @description: 书签对话框
 */
public class BookmarkDialogWrapper extends DialogWrapper {

    private final Bookmark bookmark;

    private final BookmarkEditView bookmarkEditView;

    private final Project project;

    public BookmarkDialogWrapper(@Nullable Project project, @Nullable Bookmark bookmark) {
        super(project, false);
        this.project = project;
        this.bookmark =  bookmark;
        this.bookmarkEditView = new BookmarkEditView(this.bookmark);
        initBaseInfo();
        init();
    }
    public BookmarkDialogWrapper(@Nullable Project project, @NotNull Editor editor, @NotNull VirtualFile file, @NotNull BookmarkTree bookmarkTree) {
        super(project, false);
        this.project = project;
        this.bookmark =  creatBookmark(editor,file, bookmarkTree);
        this.bookmarkEditView = new BookmarkEditView(this.bookmark);
        initBaseInfo();
        init();
    }

    private void initBaseInfo() {
        setTitle(I18N.get("bookmark.plus.bookmark.add"));
        setOKActionEnabled(true);
        setCancelButtonText(I18N.get("bookmark.plus.common.cancel"));
        setOKButtonText(I18N.get("bookmark.plus.common.confirm"));
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return this.bookmarkEditView;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return this.bookmarkEditView.getTitleLabel();
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        Bookmark bookmark = this.bookmarkEditView.getBookmark();
        BookmarkPersistence.getInstance(project).saveBookmark(bookmark);
        BookmarkTreeHolder.getInstance(project).getBookmarkTree().saveTreeNode(bookmark);
    }

    private Bookmark creatBookmark(Editor editor,VirtualFile file, BookmarkTree bookmarkTree) {
        Bookmark newBookmark = new Bookmark();
        CaretModel caretModel = editor.getCaretModel();
        // 获取行号
        int line = caretModel.getLogicalPosition().line;
        newBookmark.setLine(line);
        newBookmark.setFilePath(file.getPath());

        BookmarkTreeNode lastSelectedNode = bookmarkTree.getLastSelectedNode();
        if (lastSelectedNode == null) {
            newBookmark.setParentId(Constant.ROOT_NODE);
        }else {
            newBookmark.setParentId(lastSelectedNode.getBookmark().getId());
        }
        return newBookmark;
    }
}
