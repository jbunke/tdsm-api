package com.jordanbunke.tdsm_api.ast.stat.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.AssetChoiceLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm_api.TDSMInterpreter;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;

public final class ParallelMatchersNode extends UtilStatNode {
    public static final String NAME = "parallel_matchers";

    public ParallelMatchersNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, args, LayerTypeNode.get(), LayerTypeNode.get());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final CustomizationLayer a = (CustomizationLayer) vs[0],
                b = (CustomizationLayer) vs[1];

        if (a instanceof AssetChoiceLayer aclA &&
                b instanceof AssetChoiceLayer aclB)
            AssetChoiceLayer.parallelMatchers(aclA, aclB);
        else
            TDSMInterpreter.failure(
                    "Couldn't set parallel matching asset choice layers",
                    "one or both layers provided wasn't an asset choice layer",
                    getPosition());

        return FuncControlFlow.cont();
    }
}
