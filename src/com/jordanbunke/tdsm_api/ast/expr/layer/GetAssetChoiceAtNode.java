package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.AssetChoiceLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm_api.ast.type.AssetChoiceTypeNode;
import com.jordanbunke.tdsm_api.util.AssetChoiceConstruct;

public final class GetAssetChoiceAtNode extends LayerExprNode {
    public static final String NAME = "get_asset_choice_at";

    public GetAssetChoiceAtNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, AssetChoiceTypeNode.get(), args, TypeNode.getInt());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public AssetChoiceConstruct evaluate(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        final int index = (int) arguments.get(0).evaluate(symbolTable);

        if (layer instanceof AssetChoiceLayer acl) {
            final int numChoices = acl.getNumChoices();

            if (index < 0) {
                ScriptErrorLog.runtimeError(arguments.get(0).getPosition(),
                        "Index cannot be negative");
                return null;
            } else if (index >= numChoices) {
                ScriptErrorLog.runtimeError(arguments.get(0).getPosition(),
                        "Index must be less than the number of possible choices");
                return null;
            }

            return AssetChoiceConstruct.real(acl.getChoiceAt(index));
        }

        ScriptErrorLog.runtimeError(getPosition(),
                "Attempting to call " + NAME +
                        "() on a layer isn't an asset choice layer");
        return null;
    }
}
