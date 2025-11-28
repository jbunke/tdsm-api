package com.jordanbunke.tdsm_api.ast.expr.multitype;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.ColorSelectionLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm_api.ast.type.AssetChoiceTypeNode;
import com.jordanbunke.tdsm_api.ast.type.ColSelTypeNode;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.util.AssetChoiceConstruct;
import com.jordanbunke.tdsm_api.util.UpdateChecker;

import java.util.Arrays;

public final class GetColSelLayerNode extends GenericFExprNode {
    public static final String NAME = "get_col_sels";

    public GetColSelLayerNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, new TypeNode[] {
                LayerTypeNode.get(),
                AssetChoiceTypeNode.get()
        }, TypeNode.arrayOf(ColSelTypeNode.get()), new Arguments(args));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public ScriptArray evaluate(final SymbolTable symbolTable) {
        final TypeNode type = receiver.getType(symbolTable);
        final Object scopeVal = receiver.evaluate(symbolTable);

        if (type instanceof LayerTypeNode) {
            final CustomizationLayer layer = (CustomizationLayer) scopeVal;

            if (layer instanceof ColorSelectionLayer csl) {
                final ColorSelection[] selections = csl.getSelections();

                return new ScriptArray(Arrays.stream(selections)
                        .peek(cs -> UpdateChecker.get().link(cs, layer))
                        .map(cs -> cs));
            } else {
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.CUSTOM_RT, getPosition(),
                        "The layer '" + receiver.receiver() +
                                "' is not a color selection layer");
            }
        } else if (type instanceof AssetChoiceTypeNode) {
            final AssetChoiceConstruct acc = (AssetChoiceConstruct) scopeVal;

            if (acc.realized) {
                final ColorSelection[] selections = acc.getColorSelections();
                final CustomizationLayer layer = acc.getLayer();

                return new ScriptArray(Arrays.stream(selections)
                        .peek(cs -> UpdateChecker.get().link(cs, layer))
                        .map(cs -> cs));
            } else {
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.CUSTOM_RT, getPosition(),
                        "Attempting to call " + NAME +
                                "() on an unrealized asset_choice object '" +
                                receiver.receiver() + "'");
            }
        }

        return null;
    }
}
