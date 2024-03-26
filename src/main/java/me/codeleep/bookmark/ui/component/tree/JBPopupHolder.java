package me.codeleep.bookmark.ui.component.tree;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;

/**
 * @author: codeleep
 * @createTime: 2024/03/30 11:01
 * @description: 弹窗持有器
 */
@Service(Service.Level.PROJECT)
public final class JBPopupHolder {

    private JBPopup jbPopup;

    public static JBPopupHolder getInstance(Project project) {
        return project.getService(JBPopupHolder.class);
    }

    public JBPopup getJbPopup() {
        return jbPopup;
    }

    public boolean setJbPopup(JBPopup jbPopup) {
        if (this.jbPopup == jbPopup) {
            return false;
        }
        cancel();
        this.jbPopup = jbPopup;
        return true;
    }


    public void cancel() {
        if (jbPopup != null) {
            jbPopup.cancel();
        }
    }
}
