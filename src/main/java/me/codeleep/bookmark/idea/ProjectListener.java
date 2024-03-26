package me.codeleep.bookmark.idea;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import me.codeleep.bookmark.repo.persistence.BookmarkPersistence;
import org.jetbrains.annotations.NotNull;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 15:40
 * @description: 项目启动监听器
 */
public class ProjectListener implements StartupActivity {


    @Override
    public void runActivity(@NotNull Project project) {
        // 触发数据加载当做
        BookmarkPersistence.getInstance(project);
    }

}