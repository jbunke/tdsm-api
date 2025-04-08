package com.jordanbunke.tdsm_api.ast.stat.multitype;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.ast.type.StyleTypeNode;

public final class RandomizeNode extends GenericFStatNode {
    public static final String NAME = "randomize";

    public RandomizeNode(TextPosition pos, ExpressionNode scope, ExpressionNode[] args) {
        super(pos, scope,
                new TypeNode[] { LayerTypeNode.get(), StyleTypeNode.get() },
                new Arguments(args));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(SymbolTable symbolTable) {
        if (receiver.getType(symbolTable).equals(StyleTypeNode.get()))
            ((Style) receiver.evaluate(symbolTable)).randomize();
        else
            ((CustomizationLayer) receiver.evaluate(symbolTable))
                    .randomize(true);

        return FuncControlFlow.cont();
    }
}
