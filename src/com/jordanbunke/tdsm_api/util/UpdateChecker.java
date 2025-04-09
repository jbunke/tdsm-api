package com.jordanbunke.tdsm_api.util;

import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm.data.style.Style;

import java.util.HashMap;
import java.util.Map;

public final class UpdateChecker {
    private static final UpdateChecker INSTANCE;

    static {
        INSTANCE = new UpdateChecker();
    }

    private final Map<Style, Boolean> updateMap;
    private final Map<CustomizationLayer, Style> layerMap;
    private final Map<ColorSelection, CustomizationLayer> csMap;

    private UpdateChecker() {
        updateMap = new HashMap<>();
        layerMap = new HashMap<>();
        csMap = new HashMap<>();
    }

    public static UpdateChecker get() {
        return INSTANCE;
    }

    public void link(final CustomizationLayer l, final Style s) {
        layerMap.put(l, s);
    }

    public void link(final ColorSelection cs, final CustomizationLayer l) {
        csMap.put(cs, l);
    }

    private void ping() {
        updateMap.replaceAll((s, v) -> true);
    }

    public void ping(final CustomizationLayer l) {
        if (layerMap.containsKey(l))
            ping(layerMap.get(l));
        else
            ping();
    }

    public void ping(final ColorSelection cs) {
        if (csMap.containsKey(cs))
            ping(csMap.get(cs));
        else
            ping();
    }

    private void ping(final Style s) {
        updateMap.put(s, true);
    }

    private void reset(final Style s) {
        updateMap.put(s, false);
    }

    private boolean is(final Style s) {
        return updateMap.getOrDefault(s, true);
    }

    public void check(final Style s) {
        if (is(s)) {
            s.update();
            reset(s);
        }
    }
}
