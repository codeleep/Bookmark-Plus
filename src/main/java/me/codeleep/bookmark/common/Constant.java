package me.codeleep.bookmark.common;

import com.intellij.openapi.ui.InputValidatorEx;
import org.jsoup.internal.StringUtil;

/**
 * @author: codeleep
 * @createTime: 2024/03/25 23:53
 * @description: 常量
 */
public interface Constant {

    public static final String ROOT_NODE = "default";

    public static final String GROUP_NULL = "group_null";


    // 校验是否为空
    public static final InputValidatorEx INPUT_VALIDATOR_EX = inputString -> StringUtil.isBlank(inputString) ? I18N.get("bookmark.plus.bookmark.folder.check_message") : null;

}
