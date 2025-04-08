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

## Functions

### `all_anims`

```js
S.all_anims() -> anim[]
```

### `all_dirs`

```js
S.all_dirs() -> string[]
```

### `assembly`

```js
S.assembly() -> layer<>
```

### `custom`

```js
S.custom() -> layer<>
```

### `def_sprite_dims`

```js
S.def_sprite_dims() -> int[]
```

### `get_anims`

```js
S.get_anims() -> anim[]
```

### `get_dirs`

```js
S.get_dirs() -> string[]
```

### `get_edge`

```js
S.get_edge(int edge) -> int
```

### `has_output`

```js
S.has_output() -> bool
```

**Returns** `true` if the current animation and direction configuration is valid and yields at least one animation frame, `false` otherwise.

### `randomize`

```js
S.randomize();
```

### `render`

```js
S.render() -> image
```

### `reset_layout`

```js
S.reset_layout();
```

### `reset_padding`

```js
S.reset_padding();
```

### `reset_sequencing`

```js
S.reset_sequencing();
```

### `set_anims`

```js
S.set_anims(anim[] animations);
S.set_anims(anim<> animations);
```

### `set_dirs`

```js
S.set_dirs(string[] directions);
S.set_dirs(string<> directions);
```

### `set_edge`

```js
S.set_edge(int edge, int value);
```

### `set_padding`

```js
S.set_padding(int left, int right, int top, int bottom);
```

### `sprite_dims`

```js
S.sprite_dims() -> int[]
```

<!-- TODO -->