[***< Contents***](./README.md)

# `col_sel`

| Represents                                              | Class in *TDSM* source code                                                                                                                                            |
|:--------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| A color selection; e.g. the assignment of a hair colour | [`com.jordanbunke.tdsm.data.layer.support.ColorSelection`](https://github.com/jbunke/tdsm/blob/master/src/com/jordanbunke/tdsm/data/layer/support/ColorSelection.java) |

> **Note:**
>
> The specification uses `C` to represent an arbitrary `col_sel` instance in property and function definitions.

## Properties

### `any`

```js
C.any -> bool
```

**Description:**

`true` if the color selection can be set to any color, not just one of its swatches; `false` otherwise.

### `name`

```js
C.name -> string
```

**Description:**

A name label associated with this color selection, which is displayed in the user interface if the color selection is part of a color selection layer comprised of multiple color selections.

### `swatches`

```js
C.swatches -> color[]
```

**Description:**

The array of colors defined as swatches (presets) for this color selection.

## Functions

### `get_color`

```js
C.get_color() -> color
```

**Returns** the current assigned color value of this color selection.

### `randomize`

```js
C.randomize();
```

**Description:**

Randomly assigns this color selection's color value to one of its swatch colors.

### `set_color`

```js
C.set_color(color c);
```

**Description:**

Assigns the color `c` to this color selection. [`C.any`](#any) is ignored; `c` is assigned whether it matches a swatch color or not, even if `C.any` is `false`.

**Parameters:**
* `c` - The color to be assigned

### `set_from_swatch`

```js
C.set_from_swatch(int index);
```

**Description:**

Assigns the color to this color selection at the position `index` in `C.swatches`.

**Parameters:**
* `index` - The index in `C.swatches` of the color to be assigned

**Fails if:**
* `index < 0`
* `index >= #| C.swatches`

---

###  See Also

**`col_sel` constructor:**

* [`$Init::col_sel`](./init.md#col_sel)