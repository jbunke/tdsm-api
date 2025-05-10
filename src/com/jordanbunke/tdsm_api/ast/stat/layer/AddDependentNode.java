package com.jordanbunke.tdsm_api.ast.stat.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;

public final class AddDependentNode extends LayerStatNode {
    public static final String NAME = "add_dependent";

    public AddDependentNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeUtils.expectExact(LayerTypeNode.get()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable),
                dependent = (CustomizationLayer) arguments.get(0)
                        .evaluate(symbolTable);

        layer.addDependent(dependent);

        return FuncControlFlow.cont();
    }
}
