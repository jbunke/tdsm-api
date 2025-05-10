[***< Contents***](./README.md)

# `$Init`

The `$Init` namespace contains constructor functions for initializing objects of various types.

## Functions

<!-- TODO - full documentation -->

### `anim`

```js 
$Init.anim(string id, int[] ticks_per_frame, (int -> int[]) frame_coord_func, bool is_pong) -> anim
```

### `asset_choice`

```js 
$Init.asset_choice(string id, (color -> replacement) replace_func, col_sel[] selections) -> asset_choice
```

### `asset_choice_layer`

```js 
$Init.asset_choice_layer(string id, int[] dims, (string -> image) asset_fetcher_func, asset_choice[] choices, no_choice nc, (sheet -> (string -> image)) composer, int[] preview_coord) -> layer
```

### `asset_layer`

```js 
$Init.asset_layer(string id, int[] dims, image asset, (sheet -> (string -> image)) composer, (color -> replacement) replace_func) -> layer
```

### `choice_layer`

```js 
$Init.choice_layer(string id, string[] choices) -> layer
```

### `col_sel`

```js 
$Init.col_sel(string name, bool any_color, color[] swatches) -> col_sel
```

### `col_sel_layer`

```js 
$Init.col_sel_layer(string id, col_sel[] selections) -> layer
```

### `composed_layer`

```js 
$Init.composed_layer(string id, (string -> image) logic) -> layer
```

### `decision_layer`

```js 
$Init.decision_layer(string id, (-> layer) logic) -> layer
```

### `default_composer`

```js 
$Init.default_composer(string[] directions, bool orientation, anim[] anims) -> (sheet -> (string -> image))
```

### `dependent_layer`

```js 
$Init.dependent_layer(string id, (string -> image) asset_fetcher_func, layer reference_layer, int relative_index) -> layer
```

### `group_layer`

```js 
$Init.group_layer(string id, layer[] members) -> layer
```

### `mask_layer`

```js 
$Init.mask_layer(string id, layer[] targets, (string -> image) logic) -> layer
```

### `math_layer`

```js 
$Init.math_layer(string id, int min, int max, int default, (int -> string) format_func) -> layer
```

### `no_choice_equal`

```js 
$Init.no_choice_equal() -> no_choice
```

### `no_choice_invalid`

```js 
$Init.no_choice_invalid() -> no_choice
```

### `no_choice_prob`

```js 
$Init.no_choice_prob(float prob) -> no_choice
```

### `replacement`

```js 
$Init.replacement(int index, (color -> color) func) -> replacement
```

### `simple_frame_coord_func`

```js 
$Init.simple_frame_coord_func(int first_frame_x, int first_frame_y, bool orientation) -> (int -> int[])
```

### `style`

```js
$Init.style(string id, int[] dims, string[] directions, bool orientation, anim[] anims, {bool : layer<>} layers) -> style
```
