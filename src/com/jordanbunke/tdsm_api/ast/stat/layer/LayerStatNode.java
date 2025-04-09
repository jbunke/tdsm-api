package com.jordanbunke.tdsm_api.ast.stat.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.std_lib.MemberFuncExecNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;

public abstract class LayerStatNode extends MemberFuncExecNode {
    public LayerStatNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final TypeNode[]... expectedArgTypes
    ) {
        super(pos, scope, LayerTypeNode.get(),
                args, expectedArgTypes);
    }

    protected final CustomizationLayer getLayer(final SymbolTable symbolTable) {
        return (CustomizationLayer) receiver.evaluate(symbolTable);
    }
}
