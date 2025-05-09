package com.jordanbunke.tdsm_api.ast.expr.sheet;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.sprite.SpriteSheet;

public final class SheetIntPropNode extends SheetPropNode {
    public static final String
            SPRITES_X = "sprites_x", SPRITES_Y = "sprites_y",
            SPRITE_WIDTH = "sprite_width", SPRITE_HEIGHT = "sprite_height";

    private final String code;

    public SheetIntPropNode(
            final TextPosition pos, final ExpressionNode scope,
            final String code
    ) {
        super(pos, scope, TypeNode.getInt());

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        final SpriteSheet sheet = getSheet(symbolTable);

        return switch (code) {
            case SPRITES_X -> sheet.spritesX;
            case SPRITES_Y -> sheet.spritesY;
            case SPRITE_WIDTH -> sheet.singleSpriteWidth;
            case SPRITE_HEIGHT -> sheet.singleSpriteHeight;
            default -> null;
        };
    }
}
