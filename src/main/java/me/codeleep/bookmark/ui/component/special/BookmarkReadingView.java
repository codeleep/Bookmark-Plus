package me.codeleep.bookmark.ui.component.special;

import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.HtmlPanel;
import com.intellij.util.ui.JBUI;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.repo.model.Bookmark;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @author: codeleep
 * @createTime: 2024/03/30 10:48
 * @description:
 */
public class BookmarkReadingView extends JBPanel<BookmarkReadingView> {

    public BookmarkReadingView(Bookmark model) {
        setLayout(new BorderLayout());
        TipHtml tipHtmlPanel = new TipHtml(model);
        tipHtmlPanel.setSize(tipHtmlPanel.getPreferredSize());
        JBScrollPane scrollPane = new JBScrollPane(tipHtmlPanel);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(6, Integer.MAX_VALUE));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(JBUI.Borders.empty());
        add(scrollPane);
    }

    private static class TipHtml extends HtmlPanel {

        public TipHtml(Bookmark bookmark) {
            super();
            setBody(getContent(bookmark));
            Border borderWithPadding = JBUI.Borders.empty(0, 10, 10, 10);
            setBorder(borderWithPadding);
        }

        @Override
        protected @NotNull @Nls String getBody() {
            return getText();
        }


        private String getContent(Bookmark bookmark) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("<h3>%s</h3>", bookmark.getTitle()));
            if (bookmark.getBookmarkEnum() == BookmarkEnum.BOOKMARK) {
                sb.append(bookmark.getContent());
            }
            return sb.toString();
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension preferredSize = super.getPreferredSize();
            if (preferredSize.width > 300) {
                preferredSize.width = 300;
            }
            return preferredSize;
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            Dimension preferredSize = getPreferredSize();
            return new Dimension(Math.min(preferredSize.width, 300), Math.min(preferredSize.height, 150));
        }
    }
}
