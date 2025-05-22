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

### *`sprite_height`*

```js 
S.sprite_height -> int
```

### *`sprites_x`*

```js 
S.sprites_x -> int
```

### *`sprites_y`*

```js 
S.sprites_y -> int
```

### *`source`*

```js 
S.source -> image
```

## Functions

### `sprite_at`

```js 
S.sprite_at(int x, int y) -> image
```
