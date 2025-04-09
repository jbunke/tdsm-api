package com.jordanbunke.tdsm_api.ast.stat.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;

public final class LockLayerNode extends LayerStatNode {
    public static final String LOCK = "lock", UNLOCK = "unlock";

    private final boolean lock;

    private LockLayerNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final boolean lock
    ) {
        super(pos, scope, args, TypeUtils.expectExact());

        this.lock = lock;
    }

    public static LockLayerNode lock(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new LockLayerNode(pos, scope, args, true);
    }

    public static LockLayerNode unlock(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new LockLayerNode(pos, scope, args, false);
    }

    @Override
    protected String funcName() {
        return lock ? LOCK : UNLOCK;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        if (lock)
            layer.lock();
        else
            layer.unlock();

        return FuncControlFlow.cont();
    }
}
