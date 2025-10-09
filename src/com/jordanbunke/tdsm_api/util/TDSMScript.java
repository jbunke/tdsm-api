package com.jordanbunke.tdsm_api.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;

import java.nio.file.Path;

public record TDSMScript(HeadFuncNode head, Path path) {
}
