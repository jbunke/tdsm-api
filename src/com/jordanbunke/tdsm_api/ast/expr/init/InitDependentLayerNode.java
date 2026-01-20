package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.ChildFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.AssetChoiceLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.DependentComponentLayer;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.util.DataProcessor;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

import java.util.function.Function;

public final class InitDependentLayerNode extends InitExprNode {
    public static final String NAME = "dependent_layer";

    public InitDependentLayerNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, LayerTypeNode.get(), args, TypeNode.getString(),
                DataProcessor.ASSET_FETCHER_TYPE, LayerTypeNode.get(),
                TypeNode.getInt());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public CustomizationLayer evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String id = (String) vs[0];
        final ChildFuncNode assetFetcherSource = (ChildFuncNode) vs[1];
        final CustomizationLayer refLayer = (CustomizationLayer) vs[2];
        final int relIndex = (int) vs[3];

        if (id.isEmpty()) {
            ScriptErrorLog.runtimeError(arguments.get(0).getPosition(),
                    "Layer ID must be non-empty");
            return null;
        } else if (!(refLayer instanceof AssetChoiceLayer refACL)) {
            ScriptErrorLog.runtimeError(arguments.get(2).getPosition(),
                    "Reference layer must be an asset choice layer");
            return null;
        } else if (relIndex == 0) {
            ScriptErrorLog.runtimeError(arguments.get(0).getPosition(),
                    "Relative index cannot be 0");
            return null;
        } else {
            final Function<String, GameImage> getter =
                    spriteID -> MetaFuncHelper.evaluate(assetFetcherSource,
                            symbolTable, GameImage.class,
                            arguments.get(1).getPosition(), spriteID);

            return new DependentComponentLayer(id, getter, refACL, relIndex);
        }
    }
}
