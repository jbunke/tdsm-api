[***< Contents***](./README.md)

# `col_sel`

| Represents                                              | Class in *TDSM* source code                                                                                                                                            |
|:--------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| A color selection; e.g. the assignment of a hair colour | [`com.jordanbunke.tdsm.data.layer.support.ColorSelection`](https://github.com/jbunke/tdsm/blob/master/src/com/jordanbunke/tdsm/data/layer/support/ColorSelection.java) |

> **Note:**
>
> The specification uses `C` to represent an arbitrary `col_sel` instance in property and function definitions.

<!-- TODO - descriptions -->

## Properties

### `any`

```js
C.any -> bool
```

### `name`

```js
C.name -> string
```

### `swatches`

```js
C.swatches -> color[]
```

## Functions

### `get_color`

```js
C.get_color() -> color
```

### `randomize`

```js
C.randomize();
```

### `set_color`

```js
C.set_color(color c);
```

<!-- TODO -->