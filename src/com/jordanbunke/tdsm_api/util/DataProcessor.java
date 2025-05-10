package com.jordanbunke.tdsm_api.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.sprite.SpriteStates;
import com.jordanbunke.delta_time.sprite.constituents.InterpretedSpriteSheet;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.tdsm.data.Animation;
import com.jordanbunke.tdsm.data.Directions;
import com.jordanbunke.tdsm.data.func.Composer;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.ast.type.ReplacementTypeNode;
import com.jordanbunke.tdsm_api.ast.type.SheetTypeNode;

import java.util.Arrays;
import java.util.Set;

public final class DataProcessor {
    public static final FuncTypeNode COMPOSER_TYPE =
            new FuncTypeNode(new TypeNode[] { SheetTypeNode.get() },
                    new FuncTypeNode(new TypeNode[] { TypeNode.getString() },
                            TypeNode.getImage())),
            REPLACE_FUNC_TYPE =
                    new FuncTypeNode(new TypeNode[] { TypeNode.getColor() },
                            ReplacementTypeNode.get());

    public static Composer defaultComposer(
            final Directions directions, final Animation[] anims
    ) {
        final Coord2D FAIL = new Coord2D();

        return sheet -> new InterpretedSpriteSheet<>(sheet, id -> {
            final Directions.Dir dir = Directions.get(
                    SpriteStates.extractContributor(Style.DIRECTION, id));
            final String animID =
                    SpriteStates.extractContributor(Style.ANIM, id);
            final int frame = Integer.parseInt(
                    SpriteStates.extractContributor(Style.FRAME, id));

            final int dirIndex = indexOfDir(directions, dir);
            final Animation anim = animFromID(anims, animID);

            if (anim == null)
                return FAIL;

            if (directions.orientation())
                return anim.coordFunc.apply(frame).displace(0, dirIndex);
            else
                return anim.coordFunc.apply(frame).displace(dirIndex, 0);
        });
    }

    public static Directions extractDirections(
            final boolean orientation,
            final String[] dirStrings, final TextPosition argPos
    ) {
        final int amount = dirStrings.length;

        final Directions.NumDirs numDirs =
                Arrays.stream(Directions.NumDirs.values())
                        .filter(nd -> Integer.parseInt(
                                nd.toString()) == amount)
                        .findFirst().orElse(null);

        if (numDirs == null) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    argPos, "Passed " + amount +
                            " directions to the style initializer, which is an invalid amount.");
            return null;
        }

        final Set<Directions.Dir> included = numDirs.getIncluded();
        final Directions.Dir[] order = Arrays.stream(dirStrings)
                .map(Directions::get).filter(included::contains)
                .peek(included::remove).toArray(Directions.Dir[]::new);

        if (order.length != amount) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    argPos, (amount - order.length) +
                            " directions provided were either invalid for a " +
                            numDirs + "-directional sprite style or were duplicates");
            return null;
        }

        return new Directions(numDirs, orientation, order);
    }

    private static int indexOfDir(
            final Directions directions, final Directions.Dir target
    ) {
        for (int i = 0; i < directions.order().length; i++)
            if (target == directions.order()[i])
                return i;

        return -1;
    }

    private static Animation animFromID(
            final Animation[] animations, final String targetAnimID
    ) {
        for (Animation a : animations)
            if (a.id.equals(targetAnimID))
                return a;

        return null;
    }
}
