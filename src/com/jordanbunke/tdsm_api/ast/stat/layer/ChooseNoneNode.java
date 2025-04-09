package com.jordanbunke.tdsm_api.ast.stat.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.layer.AssetChoiceLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm_api.TDSMInterpreter;
import com.jordanbunke.tdsm_api.util.UpdateChecker;

public final class ChooseNoneNode extends LayerStatNode {
    public static final String NAME = "none";

    public ChooseNoneNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeUtils.expectExact());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        if (layer instanceof AssetChoiceLayer acl) {
            if (acl.noAssetChoice.valid) {
                acl.chooseFromScript(AssetChoiceLayer.NONE);
                UpdateChecker.get().ping(acl);
            } else
                TDSMInterpreter.failure(
                        "This layer does not support a no asset choice",
                        getPosition());
        } else
            TDSMInterpreter.failure(
                    "This layer isn't an asset choice layer",
                    getPosition());

        return FuncControlFlow.cont();
    }
}
