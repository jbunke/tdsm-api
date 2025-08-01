[***< Contents***](./README.md)

# `sheet`

| Represents                                                                 | Class in *TDSM* source code                                                                                                                                       |
|:---------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| An `image` partitioned into sprites of a particular pixel width and height | [`com.jordanbunke.delta_time.sprite.SpriteSheet`](https://github.com/jbunke/delta-time/blob/master/sprite/src/com/jordanbunke/delta_time/sprite/SpriteSheet.java) |

> **Note:**
>
> The specification uses `S` to represent an arbitrary `sheet` instance in property and function definitions.

## Properties

### *`sprite_width`*

```js 
S.sprite_width -> int
```

**Description**:

The width of a single sprite/frame of the sprite sheet `S`, in pixels

### *`sprite_height`*

```js 
S.sprite_height -> int
```

**Description**:

The height of a single sprite/frame of the sprite sheet `S`, in pixels

### *`sprites_x`*

```js 
S.sprites_x -> int
```

**Description**:

The number of columns of sprites/frames the sprite sheet `S` contains

### *`sprites_y`*

```js 
S.sprites_y -> int
```

**Description**:

The number of rows of sprites/frames the sprite sheet `S` contains

### *`source`*

```js 
S.source -> image
```

**Description**:

The entire sprite sheet `S` as an image

## Functions

### `sprite_at`

```js 
S.sprite_at(int x, int y) -> image
```

**Returns** the sprite at the coordinates (`x`, `y`) of the sprite sheet `S`.

**Parameters**:
* `x` - The 0-based X coordinate
* `y` - The 0-based Y coordinate

**Throws error if**:
* `x < 0`
* `y < 0`
* `x >= S.sprites_x`
* `y >= S.sprites_y`

<hr>

### See Also

**`sheet` constructor**:

* [`$Init::sheet`](./init.md#sheet)
