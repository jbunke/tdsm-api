[***< Contents***](./README.md)

# `layer`

| Represents                                                                      | Class in *TDSM* source code                                                                                                                                    |
|:--------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| A customization and/or assembly layer used in the composition of a sprite sheet | [`com.jordanbunke.tdsm.data.layer.CustomizationLayer`](https://github.com/jbunke/tdsm/blob/master/src/com/jordanbunke/tdsm/data/layer/CustomizationLayer.java) |

> **Note:**
>
> The specification uses `L` to represent an arbitrary `layer` instance in property and function definitions.

<!-- TODO - descriptions -->

## Properties

### *`id`*

```js
L.id -> string
```

### `type`

```js
L.type -> int
```

## Functions

<!-- TODO - more ACL-specific functionality -->

### `choose`

**Precondition:** `L.type == $TDSM.ACL || L.type == $TDSM.CHOICE_L`

```js
L.choose(string asset_code);
L.choose(int asset_index);
```

### `get_col_sels`

**Precondition:** `L.type == $TDSM.COL_SEL_L`

```js
L.get_col_sels() -> col_sel[]
```

### `get_no_choice`

**Precondition:** `L.type == $TDSM.ACL`

```js
L.get_no_choice() -> no_choice
```

### `get_value`

**Precondition:** `L.type == $TDSM.MATH_L`

```js
L.get_value() -> int
```

### `is_locked`

```js
L.is_locked() -> bool
```

### `is_none`

**Precondition:** `L.type == $TDSM.ACL`

```js
L.is_none() -> bool
```

### `lock`

```js
L.lock();
```

### `max_value`

**Precondition:** `L.type == $TDSM.MATH_L`

```js
L.max_value() -> int
```

### `min_value`

**Precondition:** `L.type == $TDSM.MATH_L`

```js
L.min_value() -> int
```

### `none`

**Precondition:** `L.type == $TDSM.ACL && L.get_no_choice().valid`

```js
L.none();
```

### `randomize`

```js
L.randomize();
```

### `set_value`

**Precondition:** `L.type == $TDSM.MATH_L`

```js
L.set_value(int value);
```

### `unlock`

```js
L.unlock();
```

<!-- TODO -->