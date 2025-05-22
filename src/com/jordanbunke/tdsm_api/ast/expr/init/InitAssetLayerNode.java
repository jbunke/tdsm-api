package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.ChildFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.tdsm.data.Replacement;
import com.jordanbunke.tdsm.data.func.ColorReplacementFunc;
import com.jordanbunke.tdsm.data.func.Composer;
import com.jordanbunke.tdsm.data.layer.AssetLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.util.DataProcessor;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

public final class InitAssetLayerNode extends InitExprNode {
    public static final String NAME = "asset_layer";

    public InitAssetLayerNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, LayerTypeNode.get(), args, TypeNode.getString(),
                TypeNode.arrayOf(TypeNode.getInt()), TypeNode.getImage(),
                DataProcessor.COMPOSER_TYPE, DataProcessor.REPLACE_FUNC_TYPE);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public CustomizationLayer evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String id = (String) vs[0];
        final int[] dims = ((ScriptArray) vs[1]).stream()
                .mapToInt(o -> (int) o).toArray();
        final GameImage asset = (GameImage) vs[2];
        final ChildFuncNode composerSource = (ChildFuncNode) vs[3],
                replaceSource = (ChildFuncNode) vs[4];

        if (id.isEmpty()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(0).getPosition(),
                    "Layer ID must be non-empty");
            return null;
        } else if (dims.length != 2) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(1).getPosition(),
                    "dims expects an int[] of 2 elements");
            return null;
        } else {
            try {
                final Bounds2D bounds = new Bounds2D(dims[0], dims[1]);

                final Composer composer = sheet -> {
                    ChildFuncNode inner = MetaFuncHelper.evaluate(
                            composerSource, symbolTable, ChildFuncNode.class,
                            arguments.get(3).getPosition(), sheet);

                    return s -> MetaFuncHelper.evaluate(inner, symbolTable,
                            GameImage.class, TextPosition.N_A, s);
                };

                final ColorReplacementFunc replaceFunc =
                        color -> MetaFuncHelper.evaluate(replaceSource,
                                symbolTable, Replacement.class,
                                arguments.get(4).getPosition(), color);

                return new AssetLayer(id, bounds, asset, composer, replaceFunc);
            } catch (IllegalArgumentException iae) {
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.CUSTOM_RT,
                        arguments.get(1).getPosition(),
                        "Width and height of the layer must be positive");
                return null;
            }
        }
    }
}
