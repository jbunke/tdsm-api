package com.jordanbunke.tdsm_api.ast.expr.style;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.ast.type.AnimTypeNode;

import java.util.Arrays;

public final class GetAnimsNode extends StyleExprNode {
    public static final String ALL = "all_anims", GET = "get_anims";

    private final boolean all;

    private GetAnimsNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final boolean all
    ) {
        super(pos, scope, TypeNode.arrayOf(AnimTypeNode.get()), args);

        this.all = all;
    }

    public static GetAnimsNode all(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new GetAnimsNode(pos, scope, args, true);
    }

    public static GetAnimsNode get(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new GetAnimsNode(pos, scope, args, false);
    }

    @Override
    protected String funcName() {
        return all ? ALL : GET;
    }

    @Override
    public ScriptArray evaluate(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);

        return new ScriptArray(Arrays.stream(all
                ? style.animations : style.exportAnimations()));
    }
}
