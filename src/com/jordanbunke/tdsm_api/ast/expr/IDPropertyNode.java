package com.jordanbunke.tdsm_api.ast.expr;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.DefFuncCallNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.Receiver;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Animation;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.ast.type.AnimTypeNode;
import com.jordanbunke.tdsm_api.ast.type.AssetChoiceTypeNode;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.ast.type.StyleTypeNode;
import com.jordanbunke.tdsm_api.util.AssetChoiceConstruct;

public final class IDPropertyNode extends DefFuncCallNode {
    public static final String NAME = "id";

    private final Receiver receiver;

    public IDPropertyNode(
            final TextPosition pos, final ExpressionNode scope
    ) {
        super(Arguments.none(), TypeNode.getString(), pos);

        receiver = new Receiver(scope, new TypeNode[] {
                StyleTypeNode.get(), LayerTypeNode.get(),
                AnimTypeNode.get(), AssetChoiceTypeNode.get()
        });
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        super.semanticErrorCheck(symbolTable);
        receiver.semanticErrorCheck(symbolTable);
    }

    @Override
    public String evaluate(final SymbolTable symbolTable) {
        final TypeNode type = receiver.getType(symbolTable);

        /* Treat as exhaustive because this code only executes if
         * the script passed semantic check */

        if (type instanceof StyleTypeNode) {
            // style
            final Style style = (Style) receiver.evaluate(symbolTable);
            return style.id;
        } else if (type instanceof LayerTypeNode) {
            // layer
            final CustomizationLayer layer =
                    (CustomizationLayer) receiver.evaluate(symbolTable);
            return layer.id;
        } else if (type instanceof AssetChoiceTypeNode) {
            // layer
            final AssetChoiceConstruct assetChoice =
                    (AssetChoiceConstruct) receiver.evaluate(symbolTable);
            return assetChoice.id;
        } else {
            // anim
            final Animation anim = (Animation) receiver.evaluate(symbolTable);
            return anim.id;
        }
    }
}
