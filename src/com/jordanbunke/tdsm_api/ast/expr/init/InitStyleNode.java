package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptList;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptMap;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.MapTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.tdsm.data.Animation;
import com.jordanbunke.tdsm.data.Directions;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.style.FromFileStyle;
import com.jordanbunke.tdsm_api.ast.type.AnimTypeNode;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.ast.type.StyleTypeNode;
import com.jordanbunke.tdsm_api.util.DataProcessor;

import static com.jordanbunke.tdsm.util.Constants.*;

public final class InitStyleNode extends InitExprNode {
    public static final String NAME = "style";

    public InitStyleNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, StyleTypeNode.get(), args,
                TypeNode.getString(), TypeNode.arrayOf(TypeNode.getInt()),
                TypeNode.arrayOf(TypeNode.getString()),
                TypeNode.getBool(),
                TypeNode.arrayOf(AnimTypeNode.get()),
                new MapTypeNode(TypeNode.getBool(),
                        TypeNode.listOf(LayerTypeNode.get())));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FromFileStyle evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);
        final String id = (String) vs[0];
        final int[] wh = ((ScriptArray) vs[1]).stream()
                .mapToInt(o -> (int) o).toArray();
        final String[] dirStrings = ((ScriptArray) vs[2]).stream()
                .map(String::valueOf).toArray(String[]::new);
        final boolean orientation = (boolean) vs[3];
        final Animation[] anims = ((ScriptArray) vs[4]).stream()
                .map(o -> (Animation) o).toArray(Animation[]::new);
        final ScriptMap layers = (ScriptMap) vs[5];

        final boolean customVal = LayerScopeConstNode.CUSTOM_VALUE;
        final CustomizationLayer[] custom = extractLayers(layers, customVal),
                assembly = extractLayers(layers, !customVal);

        final Bounds2D bounds = extractBounds(wh,
                arguments.get(1).getPosition());
        final Directions directions =
                DataProcessor.extractDirections(orientation,
                        dirStrings, arguments.get(2).getPosition());

        if (custom == null || assembly == null ||
                bounds == null || directions == null)
            return null;

        return new FromFileStyle(id, bounds,
                directions, anims, custom, assembly);
    }

    private Bounds2D extractBounds(
            final int[] wh, final TextPosition argPos
    ) {
        if (wh.length != 2) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    argPos, "Argument bounds expected a two-integer array; received " +
                            wh.length);
            return null;
        }

        final int w = wh[0], h = wh[1];

        if (w < MIN_SPRITE_EXPORT_W || w > MAX_SPRITE_EXPORT_W) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    argPos, "Width (" + w + ") must be between " +
                            MIN_SPRITE_EXPORT_W + " and " + MAX_SPRITE_EXPORT_W);
            return null;
        } else if (h < MIN_SPRITE_EXPORT_H || h > MAX_SPRITE_EXPORT_H) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    argPos, "Height (" + h + ") must be between " +
                            MIN_SPRITE_EXPORT_H + " and " + MAX_SPRITE_EXPORT_H);
            return null;
        }

        return new Bounds2D(w, h);
    }

    private CustomizationLayer[] extractLayers(
            final ScriptMap layers, final boolean scope
    ) {
        final Object value = layers.get(scope);

        if (value instanceof ScriptList contents)
            return contents.stream()
                    .filter(o -> o instanceof CustomizationLayer)
                    .map(o -> (CustomizationLayer) o)
                    .toArray(CustomizationLayer[]::new);
        else
            return null;
    }
}
