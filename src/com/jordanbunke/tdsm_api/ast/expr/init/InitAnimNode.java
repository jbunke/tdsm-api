package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.ChildFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.tdsm.data.Animation;
import com.jordanbunke.tdsm.data.func.CoordFunc;
import com.jordanbunke.tdsm_api.ast.type.AnimTypeNode;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

import java.util.Arrays;

public final class InitAnimNode extends InitExprNode {
    public static final String NAME = "anim";

    public InitAnimNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, AnimTypeNode.get(), args,
                TypeNode.getString(), TypeNode.arrayOf(TypeNode.getInt()),
                new FuncTypeNode(new TypeNode[] { TypeNode.getInt() },
                        TypeNode.arrayOf(TypeNode.getInt())),
                TypeNode.getBool());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Animation evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String id = (String) vs[0];
        final int[] ticksPerFrame = ((ScriptArray) vs[1]).stream()
                .mapToInt(o -> (Integer) o).toArray();
        final CoordFunc coordFunc = getCoordFunc(
                (ChildFuncNode) vs[2], symbolTable);
        final boolean isPong = (boolean) vs[3];

        final int frameCount = ticksPerFrame.length;

        if (id.isEmpty()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(0).getPosition(),
                    "Animation must have a non-empty id");
            return null;
        } else if (frameCount == 0) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(1).getPosition(),
                    "Animation must have at least 1 frame");
            return null;
        } else if (Arrays.stream(ticksPerFrame).filter(i -> i <= 0)
                .findAny().isPresent()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(1).getPosition(),
                    "All animation frames must last for at least 1 tick");
            return null;
        }

        return Animation.init(id, frameCount)
                .setCoordFunc(coordFunc)
                .setPlaybackMode(isPong
                        ? Animation.PlaybackMode.PONG
                        : Animation.PlaybackMode.LOOP)
                .setTicksPerFrame(ticksPerFrame).build();
    }

    private CoordFunc getCoordFunc(
            final ChildFuncNode funcSource, final SymbolTable symbolTable
    ) {
        return i -> {
            final TextPosition pos = arguments.get(2).getPosition();
            final ScriptArray arr = MetaFuncHelper.evaluate(funcSource,
                    symbolTable, ScriptArray.class, pos, i);

            if (arr.size() != 2) {
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.CUSTOM_RT, pos,
                        "Frame coordinate function yielded an array of " +
                                arr.size() + " elements instead of 2");
                return null;
            } else {
                final Object xo = arr.get(0), yo = arr.get(1);

                if (!(xo instanceof Integer x && yo instanceof Integer y)) {
                    ScriptErrorLog.fireError(
                            ScriptErrorLog.Message.CUSTOM_RT, pos,
                            "Frame coordinate function should yield an int[]");
                    return null;
                } else if (x < 0) {
                    ScriptErrorLog.fireError(
                            ScriptErrorLog.Message.CUSTOM_RT, pos,
                            "The frame coordinate function yielded a negative X value");
                    return null;
                } else if (y < 0) {
                    ScriptErrorLog.fireError(
                            ScriptErrorLog.Message.CUSTOM_RT, pos,
                            "The frame coordinate function yielded a negative Y value");
                    return null;
                } else {
                    return new Coord2D(x, y);
                }
            }
        };
    }
}
