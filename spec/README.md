# *Top Down Sprite Maker* | API Specification

## Contents

The *Top Down Sprite Maker* scripting API is a **[domain-specific](https://en.wikipedia.org/wiki/Domain-specific_language) extension** of the [*DeltaScript* programming language](https://github.com/jbunke/deltascript). It is primarily used to write `manifest.tds` scripts that define [sprite styles](TODO), or in conjunction with the [command-line interface](TODO).

The API extends the *DeltaScript* base language by introducing the following **namespaces** and **types**:

### Namespaces

* [Global namespace (`$TDSM`)](./global.md)
* [`$ColorProc`](./color_proc.md)
* [`$Init`](./init.md)
* [`$Util`](./util.md)

### Types

* [`anim`](./anim.md)
* [`asset_choice`](./asset_choice.md)
* [`col_sel`](./col_sel.md)
* [`layer`](./layer.md)
* [`no_choice`](./no_choice.md)
* [`replacement`](./replacement.md)
* [`sheet`](./sheet.md)
* [`style`](./style.md)

---

### See Also

To see API changes from one *Top Down Sprite Maker* release version to another, please read the [Changelog](./changelog.md).

There is a series of [theory](./theory/README.md) pages, each of which explains a *TDSM* program concept in a way that should understand functions defined by the API specification.

For an explanation of this specification's format and conventions, consult the [Help](./theory/help.md) page.

For documentation covering the syntax and semantics of the *DeltaScript* base language, please consult its [language specification](https://github.com/jbunke/deltascript/blob/master/docs/lang-spec.md) and [standard library](https://github.com/jbunke/deltascript/blob/master/docs/std-lib.md).
