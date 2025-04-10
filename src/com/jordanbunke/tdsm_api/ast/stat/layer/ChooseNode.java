package com.jordanbunke.tdsm_api.ast.stat.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.delta_time.utility.math.Pair;
import com.jordanbunke.tdsm.data.layer.ChoosingLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm_api.TDSMInterpreter;
import com.jordanbunke.tdsm_api.util.UpdateChecker;

public final class ChooseNode extends LayerStatNode {
    public static final String NAME = "choose";

    public ChooseNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeUtils.options(
                TypeNode.getString(), TypeNode.getInt()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);
        final ExpressionNode arg = arguments.get(0);
        final TextPosition argPos = arg.getPosition();
        final Object v = arguments.evaluate(symbolTable)[0];

        final Pair<String, TextPosition> failCode;

        if (layer instanceof ChoosingLayer cl) {
            if (v instanceof Integer i)
                failCode = execute(cl, i, argPos);
            else if (v instanceof String id)
                failCode = execute(cl, id, argPos);
            else
                failCode = failArgType(arg, arg.getType(symbolTable));
        } else
            failCode = new Pair<>("The layer '" + receiver.receiver() +
                    "' is not an asset choice layer or a choice layer",
                    getPosition());

        if (failCode == null)
            UpdateChecker.get().ping(layer);
        else
            TDSMInterpreter.failure(failCode.a(), failCode.b());

        return FuncControlFlow.cont();
    }

    private Pair<String, TextPosition> failArgType(
            final ExpressionNode arg, final TypeNode argType
    ) {
        return new Pair<>("The argument '" + arg + "' is of type '" +
                argType + "'; expected '" + TypeNode.getInt() + "' or '" +
                TypeNode.getString() + "'", arg.getPosition());
    }

    private Pair<String, TextPosition> execute(
            final ChoosingLayer cl, final int index,
            final TextPosition argPos
    ) {
        final int numChoices = cl.getNumChoices();

        if (index >= 0 && index < numChoices) {
            cl.chooseFromScript(index);
            return null;
        }

        return new Pair<>("The layer '" + cl.getID() + "' has " + numChoices +
                " choices; the index " + index + " is invalid", argPos);
    }

    private Pair<String, TextPosition> execute(
            final ChoosingLayer cl, final String id,
            final TextPosition argPos
    ) {
        return cl.choose(id) ? null : new Pair<>("The layer '" + cl.getID() +
                "' does not have a choice matching '" + id + "'", argPos);
    }
}
