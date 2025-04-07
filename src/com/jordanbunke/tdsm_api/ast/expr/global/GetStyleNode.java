package com.jordanbunke.tdsm_api.ast.expr.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm.data.style.Styles;
import com.jordanbunke.tdsm.util.EnumUtils;
import com.jordanbunke.tdsm_api.ast.type.StyleTypeNode;

public final class GetStyleNode extends GlobalExprNode {
    public static final String NAME = "get_style";

    public GetStyleNode(final TextPosition pos, ExpressionNode... args) {
        super(pos, StyleTypeNode.get(), args, TypeNode.getString());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Style evaluate(final SymbolTable symbolTable) {
        final Style[] options = EnumUtils.stream(Styles.class)
                .map(Styles::get).toArray(Style[]::new);
        final ExpressionNode idExp = arguments.get(0);
        final String id = (String) idExp.evaluate(symbolTable);

        for (Style option : options)
            if (option.id.equals(id))
                return option;

        ScriptErrorLog.fireError(
                ScriptErrorLog.Message.CUSTOM_RT, idExp.getPosition(),
                "Could not identify a sprite style matching the id \"" +
                        id + "\"");

        return null;
    }
}
