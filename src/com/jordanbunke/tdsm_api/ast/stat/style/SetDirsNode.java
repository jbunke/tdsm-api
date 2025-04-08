package com.jordanbunke.tdsm_api.ast.stat.style;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptCollection;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Directions;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.util.DirConversion;

public final class SetDirsNode extends StyleStatNode {
    public static final String NAME = "set_dirs";

    public SetDirsNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, new TypeNode[] {
                TypeNode.arrayOf(TypeNode.getString()),
                TypeNode.listOf(TypeNode.getString())
        });
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);

        final ScriptCollection arg =
                (ScriptCollection) arguments.evaluate(symbolTable)[0];
        final Directions.Dir[] dirs = arg.stream()
                .map(d -> (String) d).map(DirConversion::to)
                .toArray(Directions.Dir[]::new);

        style.setExportDirections(dirs);

        return FuncControlFlow.cont();
    }
}
