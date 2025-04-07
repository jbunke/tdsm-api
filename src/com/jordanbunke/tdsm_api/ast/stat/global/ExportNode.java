package com.jordanbunke.tdsm_api.ast.stat.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.PathHelper;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Sprite;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm.io.Export;
import com.jordanbunke.tdsm_api.TDSMInterpreter;
import com.jordanbunke.tdsm_api.ast.type.StyleTypeNode;

import java.nio.file.Path;

public final class ExportNode extends GlobalStatNode {
    public static final String NAME = "export";

    public ExportNode(final TextPosition pos, final ExpressionNode[] args) {
        super(pos, args, StyleTypeNode.get(),
                TypeNode.getString(), TypeNode.getString());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);
        final Style style = (Style) vs[0];
        final String folder = (String) vs[1],
                baseName = (String) vs[2];
        final Path fp = PathHelper.process(folder,
                symbolTable, arguments.get(1).getPosition());

        if (!Export.validFileName(baseName))
            TDSMInterpreter.failure(
                    "Could not export due to an invalid base name",
                    arguments.get(2).getPosition());
        else if (!style.exportsASprite())
            TDSMInterpreter.failure(
                    "Current configuration of sprite style \"" +
                            style.id + "\" does not export any frames",
                    arguments.get(0).getPosition());
        else {
            Sprite.get().setStyle(style);

            Export.get().setFolder(fp);
            Export.get().setFileName(baseName);

            Export.get().export();
        }

        return FuncControlFlow.cont();
    }
}
