package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.ChildFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.DecisionLayer;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

public final class InitDecisionLayerNode extends InitExprNode {
    public static final String NAME = "decision_layer";

    public InitDecisionLayerNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, LayerTypeNode.get(), args, TypeNode.getString(),
                new FuncTypeNode(new TypeNode[] {}, LayerTypeNode.get()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);
        final String id = (String) vs[0];
        final ChildFuncNode logic = (ChildFuncNode) vs[1];

        return new DecisionLayer(id, () -> MetaFuncHelper.evaluate(logic,
                symbolTable, CustomizationLayer.class,
                arguments.get(1).getPosition()));
    }
}
