package com.jordanbunke.tdsm_api.ast.expr.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.sprite.SpriteStates;
import com.jordanbunke.tdsm.data.Directions;
import com.jordanbunke.tdsm_api.util.DirConversion;

import static com.jordanbunke.tdsm.data.style.Style.*;

public final class ExtractIDCompNode extends UtilExprNode {
    public static final String EXTRACT_ANIM_ID = "extract_anim_id",
            EXTRACT_DIRECTION = "extract_direction",
            EXTRACT_FRAME = "extract_frame";

    private final String code;

    private ExtractIDCompNode(
            final TextPosition pos, final TypeNode returnType,
            final ExpressionNode[] args, final String code
    ) {
        super(pos, returnType, args, TypeNode.getString());

        this.code = code;
    }

    public static ExtractIDCompNode animID(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        return new ExtractIDCompNode(pos,
                TypeNode.getString(), args, EXTRACT_ANIM_ID);
    }

    public static ExtractIDCompNode direction(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        return new ExtractIDCompNode(pos,
                TypeNode.getString(), args, EXTRACT_DIRECTION);
    }

    public static ExtractIDCompNode frame(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        return new ExtractIDCompNode(pos,
                TypeNode.getInt(), args, EXTRACT_FRAME);
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final ExpressionNode arg = arguments.get(0);
        final String spriteID = String.valueOf(arg.evaluate(symbolTable));

        final int index = switch (code) {
            case EXTRACT_DIRECTION -> DIRECTION;
            case EXTRACT_ANIM_ID -> ANIM;
            default -> FRAME;
        };

        final String raw = SpriteStates.extractContributor(index, spriteID);

        return switch (code) {
            case EXTRACT_ANIM_ID -> raw;
            case EXTRACT_DIRECTION -> DirConversion.from(Directions.get(raw));
            default -> {
                try {
                    yield Integer.parseInt(raw);
                } catch (NumberFormatException nfe) {
                    ScriptErrorLog.fireError(
                            ScriptErrorLog.Message.CUSTOM_RT,
                            arg.getPosition(),
                            "Invalid format: frame number could not be parsed from sprite ID \"" +
                                    spriteID + "\"");
                    yield null;
                }
            }
        };
    }
}
