package com.jordanbunke.tdsm_api.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.function.*;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncHelper;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeCompatibility;

import static com.jordanbunke.delta_time.scripting.util.ScriptErrorLog.*;

import java.util.Objects;

public final class MetaFuncHelper {
    public static Object evaluate(
            final ChildFuncNode func,
            final SymbolTable symbolTable, final Object[] args
    ) {
        final SymbolTable funcTable =
                FuncHelper.getScopeTable(func, symbolTable);

        return func.execute(funcTable, args);
    }

    public static <T> T evaluate(
            final ChildFuncNode func, final SymbolTable symbolTable,
            final Class<T> c, final TextPosition argPos, final Object... args
    ) {
        return asClass(c, evaluate(func, symbolTable, args), argPos);
    }

    public static <T> T asClass(
            final Class<T> c, final Object obj, final TextPosition argPos
    ) {
        if (c.isInstance(obj))
            return c.cast(obj);

        final String expectedType = TypeCompatibility.resolveTypeName(c),
                actualType = obj == null ? "null" :
                        TypeCompatibility.resolveTypeName(obj.getClass());

        runtimeError(argPos, "Incompatible types: " +
                expectedButGot(expectedType, actualType));
        return null;
    }

    public static boolean validate(
            final FuncNode func, final TypeNode returnType,
            final TypeNode... paramSpec
    ) {
        if (func == null) return false;

        return func.paramsMatch(paramSpec) &&
                Objects.equals(returnType, func.getReturnType());
    }
}
