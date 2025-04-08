package com.jordanbunke.tdsm_api.ast.expr.style;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.tdsm.data.style.Style;

public final class SpriteDimsNode extends StyleExprNode {
    public static final String
            DEF_DIMS = "def_sprite_dims", DIMS = "sprite_dims";

    private final boolean def;

    private SpriteDimsNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final boolean def
    ) {
        super(pos, scope, TypeNode.arrayOf(TypeNode.getInt()), args);

        this.def = def;
    }

    public static SpriteDimsNode defDims(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new SpriteDimsNode(pos, scope, args, true);
    }

    public static SpriteDimsNode dims(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new SpriteDimsNode(pos, scope, args, false);
    }

    @Override
    protected String funcName() {
        return def ? DEF_DIMS : DIMS;
    }

    @Override
    public ScriptArray evaluate(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);

        final Bounds2D dims = def ? style.dims : style.getExportSpriteDims();

        final ScriptArray output = new ScriptArray(2);

        output.set(0, dims.width());
        output.set(1, dims.height());

        return output;
    }
}
