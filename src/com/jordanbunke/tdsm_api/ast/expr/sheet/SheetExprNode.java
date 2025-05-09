package com.jordanbunke.tdsm_api.ast.expr.sheet;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.MemberFuncCallNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.delta_time.sprite.SpriteSheet;
import com.jordanbunke.tdsm_api.ast.type.SheetTypeNode;

public abstract class SheetExprNode extends MemberFuncCallNode {
    public SheetExprNode(
            final TextPosition pos, final ExpressionNode scope,
            final TypeNode returnType, final ExpressionNode[] args,
            final TypeNode... expectedArgs
    ) {
        super(pos, scope, SheetTypeNode.get(), returnType,
                args, TypeUtils.expectExact(expectedArgs));
    }

    protected final SpriteSheet getSheet(final SymbolTable symbolTable) {
        return (SpriteSheet) receiver.evaluate(symbolTable);
    }
}
