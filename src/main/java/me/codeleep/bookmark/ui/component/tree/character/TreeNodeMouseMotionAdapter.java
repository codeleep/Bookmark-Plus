package me.codeleep.bookmark.ui.component.tree.character;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.awt.RelativePoint;
import me.codeleep.bookmark.ui.component.special.BookmarkReadingView;
import me.codeleep.bookmark.ui.component.tree.BookmarkTree;
import me.codeleep.bookmark.ui.component.tree.BookmarkTreeNode;
import me.codeleep.bookmark.ui.component.tree.JBPopupHolder;

import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * @author: codeleep
 * @createTime: 2024/03/30 11:11
 * @description: 鼠标移动适配
 */
public class TreeNodeMouseMotionAdapter implements MouseMotionListener {

    private final Project project;

    private final BookmarkTree bookmarkTree;

    private BookmarkTreeNode currentNode;

    public TreeNodeMouseMotionAdapter(Project project, BookmarkTree bookmarkTree) {
        this.project = project;
        this.bookmarkTree = bookmarkTree;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Get the selected node
        TreePath path = bookmarkTree.getPathForLocation(e.getX(), e.getY());
        if (path == null) {
            JBPopupHolder.getInstance(project).cancel();
            return;
        }
        BookmarkTreeNode node = (BookmarkTreeNode) path.getLastPathComponent();
        if (node == null) {
            JBPopupHolder.getInstance(project).cancel();
            return;
        }
        if (node.getBookmark() == null) {
            JBPopupHolder.getInstance(project).cancel();
            return;
        }
        // 防止一直
        if (currentNode == node) {
            return;
        }
        this.currentNode = node;
        JBPopupFactory popupFactory = JBPopupFactory.getInstance();
        JBPopup jbPopup = popupFactory.createComponentPopupBuilder(new BookmarkReadingView(node.getBookmark()), null)
                .setFocusable(true)
                .setResizable(true)
                .setRequestFocus(true)
                .createPopup();
        if (JBPopupHolder.getInstance(project).setJbPopup(jbPopup)) {
            jbPopup.show(RelativePoint.fromScreen(new Point(e.getLocationOnScreen().x + 5, e.getLocationOnScreen().y + 10)));
        }
    }
}
