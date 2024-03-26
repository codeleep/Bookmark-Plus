package me.codeleep.bookmark.ui.painter;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.EditorLinePainter;
import com.intellij.openapi.editor.LineExtensionInfo;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import me.codeleep.bookmark.repo.index.col.FilePathBookmarkIndex;
import me.codeleep.bookmark.repo.model.Bookmark;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author: codeleep
 * @createTime: 2024/03/30 10:06
 * @description: 行尾注释器
 */
public class LineEndPainter  extends EditorLinePainter {

    private static final Logger LOG = Logger.getInstance(LineEndPainter.class);

    private FilePathBookmarkIndex filePathBookmarkIndex;

    private Project project;

    @Override
    public Collection<LineExtensionInfo> getLineExtensions(Project project, VirtualFile virtualFile, int i) {
        if (this.project != project || filePathBookmarkIndex == null) {
            this.project = project;
            filePathBookmarkIndex = FilePathBookmarkIndex.getInstance(project);
        }
        List<LineExtensionInfo> result = new ArrayList<>();
        try {
            List<Bookmark> onlyIndex = filePathBookmarkIndex.findByFilePath(virtualFile.getPath());
            Bookmark bookmark = LineEndPainter.findLine(onlyIndex, i);
            if (bookmark == null) {
                return null;
            }
            result.add(new LineExtensionInfo(String.format(" // %s", bookmark.getTitle()), new TextAttributes(null, null, JBColor.GRAY, null, Font.PLAIN)));
            return result;
        } catch (Exception e) {
            LOG.error("渲染行尾注释失败 path:"+virtualFile.getPath(), e);
        }
        return null;
    }

    public static Bookmark findLine(List<Bookmark> list, int i) {
        if (list == null || list.isEmpty()){
            return null;
        }
        Optional<Bookmark> bookmarkNodeModel1 = list.stream().filter(bookmark -> bookmark.getLine() == i).findFirst();
        return bookmarkNodeModel1.orElse(null);
    }

}

