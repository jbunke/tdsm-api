package com.jordanbunke.tdsm_api.ast.stat.layer;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm_api.ast.type.ColSelTypeNode;

public final class AddInfluencesNode extends LayerStatNode {
    public static final String NAME = "add_influences";

    public AddInfluencesNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeUtils.expectExact(
                TypeNode.arrayOf(ColSelTypeNode.get())));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        final ColorSelection[] selections = ((ScriptArray) arguments.get(0)
                .evaluate(symbolTable)).stream()
                .map(o -> (ColorSelection) o).toArray(ColorSelection[]::new);

        layer.addInfluencingSelections(selections);

        return FuncControlFlow.cont();
    }
}
