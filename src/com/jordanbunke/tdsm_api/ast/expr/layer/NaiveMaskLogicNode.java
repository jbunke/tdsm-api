package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.ChildFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.DynamicFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FlexParamFunc;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.sprite.constituents.SpriteConstituent;
import com.jordanbunke.tdsm.data.layer.AbstractACLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.MaskLayer;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

import java.util.function.Function;

import static com.jordanbunke.tdsm_api.util.DataProcessor.*;

public final class NaiveMaskLogicNode extends LayerExprNode {
    public static final String NAME = "naive_mask_logic";

    private static final FuncTypeNode TYPE =
            (FuncTypeNode) COMPOSER_TYPE.getReturnType();

    public NaiveMaskLogicNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TYPE, args, ASSET_FETCHER_TYPE);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public DynamicFuncNode<?> evaluate(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        final ChildFuncNode assetFetcherSource =
                (ChildFuncNode) arguments.get(0).evaluate(symbolTable);

        if (!(layer instanceof AbstractACLayer projector)) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    getPosition(),
                    "Layer must be an asset choice layer or dependent layer");
            return null;
        } else {
            final FlexParamFunc<GameImage> func =
                    getFunc(symbolTable, projector, assetFetcherSource);

            return new DynamicFuncNode<>(
                    TYPE.getParamTypes(), TYPE.getReturnType(), func);
        }
    }

    private FlexParamFunc<GameImage> getFunc(
            final SymbolTable symbolTable, final AbstractACLayer projector,
            final ChildFuncNode assetFetcherSource
    ) {
        final Function<String, GameImage> assetFetcher =
                id -> MetaFuncHelper.evaluate(
                        assetFetcherSource, symbolTable, GameImage.class,
                        arguments.get(0).getPosition(), id);

        final SpriteConstituent<String> funcSource =
                MaskLayer.naiveLogic(projector, assetFetcher);

        return FlexParamFunc.one(funcSource::getSprite, String.class);
    }
}
