package com.jordanbunke.tdsm_api.cli;

import com.jordanbunke.delta_time.scripting.ast.nodes.ASTNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class CLIScopeNode extends ASTNode {
    private static final CLIScopeNode INSTANCE;

    static {
        INSTANCE = new CLIScopeNode();
    }

    private CLIScopeNode() {
        super(TextPosition.N_A);
    }

    public static CLIScopeNode get() {
        return INSTANCE;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {}

    @Override
    public String toString() {
        return "CLI";
    }
}
