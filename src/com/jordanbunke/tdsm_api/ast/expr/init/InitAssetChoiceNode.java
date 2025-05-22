package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.ChildFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Replacement;
import com.jordanbunke.tdsm.data.func.ColorReplacementFunc;
import com.jordanbunke.tdsm.data.layer.support.AssetChoiceTemplate;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm_api.ast.type.AssetChoiceTypeNode;
import com.jordanbunke.tdsm_api.ast.type.ColSelTypeNode;
import com.jordanbunke.tdsm_api.util.DataProcessor;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

public final class InitAssetChoiceNode extends InitExprNode {
    public static final String NAME = "asset_choice";

    public InitAssetChoiceNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, AssetChoiceTypeNode.get(), args,
                TypeNode.getString(), DataProcessor.REPLACE_FUNC_TYPE,
                TypeNode.arrayOf(ColSelTypeNode.get()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public AssetChoiceTemplate evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String id = (String) vs[0];
        final ChildFuncNode replaceSource = (ChildFuncNode) vs[1];
        final ColorSelection[] selections = ((ScriptArray) vs[2]).stream()
                .map(o -> (ColorSelection) o)
                .toArray(ColorSelection[]::new);

        if (id.isEmpty()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(0).getPosition(),
                    "Asset choice ID must be non-empty");
            return null;
        }

        final ColorReplacementFunc replaceFunc =
                color -> MetaFuncHelper.evaluate(replaceSource,
                        symbolTable, Replacement.class,
                        arguments.get(1).getPosition(), color);

        return new AssetChoiceTemplate(id, replaceFunc, selections);
    }
}
