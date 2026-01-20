package com.jordanbunke.tdsm_api.ast.stat.multitype;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.TDSMInterpreter;
import com.jordanbunke.tdsm_api.ast.type.AssetChoiceTypeNode;
import com.jordanbunke.tdsm_api.ast.type.ColSelTypeNode;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.ast.type.StyleTypeNode;
import com.jordanbunke.tdsm_api.util.AssetChoiceConstruct;
import com.jordanbunke.tdsm_api.util.UpdateChecker;

public final class RandomizeNode extends GenericFStatNode {
    public static final String NAME = "randomize";

    public RandomizeNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, new TypeNode[] {
                ColSelTypeNode.get(), LayerTypeNode.get(),
                StyleTypeNode.get(), AssetChoiceTypeNode.get()
        }, new Arguments(args));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final TypeNode type = receiver.getType(symbolTable);
        final Object scopeVal = receiver.evaluate(symbolTable);

        if (type instanceof StyleTypeNode)
            ((Style) scopeVal).randomize();
        else if (type instanceof LayerTypeNode) {
            final CustomizationLayer layer = (CustomizationLayer) scopeVal;

            layer.randomize(false);
            UpdateChecker.get().ping(layer);
        } else if (type instanceof ColSelTypeNode) {
            final ColorSelection cs = (ColorSelection) scopeVal;

            cs.randomize(false);
            UpdateChecker.get().ping(cs);
        } else if (type instanceof AssetChoiceTypeNode) {
            final AssetChoiceConstruct acc = (AssetChoiceConstruct) scopeVal;

            if (!acc.realized)
                TDSMInterpreter.failure(
                        "Attempted to randomize a non-realized asset choice template",
                        getPosition());
            else {
                acc.randomize();
            }
        }

        return FuncControlFlow.cont();
    }
}
