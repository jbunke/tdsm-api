package com.jordanbunke.tdsm_api.ast.stat.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.io.Export;

public final class SetExportFlagNode extends GlobalStatNode {
    public static final String STIP = "set_stip", JSON = "set_json";

    private final String code;

    public SetExportFlagNode(
            final TextPosition pos, final ExpressionNode[] args,
            final String code
    ) {
        super(pos, args, TypeNode.getBool());

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final ExpressionNode flagExpr = arguments.get(0);
        final boolean flag = (Boolean) flagExpr.evaluate(symbolTable);

        if (code.equals(JSON))
            Export.get().setExportJSON(flag);
        else
            Export.get().setExportStip(flag);

        return FuncControlFlow.cont();
    }
}
