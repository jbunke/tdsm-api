package com.jordanbunke.tdsm_api.ast.expr.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class OrientationConstType extends ConstNode {
    public static final String HORZ = "HORZ", VERT = "VERT";

    private final String code;

    public OrientationConstType(final TextPosition pos, final String code) {
        super(pos, TypeNode.getBool());

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        return code.equals(VERT);
    }
}
