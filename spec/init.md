[***< Contents***](./README.md)

# `$Init`

The `$Init` namespace contains constructor functions for initializing objects of various types.

## Functions

### `anim`

```js 
$Init.anim(string id, int[] ticks_per_frame, (int -> int[]) frame_coord_func, bool is_pong) -> anim
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` - 
* `ticks_per_frame` - 
* `frame_coord_func` - 
* `is_pong` - 

**Terminates with error if**:
* 

### `asset_choice`

```js 
$Init.asset_choice(string id, (color -> replacement) replace_func, col_sel[] selections) -> asset_choice
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `replace_func` -
* `selections` -

**Terminates with error if**:
*

### `asset_choice_layer`

```js 
$Init.asset_choice_layer(string id, int[] dims, (string -> image) asset_fetcher_func, asset_choice[] choices, no_choice nc, (sheet -> (string -> image)) composer, int[] preview_coord) -> layer
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `dims` -
* `asset_fetcher_func` -
* `choices` -
* `nc` -
* `composer` -
* `preview_coord` -

**Terminates with error if**:
*

### `asset_layer`

```js 
$Init.asset_layer(string id, int[] dims, image asset, (sheet -> (string -> image)) composer, (color -> replacement) replace_func) -> layer
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `dims` -
* `asset` -
* `composer` -
* `replace_func` -

**Terminates with error if**:
*

### `choice_layer`

```js 
$Init.choice_layer(string id, string[] choices) -> layer
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `choices` -

**Terminates with error if**:
*

### `col_sel`

```js 
$Init.col_sel(string name, bool any_color, color[] swatches) -> col_sel
```

**Returns** <!-- TODO -->

**Parameters**:
* `name` -
* `any_color` -
* `swatches` -

**Terminates with error if**:
*

### `col_sel_layer`

```js 
$Init.col_sel_layer(string id, col_sel[] selections) -> layer
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `selections` -

**Terminates with error if**:
*

### `composed_layer`

```js 
$Init.composed_layer(string id, (string -> image) logic) -> layer
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `logic` -

**Terminates with error if**:
*

### `decision_layer`

```js 
$Init.decision_layer(string id, (-> layer) logic) -> layer
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `logic` -

**Terminates with error if**:
*

### `default_composer`

```js 
$Init.default_composer(string[] directions, bool orientation, anim[] anims) -> (sheet -> (string -> image))
```

**Returns** <!-- TODO -->

**Parameters**:
* `directions` -
* `orientation` -
* `anims` -

**Terminates with error if**:
*

### `dependent_layer`

```js 
$Init.dependent_layer(string id, (string -> image) asset_fetcher_func, layer reference_layer, int relative_index) -> layer
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `asset_fetcher_func` -
* `reference_layer` -
* `relative_index` -

**Terminates with error if**:
*

### `group_layer`

```js 
$Init.group_layer(string id, layer[] members) -> layer
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `members` -

**Terminates with error if**:
*

### `mask_layer`

```js 
$Init.mask_layer(string id, layer[] targets, (string -> image) logic) -> layer
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `targets` -
* `logic` -

**Terminates with error if**:
*

### `math_layer`

```js 
$Init.math_layer(string id, int min, int max, int default, (int -> string) format_func) -> layer
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `min` -
* `max` -
* `default` -
* `format_func` -

**Terminates with error if**:
*

### `no_choice_equal`

```js 
$Init.no_choice_equal() -> no_choice
```

**Returns** <!-- TODO -->

### `no_choice_invalid`

```js 
$Init.no_choice_invalid() -> no_choice
```

**Returns** <!-- TODO -->

### `no_choice_prob`

```js 
$Init.no_choice_prob(float prob) -> no_choice
```

**Returns** <!-- TODO -->

**Parameters**:
* `prob` -

**Terminates with error if**:
*

### `replacement`

```js 
$Init.replacement(int index, (color -> color) func) -> replacement
```

**Returns** <!-- TODO -->

**Parameters**:
* `index` -
* `func` -

**Terminates with error if**:
*

### `sheet`

```js 
$Init.sheet(image source, int sprite_width, int sprite_height) -> sheet
```

**Returns** <!-- TODO -->

**Parameters**:
* `source` -
* `sprite_width` -
* `sprite_height` -

**Terminates with error if**:
*

### `simple_frame_coord_func`

```js 
$Init.simple_frame_coord_func(int first_frame_x, int first_frame_y, bool orientation) -> (int -> int[])
```

**Returns** <!-- TODO -->

**Parameters**:
* `first_frame_x` -
* `first_frame_y` -
* `orientation` -

**Terminates with error if**:
*

### `style`

```js
$Init.style(string id, int[] dims, string[] directions, bool orientation, anim[] anims, {bool : layer<>} layers) -> style
```

**Returns** <!-- TODO -->

**Parameters**:
* `id` -
* `dims` -
* `directions` -
* `orientation` -
* `anims` -
* `layers` -

**Terminates with error if**:
* 
