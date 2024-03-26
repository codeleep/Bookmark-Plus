package me.codeleep.bookmark.ui.component.tree.character;

import com.intellij.openapi.diagnostic.Logger;
import me.codeleep.bookmark.common.BookmarkEnum;
import me.codeleep.bookmark.ui.component.tree.BookmarkTree;
import me.codeleep.bookmark.ui.component.tree.BookmarkTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: codeleep
 * @createTime: 2024/03/29 10:53
 * @description: 简单拖拽处理器
 */
public class SimpleDragHandler extends TransferHandler {

    private static final Logger LOG = Logger.getInstance(SimpleDragHandler.class);

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        BookmarkTree tree = (BookmarkTree) c;
        int[] paths = tree.getSelectionRows();
        if (paths != null && paths.length > 0) {
            return new SimpleTransferable(paths);
        }
        return null;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (action != MOVE) {
            return;
        }
        super.exportDone(source, data, action);
    }

    @Override
    public boolean canImport(TransferSupport support) {
        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        TreePath destPath = dl.getPath();
        if (destPath == null) {
            return false;
        }
        BookmarkTreeNode targetNode = (BookmarkTreeNode) destPath.getLastPathComponent();
        return targetNode != null && targetNode.getBookmarkEnum() == BookmarkEnum.BOOKMARK_FOLDER;
    }

    @Override
    public boolean importData(TransferSupport support) {
        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        BookmarkTree tree = (BookmarkTree) support.getComponent();
        TreePath destPath = dl.getPath();
        BookmarkTreeNode targetNode = (BookmarkTreeNode) destPath.getLastPathComponent();
        try {
            Transferable transferable = support.getTransferable();
            int[] rows = (int[]) transferable.getTransferData(SimpleTransferable.NODES_FLAVOR);
            DefaultTreeModel model = tree.getModel();
            List<BookmarkTreeNode> nodes = Arrays.stream(rows).mapToObj(row -> {
                        TreePath path = tree.getPathForRow(row);
                        return path != null ? (BookmarkTreeNode) path.getLastPathComponent() : null;
                    }).collect(Collectors.toList());
            int childIndex = dl.getChildIndex();
            if (-1 == childIndex) {
                for (BookmarkTreeNode node : nodes) {
                    // 目标节点不能是拖动节点的后代，拖动节点不能是目标节点的直接子代
                    if (!targetNode.isNodeAncestor(node) && !targetNode.isNodeChild(node)) {
                        model.removeNodeFromParent(node);
                        model.insertNodeInto(node, targetNode, targetNode.getChildCount());
                        node.switchBloodRelationship(targetNode);
                    }
                }
            } else {
                Collections.reverse(nodes);
                for (BookmarkTreeNode node : nodes) {
                    // 目标节点不能是拖动节点的后代，拖动节点不能是目标节点的直接子代
                    if (!targetNode.isNodeAncestor(node)) {
                        if (targetNode.isNodeChild(node)) {
                            int index = targetNode.getIndex(node);
                            if (childIndex > index) {
                                childIndex = childIndex - 1;
                            }
                        }
                        model.removeNodeFromParent(node);
                        model.insertNodeInto(node, targetNode, childIndex);
                        node.switchBloodRelationship(targetNode);
                    }
                }
            }
            // 重新设置order属性
            tree.expandPath(destPath);
            resetOrder(targetNode);
            return true;
        } catch (Exception e) {
            LOG.error("Drag failure. ", e);
        }
        return false;
    }

    private void resetOrder(BookmarkTreeNode node) {
        node.children().asIterator().forEachRemaining(treeNode -> {
            BookmarkTreeNode bookmarkTreeNode = (BookmarkTreeNode) treeNode;
            bookmarkTreeNode.setOrderByNode();
        });
    }
}