package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.ChildFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.MathLayer;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

import java.util.function.Function;

public final class InitMathLayerNode extends InitExprNode {
    public static final String NAME = "math_layer";

    public InitMathLayerNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, LayerTypeNode.get(), args, TypeNode.getString(),
                TypeNode.getInt(), TypeNode.getInt(), TypeNode.getInt(),
                new FuncTypeNode(new TypeNode[] { TypeNode.getInt() },
                        TypeNode.getString()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public CustomizationLayer evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String id = (String) vs[0];
        final int min = (int) vs[1], max = (int) vs[2], def = (int) vs[3];
        final ChildFuncNode formatSource = (ChildFuncNode) vs[4];

        if (id.isEmpty()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(0).getPosition(),
                    "Layer ID must be non-empty");
            return null;
        }

        final Function<Integer, String> formatFunc = i ->
                MetaFuncHelper.evaluate(formatSource, symbolTable,
                        String.class, arguments.get(4).getPosition(), i);

        return new MathLayer(id, min, max, def, formatFunc);
    }
}
