package com.jordanbunke.tdsm_api.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.IllegalExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.IllegalStatementNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Edge;
import com.jordanbunke.tdsm.util.EnumUtils;
import com.jordanbunke.tdsm_api.ast.expr.IDPropertyNode;
import com.jordanbunke.tdsm_api.ast.expr.global.*;
import com.jordanbunke.tdsm_api.ast.expr.style.GetAnimsNode;
import com.jordanbunke.tdsm_api.ast.expr.style.GetDirsNode;
import com.jordanbunke.tdsm_api.ast.expr.style.RenderNode;
import com.jordanbunke.tdsm_api.ast.stat.global.*;
import com.jordanbunke.tdsm_api.ast.stat.multitype.RandomizeNode;
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
            case IDPropertyNode.NAME -> new IDPropertyNode(pos, scope);
            // TODO - extend here
            default -> new IllegalExpressionNode(pos,
                    "No property \"" + propID + "\" exists");
        };
    }

    public static ExpressionNode scopedFExpr(
            final TextPosition pos, final ExpressionNode scope,
            final String fID, final ExpressionNode... args
    ) {
        return switch (fID) {
            case RenderNode.NAME -> new RenderNode(pos, scope, args);
            case GetAnimsNode.ALL -> GetAnimsNode.all(pos, scope, args);
            case GetAnimsNode.GET -> GetAnimsNode.get(pos, scope, args);
            case GetDirsNode.ALL -> GetDirsNode.all(pos, scope, args);
            case GetDirsNode.GET -> GetDirsNode.get(pos, scope, args);
            // TODO - extend here
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
            case RandomizeNode.NAME -> new RandomizeNode(pos, scope, args);
            // TODO - extend here
            default -> new IllegalStatementNode(pos,
                    "No scoped function \"" + fID + "\" with " +
                            args.length + " arguments exists");
        };
    }
}
