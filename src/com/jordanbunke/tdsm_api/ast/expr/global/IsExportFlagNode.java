package com.jordanbunke.tdsm_api.ast.expr.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.io.Export;

public final class IsExportFlagNode extends GlobalExprNode {
    public static final String STIP = "is_stip", JSON = "is_json";

    private final String code;

    public IsExportFlagNode(
            final TextPosition pos, final ExpressionNode[] args,
            final String code
    ) {
        super(pos, TypeNode.getBool(), args);

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        return code.equals(JSON)
                ? Export.get().isExportJSON()
                : Export.get().isExportStip();
    }
}
