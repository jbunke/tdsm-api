package com.jordanbunke.tdsm_api.ast.expr.style;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.util.DirConversion;

import java.util.Arrays;

public final class GetDirsNode extends StyleExprNode {
    public static final String ALL = "all_dirs", GET = "get_dirs";

    private final boolean all;

    private GetDirsNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final boolean all
    ) {
        super(pos, scope, TypeNode.arrayOf(TypeNode.getString()), args);

        this.all = all;
    }

    public static GetDirsNode all(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new GetDirsNode(pos, scope, args, true);
    }

    public static GetDirsNode get(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new GetDirsNode(pos, scope, args, false);
    }

    @Override
    protected String funcName() {
        return all ? ALL : GET;
    }

    @Override
    public ScriptArray evaluate(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);

        return new ScriptArray(Arrays.stream(all
                ? style.directions.order() : style.exportDirections())
                .map(DirConversion::from));
    }
}
