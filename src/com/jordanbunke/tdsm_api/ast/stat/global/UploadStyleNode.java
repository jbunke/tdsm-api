package com.jordanbunke.tdsm_api.ast.stat.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.PathHelper;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.style.Styles;

import java.nio.file.Path;

public final class UploadStyleNode extends GlobalStatNode {
    public static final String NAME = "upload_style";

    public UploadStyleNode(
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
        final ExpressionNode arg = arguments.get(0);
        final String pathString = String.valueOf(arg.evaluate(symbolTable));
        final Path path = PathHelper.process(pathString,
                symbolTable, arg.getPosition());

        Styles.uploadFromScript(path);

        return FuncControlFlow.cont();
    }
}
