package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.ChildFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.DynamicFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FlexParamFunc;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.sprite.SpriteSheet;
import com.jordanbunke.delta_time.sprite.constituents.SpriteConstituent;
import com.jordanbunke.tdsm.data.Animation;
import com.jordanbunke.tdsm.data.Directions;
import com.jordanbunke.tdsm.data.func.Composer;
import com.jordanbunke.tdsm_api.ast.type.AnimTypeNode;

import static com.jordanbunke.tdsm_api.util.DataProcessor.*;

public final class DefaultComposerNode extends InitExprNode {
    public static final String NAME = "default_composer";

    public DefaultComposerNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, COMPOSER_TYPE, args,
                TypeNode.arrayOf(TypeNode.getString()),
                TypeNode.getBool(),
                TypeNode.arrayOf(AnimTypeNode.get()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public DynamicFuncNode<?> evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String[] dirStrings = ((ScriptArray) vs[0]).stream()
                .map(String::valueOf).toArray(String[]::new);
        final boolean orientation = (boolean) vs[1];
        final Animation[] anims = ((ScriptArray) vs[2]).stream()
                .map(o -> (Animation) o).toArray(Animation[]::new);

        final Directions directions = extractDirections(orientation,
                dirStrings, arguments.get(0).getPosition());

        final FlexParamFunc<ChildFuncNode> func = getFunc(directions, anims);

        return new DynamicFuncNode<>(
                COMPOSER_TYPE.getParamTypes(),
                COMPOSER_TYPE.getReturnType(), func);
    }

    private FlexParamFunc<ChildFuncNode> getFunc(
            final Directions directions, final Animation[] anims
    ) {
        final Composer composer = defaultComposer(directions, anims);

        return FlexParamFunc.one(sheet -> {
            SpriteConstituent<String> sc = composer.build(sheet);

            final FuncTypeNode innerType =
                    (FuncTypeNode) COMPOSER_TYPE.getReturnType();

            return new DynamicFuncNode<>(innerType.getParamTypes(),
                    innerType.getReturnType(),
                    FlexParamFunc.one(sc::getSprite, String.class));
            }, SpriteSheet.class);
    }
}
