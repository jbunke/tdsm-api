package com.jordanbunke.tdsm_api.ast.expr.color_proc;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.util.Colors;

public final class AlphaMaskNode extends ColorProcExprNode {
    public static final String NAME = "alpha_mask";

    public AlphaMaskNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, TypeNode.getImage(), args,
                TypeNode.getImage(), TypeNode.getImage());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final GameImage source = (GameImage) vs[0],
                mask = (GameImage) vs[1], copy = new GameImage(source);
        Colors.alphaMask(copy, mask);

        return copy;
    }
}
