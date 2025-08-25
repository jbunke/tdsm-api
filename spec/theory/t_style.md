[***< Theory***](./README.md)

# Sprite styles

> For the API type page, see [`style`](../style.md). For the constructor, see [`$Init::style`](../init.md#style).

**Sprite styles** are what most distinguishes *Top Down Sprite Maker* from other pixel art character creation / sprite sheet generation programs. They are *self-contained packages* that *define a character art style, set of animations, directions, and customization options*.

Sprite styles are distributed as ZIP file archives. Thus, they can be easily distributed and uploaded directly to *Top Down Sprite Maker*.

## `manifest.tds`

The instructions that define the sprite style sit in a script file in the root of the ZIP file archive that must be called `manifest.tds`. *TDSM* reads this file to load the sprite style into the program. For information about the contents of `manifest.tds`, see the [scripting overview](./t_scripting.md#manifesttds).

## Scope

As mentioned in the introduction, a sprite style encompasses a few things...

### Dimensions

Sprite styles are defined with the default dimensions of a single sprite/frame. This can be anywhere from 1x1 to 128x128 pixels.

### Animations

Sprite styles define a set of [animations](./t_anim.md).

### Directions

Sprite styles define a set of supported [directions](./t_dir.md).

### Layers

Sprite styles define a series of [assembly layers](./t_layer.md#assembly-layers) and [customization layers](./t_layer.md#customization-layers). These determine what gets composed into a sprite by the paper doll system, and which choices the user has to customize a sprite, respectively.

## Finding sprite styles

By default, *Top Down Sprite Maker* includes a single "default" sprite style. More can be bought or downloaded for free online. Some sprite styles adapt or are inspired by established IPs, such as *Pokémon*. Such sprite styles will always be distributed for free.

Sprite styles made by the developer, or those that have been confirmed to be compatible with the program, can be found in [this Itch.io collection](https://itch.io/c/5834066/top-down-sprite-maker-approved-sprite-styles).

<!-- TODO - editing a sprite sheet section -->
<!-- TODO - embed tutorial video -->

<!-- TODO - making a sprite sheet section -->
<!-- TODO - embed tutorial video -->
