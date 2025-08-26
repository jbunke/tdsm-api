# *Top Down Sprite Maker* | Scripting API

This repository houses the [specification](./spec/README.md) and [implementation](./src/com/jordanbunke/tdsm_api) of the [*application programing interface*](https://en.wikipedia.org/wiki/API) (API) for scripts in [*Top Down Sprite Maker*](https://github.com/jbunke/tdsm), a pixel art character customization program that supports multiple art styles and fully configurable sprite sheets.

## *DeltaScript*

*Top Down Sprite Maker* uses ***DeltaScript*** as its scripting language. The language was created by the same developer as *TDSM*, and was designed to be easy to read and write and limited in scope to particular application domains.

* [Scripting overview / *DeltaScript* crash course](./spec/theory/t_scripting.md)
* [*DeltaScript* repository + language specification](https://github.com/jbunke/deltascript)

## Uses

### Sprite styles

The primary use of the API is to define sprite styles. You can read more about sprite styles on the [dedicated sprite style page](./spec/theory/t_style.md), or about defining them in the [`manifest.tds` section](./spec/theory/t_scripting.md#manifesttds) of the scripting overview.

<!-- TODO - embed tutorial -->

### Command-line interface

COMING SOON <!-- TODO -->

## Getting started

If you are interested in creating your own sprite styles for *Top Down Sprite Maker*, you may want to start by...

<!-- TODO - watch tutorial(s) -->
<!-- TODO - downloading and following the sprite style development guide -->
* Reading the [API specification](./spec/README.md)
* Downloading [free sprite styles](https://itch.io/c/5834066/top-down-sprite-maker-approved-sprite-styles) and inspecting their `manifest.tds` files as examples
