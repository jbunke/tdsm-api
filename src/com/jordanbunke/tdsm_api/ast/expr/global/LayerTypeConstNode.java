package com.jordanbunke.tdsm_api.ast.expr.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class LayerTypeConstNode extends ConstNode {
    public static final String
            ACL = "ACL", COL_SEL_L = "COL_SEL_L", DECISION_L = "DECISION_L",
            MATH_L = "MATH_L", CHOICE_L = "CHOICE_L", OTHER_L = "OTHER_L";

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
        return switch (code) {
            case ACL -> 0;
            case COL_SEL_L -> 1;
            case DECISION_L -> 2;
            case MATH_L -> 3;
            case CHOICE_L -> 4;
            default -> -1;
        };
    }
}
