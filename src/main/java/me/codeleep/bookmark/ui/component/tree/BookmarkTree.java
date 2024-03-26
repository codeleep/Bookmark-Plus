package me.codeleep.bookmark.ui.component.tree;

import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.Tree;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.repo.index.col.IdBookmarkIndex;
import me.codeleep.bookmark.repo.index.col.ParentIdBookmarkIndex;
import me.codeleep.bookmark.repo.model.Bookmark;
import me.codeleep.bookmark.repo.persistence.BookmarkPersistence;
import me.codeleep.bookmark.ui.component.tree.character.SimpleDragHandler;
import me.codeleep.bookmark.ui.component.tree.character.SimpleTreeCellRenderer;
import me.codeleep.bookmark.ui.component.tree.character.TreeNodeMouseAdapter;
import me.codeleep.bookmark.ui.component.tree.character.TreeNodeMouseMotionAdapter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.util.Comparator;
import java.util.List;

/**
 * @author: codeleep
 * @createTime: 2024/03/26 00:15
 * @description: 书签树
 */
public class BookmarkTree extends Tree {

    private final Project project;

    private final ParentIdBookmarkIndex parentIdBookmarkIndex;

    private final IdBookmarkIndex idBookmarkIndex;

    public BookmarkTree(Project project) {
        super();
        this.project = project;
        this.parentIdBookmarkIndex = ParentIdBookmarkIndex.getInstance(project);
        this.idBookmarkIndex = IdBookmarkIndex.getInstance(project);
        initData();
        initCharacter();
        initView();
    }

    public void saveTreeNode(@NotNull Bookmark bookmark) {
        // 获取选定的节点
        BookmarkTreeNode selectedNode = getLastSelectedNode();
        if (selectedNode == null) {
            selectedNode = (BookmarkTreeNode) getModel().getRoot();
        }
        boolean exist = IdBookmarkIndex.getInstance(project).isExist(bookmark.getId());
        if (exist) {
            BookmarkPersistence.getInstance(project).updateBookmark(bookmark);
        }else {
            BookmarkTreeNode parent = selectedNode.getBookmarkEnum() == BookmarkEnum.BOOKMARK_FOLDER ? selectedNode : (BookmarkTreeNode) selectedNode.getParent();
            // 新的分组节点
            BookmarkPersistence.getInstance(project).addBookmark(bookmark);
            BookmarkTreeNode bookmarkTreeNode = new BookmarkTreeNode(project, bookmark);
            getModel().insertNodeInto(bookmarkTreeNode, parent, parent.getChildCount());
        }
    }

    /**
     * 初始化数据相关
     */
    private void initData() {
        setModel(new DefaultTreeModel(reloadData()));
    }

    /**
     * 初始化视图相关
     */
    private void initView() {
        setCellRenderer(new SimpleTreeCellRenderer());
    }

    /**
     * 初始化行为相关
     */
    private void initCharacter() {
        // 选择模型
        getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
        // 拖拽处理器
        setDragEnabled(true);
        setDropMode(DropMode.ON_OR_INSERT);
        setTransferHandler(new SimpleDragHandler());
        // 注册鼠标操作: 右键菜单。双击事件等等
        addMouseListener(new TreeNodeMouseAdapter(project, this));
        addMouseMotionListener(new TreeNodeMouseMotionAdapter(project, this));
    }

    private BookmarkTreeNode reloadData() {
        return insertChild(new BookmarkTreeNode(project, idBookmarkIndex.findRoot()));
    }


    private BookmarkTreeNode insertChild(BookmarkTreeNode parentNode) {
        List<Bookmark> childList = parentIdBookmarkIndex.findByParentId(parentNode.getBookmark().getId());
        if (childList == null || childList.isEmpty()) {
            return parentNode;
        }
        List<BookmarkTreeNode> nodeList = childList.stream().sorted(Comparator.reverseOrder()).map(child -> new BookmarkTreeNode(project, child)).toList();
        for (BookmarkTreeNode nodeChild : nodeList) {
            parentNode.add(insertChild(nodeChild));
        }
        return parentNode;
    }

    @Override
    public DefaultTreeModel getModel() {
        return (DefaultTreeModel) super.treeModel;
    }

    public BookmarkTreeNode getLastSelectedNode() {
        return  (BookmarkTreeNode) getLastSelectedPathComponent();
    }

}
