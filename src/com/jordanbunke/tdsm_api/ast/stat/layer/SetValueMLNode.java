package com.jordanbunke.tdsm_api.ast.stat.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.MathLayer;
import com.jordanbunke.tdsm_api.TDSMInterpreter;

public final class SetValueMLNode extends LayerStatNode {
    public static final String NAME = "set_value";

    public SetValueMLNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeUtils.expectExact(TypeNode.getInt()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);
        final int value = (int) arguments.evaluate(symbolTable)[0];

        if (layer instanceof MathLayer ml)
            ml.setValue(value);
        else
            TDSMInterpreter.failure("The layer '" +
                    receiver.receiver() + "' isn't a math layer",
                    getPosition());

        return FuncControlFlow.cont();
    }
}
