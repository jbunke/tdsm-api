package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.MathLayer;

public final class GetValueNode extends LayerExprNode {
    public static final String GET = "get_value",
            MIN = "min_value", MAX = "max_value";

    private final String code;

    private GetValueNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final String code
    ) {
        super(pos, scope, TypeNode.getInt(), args);

        this.code = code;
    }

    public GetValueNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        this(pos, scope, args, GET);
    }

    public static GetValueNode max(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new GetValueNode(pos, scope, args, MAX);
    }

    public static GetValueNode min(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new GetValueNode(pos, scope, args, MIN);
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        if (layer instanceof MathLayer ml) {
            return switch (code) {
                case MAX -> ml.max;
                case MIN -> ml.min;
                default -> ml.getValue();
            };
        } else
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT, getPosition(),
                    "The layer '" + receiver.receiver() +
                            "' is not a math layer");

        return null;
    }
}
