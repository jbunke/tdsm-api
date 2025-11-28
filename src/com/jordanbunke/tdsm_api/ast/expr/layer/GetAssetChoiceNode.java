package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.AssetChoiceLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm_api.ast.type.AssetChoiceTypeNode;
import com.jordanbunke.tdsm_api.util.AssetChoiceConstruct;

public final class GetAssetChoiceNode extends LayerExprNode {
    public static final String NAME = "get_asset_choice";

    public GetAssetChoiceNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, AssetChoiceTypeNode.get(), args);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public AssetChoiceConstruct evaluate(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        if (layer instanceof AssetChoiceLayer acl) {
            if (acl.hasChoice())
                return AssetChoiceConstruct.real(acl.getChoice());
            else
                ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                        getPosition(),
                        "Asset choice layer '" + receiver.receiver() +
                                "' has no selection");
        } else
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    getPosition(),
                    "Attempting to call " + NAME +
                            "() on a layer isn't an asset choice layer");

        return null;
    }
}
