package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.DynamicFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FlexParamFunc;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.tdsm.data.func.CoordFunc;

public final class InitSimpleCoordFuncNode extends InitExprNode {
    public static final String NAME = "simple_frame_coord_func";

    private static final TypeNode[] PARAM_TYPES =
            new TypeNode[] { TypeNode.getInt() };
    private static final TypeNode RETURN_TYPE =
            TypeNode.arrayOf(TypeNode.getInt());

    public InitSimpleCoordFuncNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, new FuncTypeNode(PARAM_TYPES, RETURN_TYPE), args,
                TypeNode.getInt(), TypeNode.getInt(), TypeNode.getBool());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public DynamicFuncNode<ScriptArray> evaluate(
            final SymbolTable symbolTable
    ) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final int firstFrameX = (int) vs[0], firstFrameY = (int) vs[1];
        final boolean orientation = (boolean) vs[2];

        final Coord2D firstFrame = new Coord2D(firstFrameX, firstFrameY);
        final CoordFunc func = CoordFunc.simple(firstFrame, orientation);
        final FlexParamFunc<ScriptArray> scriptFunc = FlexParamFunc.one(
                i -> {
                    Coord2D c = func.apply(i);

                    final ScriptArray arr = new ScriptArray(2);
                    arr.set(0, c.x);
                    arr.set(1, c.y);

                    return arr;
                }, Integer.class);

        return new DynamicFuncNode<>(PARAM_TYPES, RETURN_TYPE, scriptFunc);
    }
}
