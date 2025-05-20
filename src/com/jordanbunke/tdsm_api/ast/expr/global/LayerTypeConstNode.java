package com.jordanbunke.tdsm_api.ast.expr.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm_api.util.ScriptConstants;

public final class LayerTypeConstNode extends GlobalConstNode {
    public static final String
            ACL = "ACL", COL_SEL_L = "COL_SEL_L", DECISION_L = "DECISION_L",
            MATH_L = "MATH_L", CHOICE_L = "CHOICE_L",
            DEPENDENT_L = "DEPENDENT_L", OTHER_L = "OTHER_L";

    private final String code;

    public LayerTypeConstNode(final TextPosition pos, final String code) {
        super(pos, TypeNode.getInt());

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        return code.equals(OTHER_L)
                ? ScriptConstants.OTHER
                : ScriptConstants.LayerType.valueOf(code).ordinal();
    }
}
