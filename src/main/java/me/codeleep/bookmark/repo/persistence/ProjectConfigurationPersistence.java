package me.codeleep.bookmark.repo.persistence;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author: codeleep
 * @createTime: 2024/03/25 23:28
 * @description: 书签存储
 */
@State(
        name = "ProjectConfigurationPersistence",
        storages = {@Storage("ProjectConfigurationPersistence.xml")}
)
public final class ProjectConfigurationPersistence implements PersistentStateComponent<ProjectConfigurationPersistence> {

    private static final Logger LOG = Logger.getInstance(ProjectConfigurationPersistence.class);

    private final Project project;

    private ProjectConfigurationPersistence projectConfigurationPersistence;

    public ProjectConfigurationPersistence(Project project) {
        this.project = project;
    }

    public static ProjectConfigurationPersistence getInstance(Project project) {
        return project.getService(ProjectConfigurationPersistence.class);
    }

    @Override
    public @Nullable ProjectConfigurationPersistence getState() {
        // TODO 空判断
        return projectConfigurationPersistence;
    }

    @Override
    public void loadState(@NotNull ProjectConfigurationPersistence configurationPersistence) {
        this.projectConfigurationPersistence = configurationPersistence;
    }
}
