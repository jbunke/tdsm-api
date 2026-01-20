package com.jordanbunke.tdsm_api.util;

import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.support.AssetChoice;
import com.jordanbunke.tdsm.data.layer.support.AssetChoiceTemplate;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;

public final class AssetChoiceConstruct {
    public final boolean realized;
    public final String id;
    private final AssetChoiceTemplate template;
    private final AssetChoice real;

    private AssetChoiceConstruct(
            final boolean realized,
            final AssetChoiceTemplate template, final AssetChoice real
    ) {
        this.realized = realized;
        this.template = template;
        this.real = real;

        id = realized ? real.id : template.id;
    }

    public static AssetChoiceConstruct real(final AssetChoice real) {
        return new AssetChoiceConstruct(true, null, real);
    }

    public static AssetChoiceConstruct template(
            final AssetChoiceTemplate template
    ) {
        return new AssetChoiceConstruct(false, template, null);
    }

    public void randomize() {
        if (!realized)
            return;

        real.randomize();
        UpdateChecker.get().ping(getLayer());
    }

    public ColorSelection[] getColorSelections() {
        return realized ? real.getColorSelections() : template.colorSelections;
    }

    public AssetChoiceTemplate getTemplate() {
        return template;
    }

    public CustomizationLayer getLayer() {
        return real.getLayer();
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof AssetChoiceConstruct that &&
                id.equals(that.id) && realized == that.realized &&
                ((realized && real.equals(that.real)) ||
                        (!realized && template.equals(that.template)));
    }

    @Override
    public String toString() {
        return "\"" + id + " (" + (realized ? "real" : "template") + ")\": " +
                (realized ? real : template);
    }
}
