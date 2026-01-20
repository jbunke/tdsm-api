package com.jordanbunke.tdsm_api.ast.expr.sheet;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.sprite.SpriteSheet;
import com.jordanbunke.delta_time.utility.math.Coord2D;

public final class SheetSpriteAtNode extends SheetExprNode {
    public static final String NAME = "sprite_at";

    public SheetSpriteAtNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TypeNode.getImage(), args,
                TypeNode.getInt(), TypeNode.getInt());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final SpriteSheet sheet = getSheet(symbolTable);

        final Object[] vs = arguments.evaluate(symbolTable);

        final int x = (int) vs[0], y = (int) vs[1];

        if (x < 0 || x >= sheet.spritesX) {
            ScriptErrorLog.runtimeError(arguments.get(0).getPosition(),
                    "X coordinate is out of bounds; should be 0 <= x < sprites_x");
            return null;
        } else if (y < 0 || y >= sheet.spritesY) {
            ScriptErrorLog.runtimeError(arguments.get(1).getPosition(),
                    "Y coordinate is out of bounds; should be 0 <= y < sprites_y");
            return null;
        }

        return sheet.getSprite(new Coord2D(x, y));
    }
}
