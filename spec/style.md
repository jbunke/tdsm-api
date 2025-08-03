[***< Contents***](./README.md)

# `style`

| Represents                                                                                                                                                    | Class in *TDSM* source code                                                                                                          |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------|
| A particular sprite style, which defines its own set of animations ([`anim`](./anim.md)), customization layers ([`layer`](./layer.md)), and composition rules | [`com.jordanbunke.tdsm.data.style.Style`](https://github.com/jbunke/tdsm/blob/master/src/com/jordanbunke/tdsm/data/style/Style.java) |

> **Note:**
>
> The specification uses `S` to represent an arbitrary `style` instance in property and function definitions.

<!-- TODO - descriptions -->

## Properties

### *`id`*

```js
S.id -> string
```

**Description**:

The identification of the sprite style `S`. ID must be unique among sprite styles uploaded to *TDSM* at any given time. Uploading a sprite style with an ID matching another sprite style previously uploaded to the program will override the existing sprite style.

## Functions

### `all_anims`

```js
S.all_anims() -> anim[]
```

**Returns** all the animations defined by the sprite style `S` as an array.

### `all_dirs`

```js
S.all_dirs() -> string[]
```

**Returns** all the directions defined by the sprite style `S` as an array.

> **Related material**:
> * [Direction constants](./global.md#directions)

### `assembly`

```js
S.assembly() -> layer<>
```

**Returns** the [assembly layers](TODO) of the sprite style `S` as a list.

### `custom`

```js
S.custom() -> layer<>
```

**Returns** the [customization layers](TODO) of the sprite style `S` as a list.

### `def_sprite_dims`

```js
S.def_sprite_dims() -> int[]
```

**Returns** the default dimensions of a single sprite/frame of the sprite style `S`. The dimensions are represented as a two-integer array, where indices `0` and `1` represents width and height in pixels, respectively.

> **Related material**:
> * [Coordinate constants](./global.md#coordinate)

### `get_anims`

```js
S.get_anims() -> anim[]
```

**Returns** an array of the animations of the sprite style `S` that are **currently enabled** for export, in the current sequencing order.

### `get_dirs`

```js
S.get_dirs() -> string[]
```

**Returns** an array of the directions of the sprite sheet `S` that are **currently enabled** for export, in the current sequencing order.

> **Related material**:
> * [Direction constants](./global.md#directions)

### `get_edge`

```js
S.get_edge(int edge) -> int
```

**Returns** the amount of padding or cropping, in pixels, along a particular edge, relative to the default sprite dimensions (see [`style::def_sprite_dims`](#def_sprite_dims)) of the sprite style `S`, as currently configured.

**Parameters**:
* `edge` - The edge (left, right, top, or bottom) to be checked (see [Edge constants](./global.md#coordinate))

**Throws error if**:
* `edge < 0`
* `edge >= 4`

> **Related material**:
> * [`style::reset_padding`](#reset_padding)
> * [`style::sprite_dims`](#sprite_dims)

### `get_frames_per_dim`

```js
S.get_frames_per_dim() -> int
```

**Returns** the number of animation frames per row or column of an exported sprite sheet of sprite style `S`, according to its current layout configuration.

> **Related material**:
> * [`style::set_frames_per_dim`](#set_frames_per_dim)
> * [`style::get_orientation`](#get_orientation)
> * [`style::set_orientation`](#set_orientation)
> * [`style::is_all_anims_single_dim`](#is_all_anims_single_dim)
> * [`style::set_all_anims_single_dim`](#set_all_anims_single_dim)
> * [`style::is_multiple_anims_per_dim`](#is_multiple_anims_per_dim)
> * [`style::set_multiple_anims_per_dim`](#set_multiple_anims_per_dim)
> * [`style::is_wrap_anims_across_dims`](#is_wrap_anims_across_dims)
> * [`style::set_wrap_anims_across_dims`](#set_wrap_anims_across_dims)
> * [`style::reset_layout`](#reset_layout)

### `get_layer`

```js
S.get_layer(string id) -> layer
```

**Returns** the first matching *top-level* constituent layer of the sprite sheet `S` with the ID `id`; first searches customization layers, then assembly layers.

> For example, a member layer of a [**group layer**](TODO) that is not separately defined as a top-level customization or assembly layer of `S` cannot be identified by this function.

**Parameters**:
* `id` - The layer ID to match against

**Throws error if**:
* `!S.has_layer(id)`

> **Related material**:
> * [`style::has_layer`](#has_layer)

### `get_orientation`

```js
S.get_orientation() -> bool
```

**Returns** the `bool` value corresponding with the animation sequencing orientation (see [Orientation constants](./global.md#orientation)) of the sprite style `S`'s current layout configuration.

> **Related material**:
> * [`style::get_frames_per_dim`](#get_frames_per_dim)
> * [`style::set_frames_per_dim`](#set_frames_per_dim)
> * [`style::set_orientation`](#set_orientation)
> * [`style::is_all_anims_single_dim`](#is_all_anims_single_dim)
> * [`style::set_all_anims_single_dim`](#set_all_anims_single_dim)
> * [`style::is_multiple_anims_per_dim`](#is_multiple_anims_per_dim)
> * [`style::set_multiple_anims_per_dim`](#set_multiple_anims_per_dim)
> * [`style::is_wrap_anims_across_dims`](#is_wrap_anims_across_dims)
> * [`style::set_wrap_anims_across_dims`](#set_wrap_anims_across_dims)
> * [`style::reset_layout`](#reset_layout)

### `has_layer`

```js
S.has_layer(string id) -> bool
```

**Returns** `true` if the sprite style `S` contains a layer with the ID `id` among its *top-level* customization or assembly layers.

> For example, a member layer of a [**group layer**](TODO) that is not separately defined as a top-level customization or assembly layer of `S` cannot be identified by this function.

**Parameters**:
* `id` - The layer ID to match against

### `has_output`

```js
S.has_output() -> bool
```

**Returns** `true` if the current animation and direction configuration is valid and yields at least one animation frame; `false` otherwise.

### `is_all_anims_single_dim`

```js
S.is_all_anims_single_dim() -> bool
```

**Returns** `true` if the sprite style `S` is currently configured to render sprite sheets with all included animations sequenced end-to-end on the same row or column; `false` otherwise.

> **Note**:
> 
> `S.is_multiple_anims_per_dim()` must be `true` in order for the value of this function to affect sprite sheet layout.

> **Related material**:
> * [`style::get_frames_per_dim`](#get_frames_per_dim)
> * [`style::set_frames_per_dim`](#set_frames_per_dim)
> * [`style::get_orientation`](#get_orientation)
> * [`style::set_orientation`](#set_orientation)
> * [`style::set_all_anims_single_dim`](#set_all_anims_single_dim)
> * [`style::is_multiple_anims_per_dim`](#is_multiple_anims_per_dim)
> * [`style::set_multiple_anims_per_dim`](#set_multiple_anims_per_dim)
> * [`style::is_wrap_anims_across_dims`](#is_wrap_anims_across_dims)
> * [`style::set_wrap_anims_across_dims`](#set_wrap_anims_across_dims)
> * [`style::reset_layout`](#reset_layout)

### `is_multiple_anims_per_dim`

```js
S.is_multiple_anims_per_dim() -> bool
```

**Returns** `true` if the sprite style `S` is currently configured to render sprite sheets with multiple animations sequenced end-to-end on a single row or column; `false` otherwise.

> **Related material**:
> * [`style::get_frames_per_dim`](#get_frames_per_dim)
> * [`style::set_frames_per_dim`](#set_frames_per_dim)
> * [`style::get_orientation`](#get_orientation)
> * [`style::set_orientation`](#set_orientation)
> * [`style::is_all_anims_single_dim`](#is_all_anims_single_dim)
> * [`style::set_all_anims_single_dim`](#set_all_anims_single_dim)
> * [`style::set_multiple_anims_per_dim`](#set_multiple_anims_per_dim)
> * [`style::is_wrap_anims_across_dims`](#is_wrap_anims_across_dims)
> * [`style::set_wrap_anims_across_dims`](#set_wrap_anims_across_dims)
> * [`style::reset_layout`](#reset_layout)

### `is_wrap_anims_across_dims`

```js
S.is_wrap_anims_across_dims() -> bool
```

**Returns** `true` if the sprite style `S` is currently configured to render sprite sheets with multiple animations sequenced end-to-end on a single row or column, while allowing for animations to begin (frame 1) on a given row or column and end on a subsequent row or column; `false` otherwise.

> **Note**:
>
> In order for the value of this function to affect sprite sheet layout...
> * `S.is_multiple_anims_per_dim()` must be `true`
> * `S.is_all_anims_single_dim()` must be `false`

> **Related material**:
> * [`style::get_frames_per_dim`](#get_frames_per_dim)
> * [`style::set_frames_per_dim`](#set_frames_per_dim)
> * [`style::get_orientation`](#get_orientation)
> * [`style::set_orientation`](#set_orientation)
> * [`style::is_all_anims_single_dim`](#is_all_anims_single_dim)
> * [`style::set_all_anims_single_dim`](#set_all_anims_single_dim)
> * [`style::is_multiple_anims_per_dim`](#is_multiple_anims_per_dim)
> * [`style::set_multiple_anims_per_dim`](#set_multiple_anims_per_dim)
> * [`style::set_wrap_anims_across_dims`](#set_wrap_anims_across_dims)
> * [`style::reset_layout`](#reset_layout)

### `randomize`

```js
S.randomize();
```

**Description**:

Randomizes the current sprite customization of the sprite style `S`. All [**unlocked**](TODO) customization layers of `S` are sequentially randomized (see [`layer::randomize`](./layer.md#randomize)).

### `render`

```js
S.render() -> image
```

**Returns** a render of the current sprite sheet of the sprite style `S`, based on the current...
* Sprite customization
* Padding configuration
* Sequencing configuration
* Layout configuration

**Throws error if**:
* `!S.has_output()` <!-- TODO - implementation -->

### `reset_layout`

```js
S.reset_layout();
```

**Description**:

<!-- TODO -->

> **Related material**:
> * [`style::get_frames_per_dim`](#get_frames_per_dim)
> * [`style::set_frames_per_dim`](#set_frames_per_dim)
> * [`style::get_orientation`](#get_orientation)
> * [`style::set_orientation`](#set_orientation)
> * [`style::is_all_anims_single_dim`](#is_all_anims_single_dim)
> * [`style::set_all_anims_single_dim`](#set_all_anims_single_dim)
> * [`style::is_multiple_anims_per_dim`](#is_multiple_anims_per_dim)
> * [`style::set_multiple_anims_per_dim`](#set_multiple_anims_per_dim)
> * [`style::is_wrap_anims_across_dims`](#is_wrap_anims_across_dims)
> * [`style::set_wrap_anims_across_dims`](#set_wrap_anims_across_dims)

### `reset_padding`

```js
S.reset_padding();
```

**Description**:

<!-- TODO -->

### `reset_sequencing`

```js
S.reset_sequencing();
```

**Description**:

<!-- TODO -->

### `set_all_anims_single_dim`

```js
S.set_all_anims_single_dim(bool single_dim);
```

**Description**:

<!-- TODO -->

**Parameters**:
* <!-- TODO -->

> **Related material**:
> * [`style::get_frames_per_dim`](#get_frames_per_dim)
> * [`style::set_frames_per_dim`](#set_frames_per_dim)
> * [`style::get_orientation`](#get_orientation)
> * [`style::set_orientation`](#set_orientation)
> * [`style::is_all_anims_single_dim`](#is_all_anims_single_dim)
> * [`style::is_multiple_anims_per_dim`](#is_multiple_anims_per_dim)
> * [`style::set_multiple_anims_per_dim`](#set_multiple_anims_per_dim)
> * [`style::is_wrap_anims_across_dims`](#is_wrap_anims_across_dims)
> * [`style::set_wrap_anims_across_dims`](#set_wrap_anims_across_dims)
> * [`style::reset_layout`](#reset_layout)

### `set_anims`

```js 
S.set_anims(anim[] animations);
S.set_anims(anim<> animations);
```

**Description**:

<!-- TODO -->

**Parameters**:
* `animations` - The collection of animations to be set as active. The collection can be provided as an array or a list.

**Fails if**:
* <!-- TODO -->

### `set_dirs`

```js 
S.set_dirs(string[] directions);
S.set_dirs(string<> directions);
```

**Description**:

<!-- TODO -->

**Parameters**:
* `directions` - The collection of directions to be set as active. The collection can be provided as an array or a list.

**Fails if**:
* <!-- TODO -->

### `set_edge`

```js
S.set_edge(int edge, int value);
```

**Description**:

<!-- TODO -->

**Parameters**:
* <!-- TODO -->

**Fails if**:
* <!-- TODO -->

### `set_frames_per_dim`

```js
S.set_frames_per_dim(int fpd);
```

**Description**:

<!-- TODO -->

**Parameters**:
* <!-- TODO -->

**Fails if**:
* <!-- TODO -->

> **Related material**:
> * [`style::get_frames_per_dim`](#get_frames_per_dim)
> * [`style::get_orientation`](#get_orientation)
> * [`style::set_orientation`](#set_orientation)
> * [`style::is_all_anims_single_dim`](#is_all_anims_single_dim)
> * [`style::set_all_anims_single_dim`](#set_all_anims_single_dim)
> * [`style::is_multiple_anims_per_dim`](#is_multiple_anims_per_dim)
> * [`style::set_multiple_anims_per_dim`](#set_multiple_anims_per_dim)
> * [`style::is_wrap_anims_across_dims`](#is_wrap_anims_across_dims)
> * [`style::set_wrap_anims_across_dims`](#set_wrap_anims_across_dims)
> * [`style::reset_layout`](#reset_layout)

### `set_multiple_anims_per_dim`

```js
S.set_multiple_anims_per_dim(bool mapd);
```

**Description**:

<!-- TODO -->

**Parameters**:
* <!-- TODO -->

> **Related material**:
> * [`style::get_frames_per_dim`](#get_frames_per_dim)
> * [`style::set_frames_per_dim`](#set_frames_per_dim)
> * [`style::get_orientation`](#get_orientation)
> * [`style::set_orientation`](#set_orientation)
> * [`style::is_all_anims_single_dim`](#is_all_anims_single_dim)
> * [`style::set_all_anims_single_dim`](#set_all_anims_single_dim)
> * [`style::is_multiple_anims_per_dim`](#is_multiple_anims_per_dim)
> * [`style::is_wrap_anims_across_dims`](#is_wrap_anims_across_dims)
> * [`style::set_wrap_anims_across_dims`](#set_wrap_anims_across_dims)
> * [`style::reset_layout`](#reset_layout)

### `set_orientation`

```js
S.set_orientation(bool orientation);
```

**Description**:

<!-- TODO -->

**Parameters**:
* `orientation` - The `bool` value corresponding with the desired animation sequencing orientation (see [Orientation constants](./global.md#orientation))

> **Related material**:
> * [`style::get_frames_per_dim`](#get_frames_per_dim)
> * [`style::set_frames_per_dim`](#set_frames_per_dim)
> * [`style::get_orientation`](#get_orientation)
> * [`style::is_all_anims_single_dim`](#is_all_anims_single_dim)
> * [`style::set_all_anims_single_dim`](#set_all_anims_single_dim)
> * [`style::is_multiple_anims_per_dim`](#is_multiple_anims_per_dim)
> * [`style::set_multiple_anims_per_dim`](#set_multiple_anims_per_dim)
> * [`style::is_wrap_anims_across_dims`](#is_wrap_anims_across_dims)
> * [`style::set_wrap_anims_across_dims`](#set_wrap_anims_across_dims)
> * [`style::reset_layout`](#reset_layout)

### `set_padding`

```js
S.set_padding(int left, int right, int top, int bottom);
```

**Description**:

<!-- TODO -->

**Parameters**:
* <!-- TODO -->

**Fails if**:
* <!-- TODO -->

### `set_wrap_anims_across_dims`

```js
S.set_wrap_anims_across_dims(bool wrap);
```

**Description**:

<!-- TODO -->

**Parameters**:
* <!-- TODO -->

> **Related material**:
> * [`style::get_frames_per_dim`](#get_frames_per_dim)
> * [`style::set_frames_per_dim`](#set_frames_per_dim)
> * [`style::get_orientation`](#get_orientation)
> * [`style::set_orientation`](#set_orientation)
> * [`style::is_all_anims_single_dim`](#is_all_anims_single_dim)
> * [`style::set_all_anims_single_dim`](#set_all_anims_single_dim)
> * [`style::is_multiple_anims_per_dim`](#is_multiple_anims_per_dim)
> * [`style::set_multiple_anims_per_dim`](#set_multiple_anims_per_dim)
> * [`style::is_wrap_anims_across_dims`](#is_wrap_anims_across_dims)
> * [`style::reset_layout`](#reset_layout)

### `sprite_dims`

```js
S.sprite_dims() -> int[]
```

**Returns** <!-- TODO -->
