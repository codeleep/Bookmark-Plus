package me.codeleep.bookmark.ui.component.tree.character;

import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 * @author: codeleep
 * @createTime: 2024/03/29 11:05
 * @description: 简单转移行为
 */
public class SimpleTransferable  implements Transferable {

    public static final DataFlavor NODES_FLAVOR = new DataFlavor(int[].class, "Tree Rows");

    private final int[] rows;

    public SimpleTransferable(int[] rows) {
        this.rows = rows;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{NODES_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(NODES_FLAVOR);
    }

    @Override
    public @NotNull Object getTransferData(@NotNull DataFlavor flavor) throws UnsupportedFlavorException {
        if (isDataFlavorSupported(flavor)) {
            return rows;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}