package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.ChildFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.sprite.constituents.SpriteConstituent;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.MaskLayer;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

import static com.jordanbunke.tdsm_api.util.DataProcessor.COMPOSER_TYPE;

public final class InitMaskLayerNode extends InitExprNode {
    public static final String NAME = "mask_layer";

    private static final FuncTypeNode LOGIC_TYPE =
            (FuncTypeNode) COMPOSER_TYPE.getReturnType();

    public InitMaskLayerNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, LayerTypeNode.get(), args, TypeNode.getString(),
                TypeNode.arrayOf(LayerTypeNode.get()), LOGIC_TYPE);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public MaskLayer evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String id = (String) vs[0];
        final CustomizationLayer[] targets = ((ScriptArray) vs[1]).stream()
                .map(o -> (CustomizationLayer) o)
                .toArray(CustomizationLayer[]::new);
        final ChildFuncNode logicSource = (ChildFuncNode) vs[2];

        if (id.isEmpty()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(0).getPosition(),
                    "Layer ID must be non-empty");
            return null;
        }

        final SpriteConstituent<String> logic = spriteID ->
                MetaFuncHelper.evaluate(logicSource, symbolTable,
                        GameImage.class, arguments.get(2).getPosition(),
                        spriteID);

        return new MaskLayer(id, targets, logic);
    }
}
