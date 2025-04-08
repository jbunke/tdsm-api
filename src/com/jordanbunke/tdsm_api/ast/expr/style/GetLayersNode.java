package com.jordanbunke.tdsm_api.ast.expr.style;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptList;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;

import java.util.List;

public final class GetLayersNode extends StyleExprNode {
    public static final String ASSEMBLY = "assembly", CUSTOM = "custom";

    public final boolean assembly;

    private GetLayersNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final boolean assembly
    ) {
        super(pos, scope, TypeNode.listOf(LayerTypeNode.get()), args);

        this.assembly = assembly;
    }

    public static GetLayersNode assembly(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new GetLayersNode(pos, scope, args, true);
    }

    public static GetLayersNode custom(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new GetLayersNode(pos, scope, args, false);
    }

    @Override
    protected String funcName() {
        return assembly ? ASSEMBLY : CUSTOM;
    }

    @Override
    public ScriptList evaluate(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);

        final List<CustomizationLayer> list = assembly
                ? style.layers.assembly()
                : style.layers.customization();

        return new ScriptList(list.stream().map(l -> l));
    }
}
