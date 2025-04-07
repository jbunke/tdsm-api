package com.jordanbunke.tdsm_api.ast.expr.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class CoordConstNode extends ConstNode {
    public static final String X = "X", Y = "Y";

    private final String code;

    public CoordConstNode(final TextPosition pos, final String code) {
        super(pos, TypeNode.getInt());

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        return code.equals(X) ? 0 : 1;
    }
}
