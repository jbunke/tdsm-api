package com.jordanbunke.tdsm_api.ast.stat.style;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptCollection;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Animation;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.ast.type.AnimTypeNode;

public final class SetAnimsNode extends StyleStatNode {
    public static final String NAME = "set_anims";

    public SetAnimsNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, new TypeNode[] {
                TypeNode.arrayOf(AnimTypeNode.get()),
                TypeNode.listOf(AnimTypeNode.get())
        });
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);

        final ScriptCollection arg =
                (ScriptCollection) arguments.evaluate(symbolTable)[0];
        final Animation[] anims = arg.stream()
                .map(a -> (Animation) a).toArray(Animation[]::new);

        style.setExportAnimations(anims);

        return FuncControlFlow.cont();
    }
}
