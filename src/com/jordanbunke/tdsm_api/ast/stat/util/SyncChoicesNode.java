package com.jordanbunke.tdsm_api.ast.stat.util;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.AssetChoiceLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm_api.TDSMInterpreter;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;

public final class SyncChoicesNode extends UtilStatNode {
    public static final String NAME = "sync_choices";

    private final boolean array;

    private SyncChoicesNode(
            final TextPosition pos, final boolean array,
            final ExpressionNode[] args, final TypeNode... expectedTypes
    ) {
        super(pos, args, expectedTypes);

        this.array = array;
    }

    public static SyncChoicesNode array(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        return new SyncChoicesNode(pos, true, args,
                TypeNode.arrayOf(LayerTypeNode.get()));
    }

    public static SyncChoicesNode pair(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        return new SyncChoicesNode(pos, false, args,
                LayerTypeNode.get(), LayerTypeNode.get());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        if (array)
            executeArray(symbolTable);
        else
            executePair(symbolTable);

        return FuncControlFlow.cont();
    }

    private void executeArray(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final CustomizationLayer[] members = ((ScriptArray) vs[0]).stream()
                .map(o -> (CustomizationLayer) o)
                .toArray(CustomizationLayer[]::new);

        if (members.length <= 1)
            return;

        for (int i = 0; i < members.length; i++) {
            for (int j = 1; j < members.length; j++) { // TODO - should this be int j = i + 1?
                if (i == j)
                    continue;

                final CustomizationLayer a = members[i], b = members[j];

                if (!a.equals(b))
                    syncACLs(a, b);
            }
        }
    }

    private void executePair(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final CustomizationLayer a = (CustomizationLayer) vs[0],
                b = (CustomizationLayer) vs[1];
        syncACLs(a, b);
    }

    private void syncACLs(
            final CustomizationLayer a, final CustomizationLayer b
    ) {
        if (a instanceof AssetChoiceLayer aclA &&
                b instanceof AssetChoiceLayer aclB)
            AssetChoiceLayer.parallelMatchers(aclA, aclB);
        else
            TDSMInterpreter.failure(
                    "Couldn't sync asset choice layers",
                    "one or both layers provided wasn't an asset choice layer",
                    getPosition());
    }
}
