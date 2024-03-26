package me.codeleep.bookmark.ui.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import me.codeleep.bookmark.ui.component.tree.BookmarkTreeHolder;
import me.codeleep.bookmark.ui.component.tree.dialog.BookmarkDialogWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * @author: codeleep
 * @createTime: 2024/03/30 00:15
 * @description: 创建书签快捷键
 */
public class BookmarkCreateAction extends AnAction {

    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (project == null || editor == null || file == null) {
            return;
        }
        new BookmarkDialogWrapper(project, editor, file, BookmarkTreeHolder.getInstance(project).getBookmarkTree()).show();
    }

}
