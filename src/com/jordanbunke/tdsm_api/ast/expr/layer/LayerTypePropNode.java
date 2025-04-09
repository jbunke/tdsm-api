package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.*;
import com.jordanbunke.tdsm_api.util.ScriptConstants;

import static com.jordanbunke.tdsm_api.util.ScriptConstants.LayerType.*;

public final class LayerTypePropNode extends LayerPropNode {
    public static final String NAME = "type";

    public LayerTypePropNode(
            final TextPosition pos, final ExpressionNode scope
    ) {
        super(pos, scope, TypeNode.getInt());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        if (layer instanceof AssetChoiceLayer)
            return ACL.ordinal();
        else if (layer instanceof ColorSelectionLayer)
            return COL_SEL_L.ordinal();
        else if (layer instanceof DecisionLayer)
            return DECISION_L.ordinal();
        else if (layer instanceof MathLayer)
            return MATH_L.ordinal();
        else if (layer instanceof ChoiceLayer)
            return CHOICE_L.ordinal();

        return ScriptConstants.OTHER;
    }
}
