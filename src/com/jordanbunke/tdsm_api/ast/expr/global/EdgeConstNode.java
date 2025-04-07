package com.jordanbunke.tdsm_api.ast.expr.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Edge;

public final class EdgeConstNode extends ConstNode {
    private final Edge edge;

    public EdgeConstNode(TextPosition pos, final Edge edge) {
        super(pos, TypeNode.getInt());

        this.edge = edge;
    }

    @Override
    protected String funcName() {
        return edge.name();
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        return edge.ordinal();
    }
}
