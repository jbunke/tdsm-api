package com.jordanbunke.tdsm_api.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.IllegalExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.IllegalStatementNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Edge;
import com.jordanbunke.tdsm.util.EnumUtils;
import com.jordanbunke.tdsm_api.ast.expr.*;
import com.jordanbunke.tdsm_api.ast.expr.global.*;
import com.jordanbunke.tdsm_api.ast.expr.anim.*;
import com.jordanbunke.tdsm_api.ast.expr.col_sel.*;
import com.jordanbunke.tdsm_api.ast.expr.layer.*;
import com.jordanbunke.tdsm_api.ast.expr.no_choice.*;
import com.jordanbunke.tdsm_api.ast.expr.style.*;
import com.jordanbunke.tdsm_api.ast.stat.global.*;
import com.jordanbunke.tdsm_api.ast.stat.col_sel.*;
import com.jordanbunke.tdsm_api.ast.stat.layer.*;
import com.jordanbunke.tdsm_api.ast.stat.multitype.RandomizeNode;
import com.jordanbunke.tdsm_api.ast.stat.style.*;
import com.jordanbunke.tdsm_api.ast.type.*;

public final class NodeDelegator {
    public static ExtTypeNode type(
            final TextPosition pos, final String typeID
    ) {
        final ExtTypeNode t = switch (typeID) {
            case AnimTypeNode.NAME -> new AnimTypeNode(pos);
            case ColSelTypeNode.NAME -> new ColSelTypeNode(pos);
            case LayerTypeNode.NAME -> new LayerTypeNode(pos);
            case NoChoiceTypeNode.NAME -> new NoChoiceTypeNode(pos);
            case StyleTypeNode.NAME -> new StyleTypeNode(pos);
            default -> null;
        };

        if (t == null)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_CT,
                    pos, "Undefined type \"" + typeID + "\"");

        return t;
    }

    public static ExpressionNode globalConstant(
            final TextPosition pos, final String constID
    ) {
        // edge constants
        if (EnumUtils.matches(constID, Edge.class))
            return new EdgeConstNode(pos, Edge.valueOf(constID));

        return switch (constID) {
            // layer type constants
            case LayerTypeConstNode.ACL, LayerTypeConstNode.COL_SEL_L,
                 LayerTypeConstNode.DECISION_L, LayerTypeConstNode.MATH_L,
                 LayerTypeConstNode.CHOICE_L, LayerTypeConstNode.OTHER_L ->
                    new LayerTypeConstNode(pos, constID);
            // direction constants
            case DirConstNode.N, DirConstNode.W, DirConstNode.S,
                 DirConstNode.E, DirConstNode.NW, DirConstNode.NE,
                 DirConstNode.SW, DirConstNode.SE ->
                    new DirConstNode(pos, constID);
            // orientation constants
            case OrientationConstType.HORZ, OrientationConstType.VERT ->
                    new OrientationConstType(pos, constID);
            // coordinate constants
            case CoordConstNode.X, CoordConstNode.Y ->
                    new CoordConstNode(pos, constID);
            // extend here
            default -> new IllegalExpressionNode(pos,
                    "No constant \"" + formatGlobal(constID, false) +
                            "\" exists");
        };
    }

    public static ExpressionNode globalFExpr(
            final TextPosition pos, final String fID,
            final ExpressionNode... args
    ) {
        return switch (fID) {
            case GetStyleNode.NAME -> new GetStyleNode(pos, args);
            case IsExportFlagNode.JSON, IsExportFlagNode.STIP ->
                    new IsExportFlagNode(pos, args, fID);
            // extend here
            default -> new IllegalExpressionNode(pos,
                    "Undefined function \"" + formatGlobal(fID, true) + "\"");
        };
    }

    public static StatementNode globalFStat(
            final TextPosition pos, final String fID,
            final ExpressionNode... args
    ) {
        return switch (fID) {
            case SetExportFlagNode.JSON, SetExportFlagNode.STIP ->
                    new SetExportFlagNode(pos, args, fID);
            case ExportNode.NAME -> new ExportNode(pos, args);
            // extend here
            default -> new IllegalStatementNode(pos,
                    "Undefined function \"" + formatGlobal(fID, true) + "\"");
        };
    }

    private static String formatGlobal(
            final String subident, final boolean function
    ) {
        return "$" + Tokens.GLOBAL_NAMESPACE + "." +
                subident + (function ? "()" : "");
    }

    public static ExpressionNode property(
            final TextPosition pos, final ExpressionNode scope,
            final String propID
    ) {
        return switch (propID) {
            // multi-type
            case IDPropertyNode.NAME -> new IDPropertyNode(pos, scope);
            case NamePropertyNode.NAME -> new NamePropertyNode(pos, scope);
            // layer
            case LayerTypePropNode.NAME -> new LayerTypePropNode(pos, scope);
            // col_sel
            case ColSelAnyNode.NAME -> new ColSelAnyNode(pos, scope);
            case ColSelSwatchesNode.NAME -> new ColSelSwatchesNode(pos, scope);
            // no_choice
            case NoChoiceBoolPropNode.VALID ->
                    NoChoiceBoolPropNode.valid(pos, scope);
            case NoChoiceBoolPropNode.EQUAL ->
                    NoChoiceBoolPropNode.equal(pos, scope);
            // extend here
            default -> new IllegalExpressionNode(pos,
                    "No property \"" + propID + "\" exists");
        };
    }

    public static ExpressionNode scopedFExpr(
            final TextPosition pos, final ExpressionNode scope,
            final String fID, final ExpressionNode... args
    ) {
        return switch (fID) {
            // style
            case RenderNode.NAME -> new RenderNode(pos, scope, args);
            case GetAnimsNode.ALL -> GetAnimsNode.all(pos, scope, args);
            case GetAnimsNode.GET -> GetAnimsNode.get(pos, scope, args);
            case GetDirsNode.ALL -> GetDirsNode.all(pos, scope, args);
            case GetDirsNode.GET -> GetDirsNode.get(pos, scope, args);
            case GetLayersNode.ASSEMBLY ->
                    GetLayersNode.assembly(pos, scope, args);
            case GetLayersNode.CUSTOM ->
                    GetLayersNode.custom(pos, scope, args);
            case SpriteDimsNode.DEF_DIMS ->
                    SpriteDimsNode.defDims(pos, scope, args);
            case SpriteDimsNode.DIMS -> SpriteDimsNode.dims(pos, scope, args);
            case HasOutputNode.NAME -> new HasOutputNode(pos, scope, args);
            case GetEdgeNode.NAME -> new GetEdgeNode(pos, scope, args);
            case GetFramesPerDimNode.NAME ->
                    new GetFramesPerDimNode(pos, scope, args);
            case GetOrientationNode.NAME ->
                    new GetOrientationNode(pos, scope, args);
            case IsStyleFlagNode.SINGLE, IsStyleFlagNode.MULTIPLE,
                 IsStyleFlagNode.WRAP ->
                    new IsStyleFlagNode(pos, scope, args, fID);
            // layer
            case GetColSelLayerNode.NAME ->
                    new GetColSelLayerNode(pos, scope, args);
            case GetNoChoiceNode.NAME -> new GetNoChoiceNode(pos, scope, args);
            case IsLockedNode.NAME -> new IsLockedNode(pos, scope, args);
            case GetValueNode.GET -> new GetValueNode(pos, scope, args);
            case GetValueNode.MAX -> GetValueNode.max(pos, scope, args);
            case GetValueNode.MIN -> GetValueNode.min(pos, scope, args);
            case IsNoneNode.NAME -> new IsNoneNode(pos, scope, args);
            // col_sel
            case GetColorNode.NAME -> new GetColorNode(pos, scope, args);
            // anim
            case GetFrameCountNode.NAME ->
                    new GetFrameCountNode(pos, scope, args);
            // no_choice
            case NoChoiceProbNode.NAME ->
                    new NoChoiceProbNode(pos, scope, args);
            // extend here
            default -> new IllegalExpressionNode(pos,
                    "No scoped function \"" + fID + "\" with " +
                            args.length + " arguments exists");
        };
    }

    public static StatementNode scopedFStat(
            final TextPosition pos, final ExpressionNode scope,
            final String fID, final ExpressionNode... args
    ) {
        return switch (fID) {
            // multi-type
            case RandomizeNode.NAME -> new RandomizeNode(pos, scope, args);
            // style
            case SetAnimsNode.NAME -> new SetAnimsNode(pos, scope, args);
            case SetDirsNode.NAME -> new SetDirsNode(pos, scope, args);
            case ResetConfigNode.RESET_LAYOUT, ResetConfigNode.RESET_PADDING,
                 ResetConfigNode.RESET_SEQUENCING ->
                    new ResetConfigNode(pos, scope, args, fID);
            case SetEdgeNode.NAME -> new SetEdgeNode(pos, scope, args);
            case SetPaddingNode.NAME -> new SetPaddingNode(pos, scope, args);
            case SetFramesPerDimNode.NAME ->
                    new SetFramesPerDimNode(pos, scope, args);
            case SetOrientationNode.NAME ->
                    new SetOrientationNode(pos, scope, args);
            case SetStyleFlagNode.SINGLE, SetStyleFlagNode.MULTIPLE,
                 SetStyleFlagNode.WRAP ->
                    new SetStyleFlagNode(pos, scope, args, fID);
            // layer
            case LockLayerNode.LOCK -> LockLayerNode.lock(pos, scope, args);
            case LockLayerNode.UNLOCK ->
                    LockLayerNode.unlock(pos, scope, args);
            case ChooseNoneNode.NAME -> new ChooseNoneNode(pos, scope, args);
            case SetValueMLNode.NAME -> new SetValueMLNode(pos, scope, args);
            case ChooseNode.NAME -> new ChooseNode(pos, scope, args);
            // col_sel
            case SetColorNode.NAME -> new SetColorNode(pos, scope, args);
            // extend here
            default -> new IllegalStatementNode(pos,
                    "No scoped function \"" + fID + "\" with " +
                            args.length + " arguments exists");
        };
    }
}
