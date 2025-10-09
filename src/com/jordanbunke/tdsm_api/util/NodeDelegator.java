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
import com.jordanbunke.tdsm_api.ast.expr.color_proc.*;
import com.jordanbunke.tdsm_api.ast.expr.ext.*;
import com.jordanbunke.tdsm_api.ast.expr.init.*;
import com.jordanbunke.tdsm_api.ast.expr.layer.*;
import com.jordanbunke.tdsm_api.ast.expr.no_choice.*;
import com.jordanbunke.tdsm_api.ast.expr.replacement.*;
import com.jordanbunke.tdsm_api.ast.expr.script.*;
import com.jordanbunke.tdsm_api.ast.expr.sheet.*;
import com.jordanbunke.tdsm_api.ast.expr.style.*;
import com.jordanbunke.tdsm_api.ast.expr.util.*;
import com.jordanbunke.tdsm_api.ast.stat.global.*;
import com.jordanbunke.tdsm_api.ast.stat.col_sel.*;
import com.jordanbunke.tdsm_api.ast.stat.layer.*;
import com.jordanbunke.tdsm_api.ast.stat.multitype.RandomizeNode;
import com.jordanbunke.tdsm_api.ast.stat.script.*;
import com.jordanbunke.tdsm_api.ast.stat.style.*;
import com.jordanbunke.tdsm_api.ast.stat.util.*;
import com.jordanbunke.tdsm_api.ast.type.*;

public final class NodeDelegator {
    public static ExtTypeNode type(
            final TextPosition pos, final String typeID
    ) {
        final ExtTypeNode t = switch (typeID) {
            case AnimTypeNode.NAME -> new AnimTypeNode(pos);
            case AssetChoiceTypeNode.NAME -> new AssetChoiceTypeNode(pos);
            case ColSelTypeNode.NAME -> new ColSelTypeNode(pos);
            case LayerTypeNode.NAME -> new LayerTypeNode(pos);
            case NoChoiceTypeNode.NAME -> new NoChoiceTypeNode(pos);
            case ReplacementTypeNode.NAME -> new ReplacementTypeNode(pos);
            case ScriptTypeNode.NAME -> new ScriptTypeNode(pos);
            case SheetTypeNode.NAME -> new SheetTypeNode(pos);
            case StyleTypeNode.NAME -> new StyleTypeNode(pos);
            // extend here
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
                 LayerTypeConstNode.CHOICE_L, LayerTypeConstNode.DEPENDENT_L,
                 LayerTypeConstNode.OTHER_L ->
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
            case LoadFromJSONNode.NAME -> new LoadFromJSONNode(pos, args);
            case UploadStyleNode.NAME -> new UploadStyleNode(pos, args);
            // extend here
            default -> new IllegalStatementNode(pos,
                    "Undefined function \"" + formatGlobal(fID, true) + "\"");
        };
    }

    private static String formatGlobal(
            final String subident, final boolean function
    ) {
        return formatNamespace(Tokens.GLOBAL_NAMESPACE, subident, function);
    }

    public static ExpressionNode initConstant(
            final TextPosition pos, final String constID
    ) {
        return switch (constID) {
            // layer scope constants
            case LayerScopeConstNode.ASSEMBLY, LayerScopeConstNode.CUSTOM ->
                    new LayerScopeConstNode(pos, constID);
            // extend here
            default -> new IllegalExpressionNode(pos,
                    "No constant \"" + formatInit(constID, false) +
                            "\" exists");
        };
    }

    public static ExpressionNode initFExpr(
            final TextPosition pos, final String fID,
            final ExpressionNode... args
    ) {
        return switch (fID) {
            case InitAnimNode.NAME -> new InitAnimNode(pos, args);
            case InitAssetChoiceLayerNode.NAME ->
                    new InitAssetChoiceLayerNode(pos, args);
            case InitAssetChoiceNode.NAME ->
                    new InitAssetChoiceNode(pos, args);
            case InitAssetLayerNode.NAME -> new InitAssetLayerNode(pos, args);
            case InitChoiceLayerNode.NAME ->
                    new InitChoiceLayerNode(pos, args);
            case InitColSelNode.NAME -> new InitColSelNode(pos, args);
            case InitColSelLayerNode.NAME ->
                    new InitColSelLayerNode(pos, args);
            case InitComposedLayerNode.NAME ->
                    new InitComposedLayerNode(pos, args);
            case InitDecisionLayerNode.NAME ->
                    new InitDecisionLayerNode(pos, args);
            case DefaultComposerNode.NAME ->
                    new DefaultComposerNode(pos, args);
            case InitDependentLayerNode.NAME ->
                    new InitDependentLayerNode(pos, args);
            case InitGroupLayerNode.NAME ->
                    new InitGroupLayerNode(pos, args);
            case InitMaskLayerNode.NAME -> new InitMaskLayerNode(pos, args);
            case InitMathLayerNode.NAME -> new InitMathLayerNode(pos, args);
            case InitNoChoiceNoArgsNode.EQUAL,
                 InitNoChoiceNoArgsNode.INVALID ->
                    new InitNoChoiceNoArgsNode(pos, args, fID);
            case InitNoChoiceProbNode.NAME ->
                    new InitNoChoiceProbNode(pos, args);
            case InitReplacementNode.NAME ->
                    new InitReplacementNode(pos, args);
            case InitScriptNode.NAME -> new InitScriptNode(pos, args);
            case InitSheetNode.NAME -> new InitSheetNode(pos, args);
            case InitSimpleCoordFuncNode.NAME ->
                    new InitSimpleCoordFuncNode(pos, args);
            case InitStyleNode.NAME -> new InitStyleNode(pos, args);
            default -> new IllegalExpressionNode(pos,
                    "Undefined function \"" +
                            formatInit(fID, true) + "\"");
        };
    }

    private static String formatInit(
            final String subident, final boolean function
    ) {
        return formatNamespace(Tokens.INIT_NAMESPACE, subident, function);
    }

    public static ExpressionNode colorProcFExpr(
            final TextPosition pos, final String fID,
            final ExpressionNode... args
    ) {
        return switch (fID) {
            case AlphaMaskNode.NAME -> new AlphaMaskNode(pos, args);
            case ColorProcHSVNode.NAME -> args.length == 4
                    ? ColorProcHSVNode.withAlpha(pos, args)
                    : ColorProcHSVNode.justHSV(pos, args);
            case NormalizeHueNode.NAME -> new NormalizeHueNode(pos, args);
            default -> new IllegalExpressionNode(pos,
                    "Undefined function \"" +
                            formatNamespace(Tokens.COLOR_PROC_NAMESPACE,
                                    fID, true) + "\"");
        };
    }

    public static ExpressionNode utilFExpr(
            final TextPosition pos, final String fID,
            final ExpressionNode... args
    ) {
        return switch (fID) {
            case ExtractIDCompNode.EXTRACT_ANIM_ID ->
                    ExtractIDCompNode.animID(pos, args);
            case ExtractIDCompNode.EXTRACT_DIRECTION ->
                    ExtractIDCompNode.direction(pos, args);
            case ExtractIDCompNode.EXTRACT_FRAME ->
                    ExtractIDCompNode.frame(pos, args);
            // extend here
            default -> new IllegalExpressionNode(pos,
                    "Undefined function \"" +
                            formatNamespace(Tokens.UTIL_NAMESPACE,
                                    fID, true) + "\"");
        };
    }

    public static StatementNode utilFStat(
            final TextPosition pos, final String fID,
            final ExpressionNode... args
    ) {
        return switch (fID) {
            case SyncChoicesNode.NAME -> {
                if (args.length == 2)
                    yield SyncChoicesNode.pair(pos, args);

                yield SyncChoicesNode.array(pos, args);
            }
            // extend here
            default -> new IllegalStatementNode(pos,
                    "Undefined function \"" +
                            formatNamespace(Tokens.UTIL_NAMESPACE,
                                    fID, true) + "\"");
        };
    }

    private static String formatNamespace(
            final String namespace, final String subident, final boolean function
    ) {
        return "$" + namespace + "." +
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
            // color
            case ColorHSVPropNode.HUE, ColorHSVPropNode.SAT,
                 ColorHSVPropNode.VAL ->
                    new ColorHSVPropNode(pos, scope, propID);
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
            // replacement
            case ReplacementFuncNode.NAME ->
                    new ReplacementFuncNode(pos, scope);
            case ReplacementIndexNode.NAME ->
                    new ReplacementIndexNode(pos, scope);
            // sheet
            case SheetIntPropNode.SPRITES_X,
                 SheetIntPropNode.SPRITES_Y,
                 SheetIntPropNode.SPRITE_WIDTH,
                 SheetIntPropNode.SPRITE_HEIGHT ->
                    new SheetIntPropNode(pos, scope, propID);
            case SheetSourceNode.NAME -> new SheetSourceNode(pos, scope);
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
            case GetLayerNode.GET -> new GetLayerNode(pos, scope, args);
            case GetLayerNode.HAS -> GetLayerNode.has(pos, scope, args);
            // layer
            case NumChoicesNode.NAME -> new NumChoicesNode(pos, scope, args);
            case GetChoiceNode.NAME -> new GetChoiceNode(pos, scope, args);
            case GetChoiceAtNode.NAME ->
                    new GetChoiceAtNode(pos, scope, args);
            case GetChoiceIndexNode.NAME ->
                    new GetChoiceIndexNode(pos, scope, args);
            case NaiveMaskLogicNode.NAME ->
                    new NaiveMaskLogicNode(pos, scope, args);
            case LayerComposeNode.NAME ->
                    new LayerComposeNode(pos, scope, args);
            case GetColSelLayerNode.NAME ->
                    new GetColSelLayerNode(pos, scope, args);
            case GetNoChoiceNode.NAME -> new GetNoChoiceNode(pos, scope, args);
            case IsLockedNode.NAME -> new IsLockedNode(pos, scope, args);
            case GetValueNode.GET -> new GetValueNode(pos, scope, args);
            case GetValueNode.MAX -> GetValueNode.max(pos, scope, args);
            case GetValueNode.MIN -> GetValueNode.min(pos, scope, args);
            case IsNoneNode.NAME -> new IsNoneNode(pos, scope, args);
            case GetDecisionNode.NAME -> new GetDecisionNode(pos, scope, args);
            // col_sel
            case GetColorNode.NAME -> new GetColorNode(pos, scope, args);
            // anim
            case GetFrameCountNode.NAME ->
                    new GetFrameCountNode(pos, scope, args);
            // no_choice
            case NoChoiceProbNode.NAME ->
                    new NoChoiceProbNode(pos, scope, args);
            // script
            case RunExprNode.NAME -> new RunExprNode(pos, scope, args);
            // sheet
            case SheetSpriteAtNode.NAME ->
                    new SheetSpriteAtNode(pos, scope, args);
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
            case AddDependentNode.NAME ->
                    new AddDependentNode(pos, scope, args);
            case AddInfluencesNode.NAME ->
                    new AddInfluencesNode(pos, scope, args);
            // col_sel
            case SetColorNode.NAME -> new SetColorNode(pos, scope, args);
            case SetFromSwatchNode.NAME ->
                    new SetFromSwatchNode(pos, scope, args);
            // script
            case RunStatNode.NAME -> new RunStatNode(pos, scope, args);
            // extend here
            default -> new IllegalStatementNode(pos,
                    "No scoped function \"" + fID + "\" with " +
                            args.length + " arguments exists");
        };
    }
}
