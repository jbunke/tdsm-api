package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.DynamicFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FlexParamFunc;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.sprite.constituents.SpriteConstituent;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;

import static com.jordanbunke.tdsm_api.util.DataProcessor.COMPOSER_TYPE;


public final class LayerComposeNode extends LayerExprNode {
    public static final String NAME = "compose";

    private static final FuncTypeNode TYPE =
            (FuncTypeNode) COMPOSER_TYPE.getReturnType();

    public LayerComposeNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TYPE, args);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public DynamicFuncNode<?> evaluate(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        final SpriteConstituent<String> composeSource = layer.compose();
        final FlexParamFunc<GameImage> func = FlexParamFunc.one(
                composeSource::getSprite, String.class);

        return new DynamicFuncNode<>(
                TYPE.getParamTypes(),
                TYPE.getReturnType(), func);
    }
}
