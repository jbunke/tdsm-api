package com.jordanbunke.tdsm_api.ast.expr.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class DirConstNode extends ConstNode {
    public static final String N = "N", W = "W", S = "S", E = "E",
            NW = "NW", NE = "NE", SW = "SW", SE = "SE";

    private final String code;

    public DirConstNode(final TextPosition pos, final String code) {
        super(pos, TypeNode.getString());

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public String evaluate(final SymbolTable symbolTable) {
        return code;
    }
}
