[***< Contents***](./README.md)

# `$Init`

The `$Init` namespace contains constructor functions for initializing objects of various types.

## Constants

> **Note:**
>
> Constants are bound to primitive values of type `bool`, `int`, or `string`. These **can** be avoided and replaced where appropriate by the corresponding literal, but **should** be used to make scripts more **readable** and **maintainable**, in case constant values are reassigned in future updates.

### Layer scope constants

| Constant            |  Type  |  Value  | Represents                                                                        |
|:--------------------|:------:|:-------:|:----------------------------------------------------------------------------------|
| `$Init.CUSTOM`      | `bool` | `true`  | A sprite style's [customization layers](./theory/t_layer.md#customization-layers) |
| `$Init.ASSEMBLY`    | `bool` | `false` | A sprite style's [assembly layers](./theory/t_layer.md#assembly-layers)           |

> **Note:**
> 
> Layer scope constants are intended to be used to define the `layers` parameter of [`$Init::style`](#style). 

## Functions

### `anim`

```js 
$Init.anim(string id, int[] ticks_per_frame, (int -> int[]) frame_coord_func, bool is_pong) -> anim
```

**Returns** a newly defined animation.

**Parameters:**
* `id` - The animation ID. IDs should be unique among animations of a sprite style. IDs mustn't contain hyphens (`-`), as this will break the extraction functions (see [`$Util::extract_anim_id`](./util.md#extract_anim_id)). IDs are reformatted for display in the program's GUI. If the animation ID requires multiple words, use an underscore (`_`) to separate them.
* `ticks_per_frame` - An array of integers representing the elapsed time of each animation frame, in program ticks. The length of the array is the animation's frame count. No array index should contain a non-positive number.
* `frame_coord_func` - The frame to coordinate function. This function takes the 0-based animation frame number as input and should return a two-element array corresponding to the (X, Y) coordinate pair representing that animation frame in a source asset sprite sheet.
* `is_pong` - Whether the animation is a ping-pong/oscillating animation (`true`) or a looping animation (`false`).

**Terminates with error if:**
* `id == ""`
* `#|ticks_per_frame == 0`
* For any `int frame` in `ticks_per_frame`... `frame < 1`

### `asset_choice`

```js 
$Init.asset_choice(string id, (color -> replacement) replace_func, col_sel[] selections) -> asset_choice
```

**Returns** a newly defined asset choice.

**Parameters:**
* `id` - The asset choice ID. IDs should be unique among asset choices of an asset choice layer.
* `replace_func` - The color replacement function for the asset choice. This function takes as input the color at some arbitrary pixel of the asset source file and returns the color replacement ([`replacement`](./replacement.md)) instructions.
* `selections` - An array of the influencing color selections specific to this asset choice.

**Terminates with error if:**
* `id == ""`

### `asset_choice_layer`

```js 
$Init.asset_choice_layer(string id, int[] dims, (string -> image) asset_fetcher_func, asset_choice[] choices, no_choice nc, (sheet -> (string -> image)) composer, int[] preview_coord) -> layer
```

**Returns** a newly defined asset choice layer.

**Parameters:**
* `id` - The layer ID. 
* `dims` - A two-integer array representing the width and height, in pixels, of a single sprite/frame of the asset choice layer. The dimensions of some asset choice layer of a sprite style don't necessarily have to be the same as the dimensions of the sprite style itself (see [`$Init::style`](#style)).
* `asset_fetcher_func` - A function that takes as input the asset choice ID and returns the asset as an image. This function should define a process that successfully fetches every asset of the asset choice layer given the choice ID.
* `choices` - An array of asset choices comprising this asset choice layer.
* `nc` - The [no choice configuration (`no_choice`)](./no_choice.md) for this asset choice layer.
* `composer` - A function that takes as input a [sprite sheet (`sheet`)](./sheet.md) of this asset choice layer's contents and returns a function mapping a [sprite ID](./theory/t_sprite_id.md) to an image representing the layer contents for that particular sprite/frame. In many cases, the function [`$Init::default_composer`](#default_composer) can be used here.
* `preview_coord` - A two-integer array representing the (X, Y) coordinate pair to use as the top-left pixel anchor for preview pictures displayed by the program UI for each asset choice.

**Terminates with error if:**
* `id == ""`
* `#|dims != 2`
* `#|preview_coord != 2`
* `dims[$TDSM.X] < 1 || dims[$TDSM.Y] < 1`

### `asset_layer`

```js 
$Init.asset_layer(string id, int[] dims, image asset, (sheet -> (string -> image)) composer, (color -> replacement) replace_func) -> layer
```

**Returns** a newly defined asset layer.

**Parameters:**
* `id` - The layer ID.
* `dims` - A two-integer array representing the width and height, in pixels, of a single sprite/frame of the asset layer. The dimensions of some asset layer of a sprite style don't necessarily have to be the same as the dimensions of the sprite style itself (see [`$Init::style`](#style)).
* `asset` - The source asset for this layer as an image.
* `composer` - A function that takes as input a [sprite sheet (`sheet`)](./sheet.md) of this asset layer's contents and returns a function mapping a [sprite ID](./theory/t_sprite_id.md) to an image representing the layer contents for that particular sprite/frame. In many cases, the function [`$Init::default_composer`](#default_composer) can be used here.
* `replace_func` - The color replacement function for the asset layer. This function takes as input the color at some arbitrary pixel of the asset source file and returns the color replacement ([`replacement`](./replacement.md)) instructions.

**Terminates with error if:**
* `id == ""`
* `#|dims != 2`
* `dims[$TDSM.X] < 1 || dims[$TDSM.Y] < 1`

### `choice_layer`

```js 
$Init.choice_layer(string id, string[] choices) -> layer
```

**Returns** a newly defined choice layer.

**Parameters:**
* `id` - The layer ID.
* `choices` - An array of unique strings, representing the choices of the layer.

**Terminates with error if:**
* `id == ""`
* `#|choices == 0`

### `col_sel`

```js 
$Init.col_sel(string name, bool any_color, color[] swatches) -> col_sel
```

**Returns** a newly defined [color selection (`col_sel`)](./col_sel.md).

**Parameters:**
* `name` - The name of the color selection. This acts as a label shown in the UI if the color selection is part of a [color selection layer](./theory/t_layer.md#color-selection-layer) consisting of multiple color selections.
* `any_color` - Whether this color selection can be set to the value of any RGB color. If `false`, color assignment via the GUI is limited to the color selection's swatches. However, any color can still be assigned to the color selection programmatically (see [`col_sel::set_color`](./col_sel.md#set_color)).
* `swatches` - An array representing the preset color options associated with this color selection. If an empty array is provided, the color selection will be defined with the program's [default swatches](./theory/t_col_sel.md#default-swatches). Randomizing (see [`col_sel::randomize`](./col_sel.md#randomize)) this color selection will randomly assign a swatch color.

**Terminates with error if:**
* `name == ""`

### `col_sel_layer`

```js 
$Init.col_sel_layer(string id, col_sel[] selections) -> layer
```

**Returns** a newly defined color selection layer.

**Parameters:**
* `id` - The layer ID.
* `selections` - An array of one or more color selections comprising this layer. A color selection **can** be defined as a member of multiple color selection layers, though this should never be necessary.

**Terminates with error if:**
* `id == ""`
* `#|selections == 0`

### `composed_layer`

```js 
$Init.composed_layer(string id, (string -> image) logic) -> layer
```

**Returns** a newly defined composed layer.

**Parameters:**
* `id` - The layer ID.
* `logic` - A function that takes as input a [sprite ID](./theory/t_sprite_id.md) and returns the contents of this layer for that sprite ID as an image.

**Terminates with error if:**
* `id == ""`

### `decision_layer`

```js 
$Init.decision_layer(string id, (-> layer) logic) -> layer
```

**Returns** a newly defined decision layer.

**Parameters:**
* `id` - The layer ID.
* `logic` - A function with no input that returns another layer. The decision layer should not return itself, as this will lead to infinite recursion.

**Terminates with error if:**
* `id == ""`

### `default_composer`

```js 
$Init.default_composer(string[] directions, bool orientation, anim[] anims) -> (sheet -> (string -> image))
```

**Returns** the default composer as defined by a series of directions, animations, and an animation orientation. The result of this function can be passed as the argument to the `composer` parameter in [`$Init::asset_choice_layer`](#asset_choice_layer) and [`$Init::asset_layer`](#asset_layer).

**Parameters:**
* `directions` - An array of direction codes, in their source sprite sheet sequencing order.
* `orientation` - The `bool` value corresponding with the sprite sheet's animation sequencing orientation (see [Orientation constants](./global.md#orientation))
* `anims` - An array of animations, in their source sprite sheet sequencing order.

**Terminates with error if:**
* `!(#|directions == 4 || #|directions == 6 || #|directions == 8)`<sup>a</sup>
* `#|anims == 0` <!-- TODO - implementation -->

> **Note:**
>
> a - Beyond merely being an array of a valid length, `directions` must be some [permutation](https://en.wikipedia.org/wiki/Permutation) of a valid set of directions:
> * **4 directions:** `[ $TDSM.N, $TDSM.W, $TDSM.S, $TDSM.E ]`
> * **6 directions:** `[ $TDSM.N, $TDSM.NW, $TDSM.SW, $TDSM.S, $TDSM.SE, $TDSM.NE ]`
> * **8 directions:** `[ $TDSM.N, $TDSM.NW, $TDSM.W, $TDSM.SW, $TDSM.S, $TDSM.SE, $TDSM.E, $TDSM.NE ]`
>
> **Related material:**
> * [Direction constants](./global.md#directions)


### `dependent_layer`

```js 
$Init.dependent_layer(string id, (string -> image) asset_fetcher_func, layer reference_layer, int relative_index) -> layer
```

**Returns** a newly defined dependent layer.

**Parameters:**
* `id` - The layer ID.
* `asset_fetcher_func` - A function that takes as input the asset choice ID and returns the asset as an image. This function should define a process that successfully fetches every asset of the asset choice layer given the choice ID.
* `reference_layer` - The asset choice layer that this dependent layer depends on.
* `relative_index` - The index, relative to `reference_layer`, that determines the order in which dependent layers of `reference_layer` are rendered for the preview in the user interface. `reference_layer` has a render index of `0`; dependent layers rendered lower should have a **negative** `relative_index`, dependent layers rendered above should have a **positive** `relative_index`.

**Terminates with error if:**
* `id == ""`
* `reference_layer.type != $TDSM.ACL`
* `relative_index == 0`

### `group_layer`

```js 
$Init.group_layer(string id, layer[] members) -> layer
```

**Returns** a newly defined group layer.

> **Note:**
> 
> Group layers should only be used when multiple layers need to be treated as a single layer, usually as the output of a [decision layer](./theory/t_layer.md#decision-layer).

**Parameters:**
* `id` - The layer ID.
* `members` - An array of layers that constitute the group layer's members.

**Terminates with error if:**
* `id == ""`

### `mask_layer`

```js 
$Init.mask_layer(string id, layer[] targets, (string -> image) logic) -> layer
```

**Returns** a newly defined mask layer.

**Parameters:**
* `id` - The layer ID.
* `targets` - An array of layers to which the mask will be applied.
* `logic` - A function that takes as input the [sprite ID](./theory/t_sprite_id.md) and returns an image of mask data, where every non-transparent pixel in the mask will be erased from the render output of the layers in `targets`.

> **Note:**
> 
> If a mask is meant to target an asset choice layer or dependent layer, you may wish to use the target layer itself as the basis for `logic` (see [`layer::naive_mask_logic`](./layer.md#naive_mask_logic)).

**Terminates with error if:**
* `id == ""`

### `math_layer`

```js 
$Init.math_layer(string id, int min, int max, int default, (int -> string) format_func) -> layer
```

**Returns** a newly defined math layer.

**Parameters:**
* `id` - The layer ID.
* `min` - The minimum integer value that this layer can hold.
* `max` - The maximum integer value that this layer can hold.
* `default` - The initial value of the layer.
* `format_func` - A function that takes the layer's current integer value as input and returns a corresponding string representation to be displayed by the GUI. `format_func` should account for every possible integer value in the range `[min, max]`.

**Terminates with error if:**
* `id == ""`
* `min >= max` <!-- TODO - implementation -->
* `default < min || default > max` <!-- TODO - implementation -->

### `no_choice_equal`

```js 
$Init.no_choice_equal() -> no_choice
```

**Returns** a no choice configuration that assigns no choice an equal probability of being assigned by [`layer::randomize`](./layer.md#randomize) as any choice. Defines no choice as **valid** for some asset choice layer (see [`$Init::asset_choice_layer`](#asset_choice_layer)).

### `no_choice_invalid`

```js 
$Init.no_choice_invalid() -> no_choice
```

**Returns** a no choice configuration that defines no choice as invalid for some asset choice layer (see [`$Init::asset_choice_layer`](#asset_choice_layer)).

### `no_choice_prob`

```js 
$Init.no_choice_prob(float prob) -> no_choice
```

**Returns** a no choice configuration that assigns no choice a probability of `prob` of being assigned by [`layer::randomize`](./layer.md#randomize). Defines no choice as **valid** for some asset choice layer (see [`$Init::asset_choice_layer`](#asset_choice_layer)).

**Parameters:**
* `prob` - The probability that no choice is assigned by randomization, as a floating-point number between `0.0` and `1.0`.

### `replacement`

```js 
$Init.replacement(int index, (color -> color) func) -> replacement
```

**Returns** newly defined color replacement logic.

**Parameters:**
* `index` - The 0-based index of the layer's influencing color selections to fetch as input for `func`. Negative indices or indices otherwise out of range are not processed; such pixels retain their input color.
* `func` - A function that takes as input a color fetches from an asset choice or asset layer's influencing color selections, and returns another color. This can apply a shadow or highlight effect, or any other imaginable color transformation.

> **Note:**
> 
> *TDSM* performs color replacements internally on asset layers and asset choices:
> 
> * When performed on [***asset layers***](./theory/t_layer.md#asset-layer), the influencing color selections constitute the color selections added to the asset layer via [`layer::add_influences`](./layer.md#add_influences), in the order they were added.
> * When performed on [***asset choices***](./theory/t_asset_choice.md), the influencing color selections constitute the color selections defined as part of the asset choice (see [`$Init::asset_choice`](#asset_choice)), followed by the color selections added to the asset choice's parent asset choice layer via [`layer::add_influences`](./layer.md#add_influences).

### `sheet`

```js 
$Init.sheet(image source, int sprite_width, int sprite_height) -> sheet
```

**Returns** a newly defined sprite sheet.

**Parameters:**
* `source` - The source image.
* `sprite_width` - The width, in pixels, of a single sprite/frame of the sprite sheet.
* `sprite_height` - The height, in pixels, of a single sprite/frame of the sprite sheet.

**Terminates with error if:**
* `sprite_width < 1`
* `sprite_height < 1`
* `source.w % sprite_width != 0`
* `source.h % sprite_height != 0`

### `simple_frame_coord_func`

```js 
$Init.simple_frame_coord_func(int first_frame_x, int first_frame_y, bool orientation) -> (int -> int[])
```

**Returns** a function representing the coordinates of each frame of an animation in a sprite sheet. The function characterizes an animation that is represented as consecutive animation frames in a sprite sheet, either top to bottom or left to right.

**Parameters:**
* `first_frame_x` - The 0-based X coordinate of the first frame in the animation in the sprite sheet. This value represents frames, not pixels.
* `first_frame_y` - The 0-based Y coordinate of the first frame in the animation in the sprite sheet. This value represents frames, not pixels.
* `orientation` - The `bool` value corresponding with the sprite sheet's animation sequencing orientation (see [Orientation constants](./global.md#orientation))

### `style`

```js
$Init.style(string id, int[] dims, string[] directions, bool orientation, anim[] anims, {bool : layer<>} layers) -> style
```

**Returns** a newly defined sprite style.

**Parameters:**
* `id` - The sprite ID. Uploading a sprite style to the program either via the GUI (![](https://raw.githubusercontent.com/jbunke/tdsm/refs/heads/master/res/icons/add.png)) or programmatically (see [`$TDSM::upload_style`](./global.md#upload_style)) with the same ID as a style already loaded into TDSM will replace the existing style with the newly uploaded one.
* `dims` - A two-integer array representing the width and height, in pixels, of a single sprite/frame of a sprite sheet in this style. These are the **default dimensions** (see [`style::def_sprite_dims`](./style.md#def_sprite_dims)), and can be padded or cropped as needed.
* `directions` - An array of direction codes representing the valid directions defined by this sprite style.
* `orientation` - The `bool` value corresponding with the sprite sheet's animation sequencing orientation (see [Orientation constants](./global.md#orientation))
* `anims` - An array of all the animations defined by this sprite style.
* `layers` - A map with key-value pairs for the sprite style's [customization](./theory/t_layer.md#customization-layers) and [assembly](./theory/t_layer.md#assembly-layers) layers. Map values are defined as lists -- for assembly layers, the order is the bottom to top rendering order, while for customization layers, it is the order in which customization dropdowns are presented in the program interface.

**Terminates with error if:**
* `id == ""` <!-- TODO - implementation -->
* `#|dims != 2`
* `dims[$TDSM.X] < 1 || dims[$TDSM.Y] < 1`
* `dims[$TDSM.X] > 128 || dims[$TDSM.Y] > 128`
* `!(#|directions == 4 || #|directions == 6 || #|directions == 8)`<sup>a</sup>
* `#|anims == 0` <!-- TODO - implementation -->
* `!(layers.has($Init.CUSTOM) && layers.has($Init.ASSEMBLY))`

> **Note:**
> 
> a - Beyond merely being an array of a valid length, `directions` must be some [permutation](https://en.wikipedia.org/wiki/Permutation) of a valid set of directions:
> * **4 directions:** `[ $TDSM.N, $TDSM.W, $TDSM.S, $TDSM.E ]`
> * **6 directions:** `[ $TDSM.N, $TDSM.NW, $TDSM.SW, $TDSM.S, $TDSM.SE, $TDSM.NE ]`
> * **8 directions:** `[ $TDSM.N, $TDSM.NW, $TDSM.W, $TDSM.SW, $TDSM.S, $TDSM.SE, $TDSM.E, $TDSM.NE ]`
>
> **Related material:**
> * [Direction constants](./global.md#directions)
