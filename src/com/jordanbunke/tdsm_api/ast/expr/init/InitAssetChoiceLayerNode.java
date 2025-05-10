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
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.tdsm.data.func.Composer;
import com.jordanbunke.tdsm.data.layer.AssetChoiceLayer;
import com.jordanbunke.tdsm.data.layer.support.AssetChoiceTemplate;
import com.jordanbunke.tdsm.data.layer.support.NoAssetChoice;
import com.jordanbunke.tdsm.util.StringUtils;
import com.jordanbunke.tdsm_api.ast.type.AssetChoiceTypeNode;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.ast.type.NoChoiceTypeNode;
import com.jordanbunke.tdsm_api.util.DataProcessor;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

import java.util.function.Function;

public final class InitAssetChoiceLayerNode extends InitExprNode {
    public static final String NAME = "asset_choice_layer";

    public InitAssetChoiceLayerNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, LayerTypeNode.get(), args, TypeNode.getString(),
                TypeNode.arrayOf(TypeNode.getInt()),
                DataProcessor.ASSET_FETCHER_TYPE,
                TypeNode.arrayOf(AssetChoiceTypeNode.get()),
                NoChoiceTypeNode.get(), DataProcessor.COMPOSER_TYPE,
                TypeNode.arrayOf(TypeNode.getInt()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public AssetChoiceLayer evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String id = (String) vs[0];
        final int[] bounds = ((ScriptArray) vs[1]).stream()
                .mapToInt(o -> (int) o).toArray(),
                previewAt = ((ScriptArray) vs[6]).stream()
                        .mapToInt(o -> (int) o).toArray();
        final ChildFuncNode assetFetcherSource = (ChildFuncNode) vs[2],
                composerSource = (ChildFuncNode) vs[5];
        final AssetChoiceTemplate[] choices = ((ScriptArray) vs[3]).stream()
                .map(o -> (AssetChoiceTemplate) o)
                .toArray(AssetChoiceTemplate[]::new);
        final NoAssetChoice noChoice = (NoAssetChoice) vs[4];

        if (id.isEmpty()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(0).getPosition(),
                    "Layer ID must be non-empty");
            return null;
        } else if (bounds.length != 2) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(1).getPosition(),
                    "dims expects an int[] of 2 elements");
            return null;
        } else if (previewAt.length != 2) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(1).getPosition(),
                    "preview_coord expects an int[] of 2 elements");
            return null;
        } else {
            try {
                final Bounds2D dims = new Bounds2D(bounds[0], bounds[1]);
                final Coord2D preview =
                        new Coord2D(previewAt[0], previewAt[1]);

                final Function<String, GameImage> getter =
                        spriteID -> MetaFuncHelper.evaluate(assetFetcherSource,
                                symbolTable, GameImage.class,
                                arguments.get(2).getPosition(), spriteID);

                final Composer composer = sheet -> {
                    ChildFuncNode inner = MetaFuncHelper.evaluate(
                            composerSource, symbolTable, ChildFuncNode.class,
                            arguments.get(5).getPosition(), sheet);

                    return s -> MetaFuncHelper.evaluate(inner, symbolTable,
                            GameImage.class, TextPosition.N_A, s);
                };

                return new AssetChoiceLayer(id, StringUtils.nameFromID(id),
                        dims, getter, choices, composer, noChoice, preview);
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
