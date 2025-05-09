[***< Contents***](./README.md)

# `$Init`

The `$Init` namespace contains constructor functions for initializing objects of various types.

## Functions

<!-- TODO - full documentation -->

### `anim`

```js 
$Init.anim(string id, int[] ticks_per_frame, (int -> int[]) frame_coord_func, bool is_pong) -> anim
```

### `choice_layer`

```js 
$Init.choice_layer(string id, string[] choices) -> layer
```

### `col_sel`

```js 
$Init.col_sel(string name, bool any_color, color[] swatches) -> col_sel
```

### `composed_layer`

```js 
$Init.composed_layer(string id, (string -> image) logic) -> layer
```

### `decision_layer`

```js 
$Init.decision_layer(string id, (-> layer) logic) -> layer
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
