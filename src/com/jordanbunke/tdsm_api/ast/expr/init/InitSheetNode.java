package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.sprite.SpriteSheet;
import com.jordanbunke.tdsm_api.ast.type.SheetTypeNode;

public final class InitSheetNode extends InitExprNode {
    public static final String NAME = "sheet";

    public InitSheetNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, SheetTypeNode.get(), args, TypeNode.getImage(),
                TypeNode.getInt(), TypeNode.getInt());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public SpriteSheet evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final GameImage source = (GameImage) vs[0];
        final int spriteW = (int) vs[1], spriteH = (int) vs[2];

        if (spriteW <= 0) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(1).getPosition(),
                    "Sprite width must be a positive integer");
            return null;
        } else if (spriteH <= 0) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(2).getPosition(),
                    "Sprite height must be a positive integer");
            return null;
        } else if (source.getWidth() % spriteW != 0) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(1).getPosition(),
                    "Sprite width must divide the source image's width without remainder");
            return null;
        } else if (source.getHeight() % spriteH != 0) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(2).getPosition(),
                    "Sprite height must divide the source image's height without remainder");
            return null;
        }

        return new SpriteSheet(source, spriteW, spriteH);
    }
}
