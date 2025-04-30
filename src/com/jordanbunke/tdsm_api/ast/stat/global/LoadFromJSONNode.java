package com.jordanbunke.tdsm_api.ast.stat.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.json.JSONObject;
import com.jordanbunke.json.JSONPair;
import com.jordanbunke.json.JSONReader;
import com.jordanbunke.tdsm.data.layer.ChoiceLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.MathLayer;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm.data.style.Styles;
import com.jordanbunke.tdsm.util.EnumUtils;
import com.jordanbunke.tdsm_api.TDSMInterpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jordanbunke.tdsm.util.JSONHelper.*;

public final class LoadFromJSONNode extends GlobalStatNode {
    public static final String NAME = "load_from_json";

    public LoadFromJSONNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, args, TypeNode.getString());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final String contents = (String) arguments.get(0).evaluate(symbolTable);

        final JSONPair[] pairs = JSONReader.readObject(contents);
        Style style = null;

        if (pairs == null)
            TDSMInterpreter.failure(
                    "Argument could not be parsed as JSON",
                    arguments.get(0).getPosition());
        else {
            for (JSONPair pair : pairs) {
                switch (pair.key()) {
                    case STYLE_ID -> {
                        final String value = String.valueOf(pair.value());
                        style = EnumUtils.stream(Styles.class).map(Styles::get)
                                .filter(s -> s.id.equals(value))
                                .findFirst().orElse(null);
                    }
                    case CUSTOMIZATION -> {
                        if (style != null && pair.value() instanceof JSONObject o) {
                            final JSONPair[] layerPairs = o.get();
                            final List<CustomizationLayer> layers =
                                    style.layers.customization();

                            setLayersFromJSON(layerPairs,
                                    layerIDMap(layers.toArray(
                                            CustomizationLayer[]::new)));
                            style.update();
                        }
                    }
                }
            }
        }

        return FuncControlFlow.cont();
    }

    private void setLayersFromJSON(
            final JSONPair[] layerPairs,
            final Map<String, CustomizationLayer> layerIDMap
    ) {
        for (JSONPair layerPair : layerPairs) {
            final String id = layerPair.key();
            final Object value = layerPair.value();

            if (!layerIDMap.containsKey(id)) continue;

            final CustomizationLayer layer = layerIDMap.get(id);

            if (layer instanceof MathLayer ml && value instanceof Integer i)
                ml.setValue(i);
            else if (layer instanceof ChoiceLayer cl &&
                    value instanceof String s) {
                if (!cl.choose(s))
                    TDSMInterpreter.failure("\"" + s +
                            "\" isn't a valid choice for layer \"" + id +
                            "\"", TextPosition.N_A);
            }
            // TODO - other layer types
        }
    }

    private Map<String, CustomizationLayer> layerIDMap(
            final CustomizationLayer... layers
    ) {
        final Map<String, CustomizationLayer> layerMap = new HashMap<>();

        for (CustomizationLayer layer : layers)
            layerMap.put(layer.id, layer);

        return layerMap;
    }
}
