package com.jordanbunke.tdsm_api.ast.stat.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.style.Style;

public final class SetStyleFlagNode extends StyleStatNode {
    public static final String WRAP = "set_wrap_anims_across_dims",
            MULTIPLE = "set_multiple_anims_per_dim",
            SINGLE = "set_all_anims_single_dim";

    private final String code;

    public SetStyleFlagNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final String code
    ) {
        super(pos, scope, args, TypeUtils.expectExact(TypeNode.getBool()));

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);
        final boolean flag = (boolean) arguments.evaluate(symbolTable)[0];

        switch (code) {
            case SINGLE -> style.setSingleDim(flag);
            case MULTIPLE -> style.setMultipleAnimsPerDim(flag);
            case WRAP -> style.setWrapAnimsAcrossDims(flag);
        }

        return FuncControlFlow.cont();
    }
}
