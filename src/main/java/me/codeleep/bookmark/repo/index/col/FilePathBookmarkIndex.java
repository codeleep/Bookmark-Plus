package me.codeleep.bookmark.repo.index.col;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import me.codeleep.bookmark.repo.index.GroupBookmarkIndex;
import me.codeleep.bookmark.repo.model.Bookmark;

import java.util.List;
import java.util.function.Function;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 09:54
 * @description: 文件路径标签索引。filepath 具有不变性。所以可以作为这样使用
 */
@Service(Service.Level.PROJECT)
public final class FilePathBookmarkIndex extends GroupBookmarkIndex {

    private Project project;

    public static FilePathBookmarkIndex getInstance(Project project) {
        return project.getService(FilePathBookmarkIndex.class);
    }

    public List<Bookmark> findByFilePath(String filePath) {
        if (filePath == null) {
            return null;
        }
        return index.get(filePath);
    }


    @Override
    public Function<? super Bookmark, Object> index() {
        return Bookmark::getFilePath;
    }
}
