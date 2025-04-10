package com.jordanbunke.tdsm_api.ast.expr;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.DefFuncCallNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.Receiver;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.ast.type.ColSelTypeNode;
import com.jordanbunke.tdsm_api.ast.type.StyleTypeNode;

public final class NamePropertyNode extends DefFuncCallNode {
    public static final String NAME = "name";

    private final Receiver receiver;

    public NamePropertyNode(
            final TextPosition pos, final ExpressionNode scope
    ) {
        super(Arguments.none(), TypeNode.getString(), pos);

        receiver = new Receiver(scope, new TypeNode[] {
                ColSelTypeNode.get(), StyleTypeNode.get()
                // TODO - consider adding more
        });
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        super.semanticErrorCheck(symbolTable);
        receiver.semanticErrorCheck(symbolTable);
    }

    @Override
    public String evaluate(final SymbolTable symbolTable) {
        final TypeNode type = receiver.getType(symbolTable);

        /* Treat as exhaustive because this code only executes if
         * the script passed semantic check */

        if (type instanceof StyleTypeNode) {
            // style
            final Style style = (Style) receiver.evaluate(symbolTable);
            return style.name();
        } else {
            // anim
            final ColorSelection cs =
                    (ColorSelection) receiver.evaluate(symbolTable);
            return cs.name;
        }
    }
}
