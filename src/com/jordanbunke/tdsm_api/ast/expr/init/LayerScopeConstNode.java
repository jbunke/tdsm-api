package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class LayerScopeConstNode extends InitConstNode {
    public static final String ASSEMBLY = "ASSEMBLY", CUSTOM = "CUSTOM";

    public static final boolean CUSTOM_VALUE = true;

    private final String code;

    public LayerScopeConstNode(final TextPosition pos, final String code) {
        super(pos, TypeNode.getBool());

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        return CUSTOM.equals(code) == CUSTOM_VALUE;
    }
}
