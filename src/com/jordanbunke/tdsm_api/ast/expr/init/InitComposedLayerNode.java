package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.ChildFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.sprite.constituents.SpriteConstituent;
import com.jordanbunke.tdsm.data.layer.PureComposeLayer;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

public final class InitComposedLayerNode extends InitExprNode {
    public static final String NAME = "composed_layer";

    public InitComposedLayerNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, LayerTypeNode.get(), args, TypeNode.getString(),
                new FuncTypeNode(new TypeNode[] { TypeNode.getString() },
                        TypeNode.getImage()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String id = (String) vs[0];
        final ChildFuncNode logicSource = (ChildFuncNode) vs[1];

        final SpriteConstituent<String> logic = spriteID ->
                MetaFuncHelper.evaluate(logicSource, symbolTable,
                        GameImage.class, arguments.get(1).getPosition(),
                        spriteID);

        if (id.isEmpty()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(0).getPosition(),
                    "Layer ID must be non-empty");
            return null;
        }

        return new PureComposeLayer(id, logic);
    }
}
