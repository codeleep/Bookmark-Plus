package me.codeleep.bookmark.ui.component.special;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.components.JBTextField;
import me.codeleep.bookmark.repo.model.Bookmark;

import javax.swing.*;

/**
 * @author: codeleep
 * @createTime: 2024/03/29 21:47
 * @description:
 */
public class BookmarkEditView extends JPanel {
    private JBLabel titleLabel;
    private JBLabel contentLabel;
    private JBTextField titleInput;
    private JBScrollPane contentInputPanel;
    private JBTextArea contentInput;
    private JPanel editPanel;

    private final Bookmark bookmark;


    public BookmarkEditView(Bookmark bookmark) {
        add(editPanel);
        this.bookmark = bookmark == null ? new Bookmark() : bookmark;
        titleInput.setText(this.bookmark.getTitle());
        contentInput.setText(this.bookmark.getContent());
    }

    public JComponent getTitleLabel() {
        return this.titleLabel;
    }

    public Bookmark getBookmark() {
        this.bookmark.setTitle(titleInput.getText());
        this.bookmark.setContent(contentInput.getText());
        return this.bookmark;
    }
}
