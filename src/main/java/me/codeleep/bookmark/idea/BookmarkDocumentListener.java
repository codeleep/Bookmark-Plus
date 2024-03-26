package me.codeleep.bookmark.idea;

import com.intellij.diff.util.LineRange;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.impl.EditorFactoryImpl;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import me.codeleep.bookmark.repo.index.col.FilePathBookmarkIndex;
import me.codeleep.bookmark.repo.model.Bookmark;
import me.codeleep.bookmark.repo.persistence.BookmarkPersistence;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: codeleep
 * @createTime: 2024/03/30 10:11
 * @description: 文档变化监听器
 */
public class BookmarkDocumentListener  implements DocumentListener {

    private static final Logger LOG = Logger.getInstance(BookmarkDocumentListener.class);

    @Override
    public void beforeDocumentChange(@NotNull DocumentEvent event) {
        try {
            Document document = event.getDocument();
            CharSequence newFragment = event.getNewFragment();
            CharSequence oldFragment = event.getOldFragment();
            // 获取行变化
            String newStr = String.valueOf(newFragment);
            String oldStr = String.valueOf(oldFragment);
            int newLineCount = StringUtil.countChars(newStr, '\n');
            int oldLineCount = StringUtil.countChars(oldStr, '\n');
            if ((newLineCount <= 0 && oldLineCount <= 0) || newLineCount == oldLineCount){
                return;
            }
            VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(document);
            if (virtualFile == null) {
                return;
            }
            Editor editor = getEditor(document);
            if (editor == null) {
                return;
            }
            Project project = editor.getProject();
            if (project == null) {
                return;
            }
            FilePathBookmarkIndex filePathBookmarkIndex = FilePathBookmarkIndex.getInstance(project);
            List<Bookmark> onlyIndex = filePathBookmarkIndex.findByFilePath(virtualFile.getPath());
            // 空的直接返回
            if (onlyIndex == null || onlyIndex.isEmpty()) {
                return;
            }
            // 计算行变化
            int offset = event.getOffset();
            // 变化所在行
            int startLineNumber = 0;
            int endLineNumber = 0;
            boolean isAdd = true;
            if (newLineCount > oldLineCount) {
                isAdd = true;
                startLineNumber = document.getLineNumber(offset) + 1;
                endLineNumber = startLineNumber + newLineCount - oldLineCount;
            }else {
                isAdd = false;
                endLineNumber = document.getLineNumber(offset) + 1;
                startLineNumber = endLineNumber - oldLineCount + newLineCount;
            }
            LineRange lineRange = new LineRange(startLineNumber, endLineNumber);
            perceivedLineChange(project, virtualFile, onlyIndex, lineRange, isAdd);
        }catch (Exception e) {
            LOG.info("perceivedLineChange error", e);
        }
    }


    private void perceivedLineChange(Project project, VirtualFile virtualFile, List<Bookmark> list, LineRange lineRange, boolean isAdd) {
        if (list == null || list.isEmpty()) {
            return;
        }
        FilePathBookmarkIndex filePathBookmarkIndex = FilePathBookmarkIndex.getInstance(project);
        List<Bookmark> removeList = new ArrayList<>();
        for (Bookmark node : list) {
            if (node == null){
                continue;
            }
            int positionLine = Integer.parseInt(String.valueOf(node.getLine()));
            int rowGap = lineRange.end - lineRange.start;
            int changeLine = lineRange.start;
            if(isAdd) {
                if (positionLine + 1 < changeLine) {
                    continue;
                }
                node.setLine(positionLine + rowGap);
                node.setLine(positionLine + rowGap);
            }else {
                if (positionLine <= changeLine) {
                    continue;
                }
                if (positionLine < lineRange.end && positionLine > lineRange.start) {
                    removeList.add(node);
                    continue;
                }
                node.setLine(positionLine - rowGap);
                node.setLine(positionLine - rowGap);
            }
            BookmarkPersistence persistence = BookmarkPersistence.getInstance(project);
            removeList.forEach(persistence::removeBookmark);
        }
    }

    private Editor getEditor(Document document) {
        Editor[] editors = EditorFactoryImpl.getInstance().getEditors(document);
        if (editors.length >= 1){
            return editors[0];
        }
        return null;
    }

}
