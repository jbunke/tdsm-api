package com.jordanbunke.tdsm_api;

import com.jordanbunke.anim.data.Animation;
import com.jordanbunke.delta_time.scripting.ScriptParser;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.IllegalExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.IllegalStatementNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.delta_time.scripting.util.ScriptVisitor;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeCompatibility;
import com.jordanbunke.delta_time.sprite.SpriteSheet;
import com.jordanbunke.tdsm.data.Replacement;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm.data.layer.support.NoAssetChoice;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.ast.type.*;
import com.jordanbunke.tdsm_api.util.AssetChoiceConstruct;
import com.jordanbunke.tdsm_api.util.NodeDelegator;
import com.jordanbunke.tdsm_api.util.TDSMScript;
import com.jordanbunke.tdsm_api.util.Tokens;

import java.util.HashMap;
import java.util.Map;

public final class TDSMVisitor extends ScriptVisitor {
    private static final String SCOPE_SEP = ".";

    /*
    * new types:
    * anim, asset_choice, col_sel, layer, no_choice,
    * replacement, script, sheet, style
    * */

    static {
        final Map<ExtTypeNode, Class<?>> extensionTypes = new HashMap<>();

        extensionTypes.put(AnimTypeNode.get(), Animation.class);
        extensionTypes.put(AssetChoiceTypeNode.get(), AssetChoiceConstruct.class);
        extensionTypes.put(ColSelTypeNode.get(), ColorSelection.class);
        extensionTypes.put(LayerTypeNode.get(), CustomizationLayer.class);
        extensionTypes.put(NoChoiceTypeNode.get(), NoAssetChoice.class);
        extensionTypes.put(ReplacementTypeNode.get(), Replacement.class);
        extensionTypes.put(ScriptTypeNode.get(), TDSMScript.class);
        extensionTypes.put(SheetTypeNode.get(), SpriteSheet.class);
        extensionTypes.put(StyleTypeNode.get(), Style.class);

        extensionTypes.keySet().forEach(k -> {
            final Class<?> v = extensionTypes.get(k);
            TypeCompatibility.addBaseType(k, v);
        });
    }

    @Override
    public ExtTypeNode visitExtensionType(
            final ScriptParser.ExtensionTypeContext ctx
    ) {
        return NodeDelegator.type(
                TextPosition.fromToken(ctx.start), ctx.ident().getText());
    }

    @Override
    public ExpressionNode visitExtPropertyExpression(
            final ScriptParser.ExtPropertyExpressionContext ctx
    ) {
        final String namespace = ctx.namespace().ident().getText(),
                constID = ctx.namespace().subident().getText()
                        .substring(SCOPE_SEP.length());

        final TextPosition position = TextPosition.fromToken(ctx.start);

        return switch (namespace) {
            case Tokens.GLOBAL_NAMESPACE ->
                    NodeDelegator.globalConstant(position, constID);
            case Tokens.INIT_NAMESPACE ->
                    NodeDelegator.initConstant(position, constID);
            default -> new IllegalExpressionNode(position,
                    "\"" + namespace + "\" is an illegal namespace");
        };
    }

    @Override
    public ExpressionNode visitExtFuncCallExpression(
            final ScriptParser.ExtFuncCallExpressionContext ctx
    ) {
        final String namespace = ctx.namespace().ident().getText(),
                fID = ctx.namespace().subident().getText()
                        .substring(SCOPE_SEP.length());

        final TextPosition position = TextPosition.fromToken(ctx.start);

        final ExpressionNode[] args = unpackElements(ctx.args().elements());

        return switch (namespace) {
            case Tokens.GLOBAL_NAMESPACE ->
                    NodeDelegator.globalFExpr(position, fID, args);
            case Tokens.COLOR_PROC_NAMESPACE ->
                    NodeDelegator.colorProcFExpr(position, fID, args);
            case Tokens.INIT_NAMESPACE ->
                    NodeDelegator.initFExpr(position, fID, args);
            case Tokens.UTIL_NAMESPACE ->
                    NodeDelegator.utilFExpr(position, fID, args);
            default -> new IllegalExpressionNode(position,
                    "Namespace \"" + namespace +
                            "\" does not exist or defines no value-returning functions");
        };
    }

    @Override
    public StatementNode visitExtFuncCallStatement(
            final ScriptParser.ExtFuncCallStatementContext ctx
    ) {
        final String namespace = ctx.namespace().ident().getText(),
                fID = ctx.namespace().subident().getText()
                        .substring(SCOPE_SEP.length());

        final TextPosition position = TextPosition.fromToken(ctx.start);

        final ExpressionNode[] args = unpackElements(ctx.args().elements());

        return switch (namespace) {
            case Tokens.GLOBAL_NAMESPACE ->
                    NodeDelegator.globalFStat(position, fID, args);
            case Tokens.UTIL_NAMESPACE ->
                    NodeDelegator.utilFStat(position, fID, args);
            default -> new IllegalStatementNode(position,
                    "Namespace \"" + namespace +
                            "\" does not exist or defines no void functions");
        };
    }

    @Override
    public ExpressionNode determineExtPropertyExpression(
            final TextPosition position, final ExpressionNode scope,
            final String propertyID
    ) {
        return NodeDelegator.property(position, scope, propertyID);
    }

    @Override
    public ExpressionNode determineExtScopedFunctionExpression(
            final TextPosition position, final ExpressionNode scope,
            final String functionID, final ExpressionNode... args
    ) {
        return NodeDelegator.scopedFExpr(position, scope, functionID, args);
    }

    @Override
    public StatementNode determineExtScopedFunctionStatement(
            final TextPosition position, final ExpressionNode scope,
            final String functionID, final ExpressionNode... args
    ) {
        return NodeDelegator.scopedFStat(position, scope, functionID, args);
    }
}
