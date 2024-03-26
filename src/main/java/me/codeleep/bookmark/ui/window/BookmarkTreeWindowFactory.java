package me.codeleep.bookmark.ui.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.ui.JBUI;
import me.codeleep.bookmark.repo.persistence.BookmarkPersistence;
import me.codeleep.bookmark.ui.component.special.BookmarkEditView;
import me.codeleep.bookmark.ui.component.tree.BookmarkTree;
import me.codeleep.bookmark.ui.component.tree.BookmarkTreeHolder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 19:01
 * @description: 书签树窗口工厂
 */
public class BookmarkTreeWindowFactory implements ToolWindowFactory {

    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        // 加载数据
        BookmarkPersistence.getInstance(toolWindow.getProject());
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content regularRetention = contentFactory.createContent(initJComponent(project, toolWindow), null, false);
        toolWindow.getContentManager().addContent(regularRetention);
    }

    private JComponent initJComponent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        BookmarkTree bookmarkTree = new BookmarkTree(project);
        BookmarkTreeHolder.getInstance(project).setBookmarkTree(bookmarkTree);
        JBScrollPane scrollPane = new JBScrollPane(bookmarkTree);
        scrollPane.setBorder(JBUI.Borders.empty());
        return scrollPane;
    }

}
