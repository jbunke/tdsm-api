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

**Description**:

The identification code of a layer.

### `type`

```js
L.type -> int
```

**Description**:

The type of layer represented by this object, as an integer matching one of the [layer type constants](./global.md/#layer-types).

## Functions

<!-- TODO - more ACL-specific functionality -->

### `add_dependent`

<!-- TODO - precondition: dependent layer cannot be L -->

```js 
L.add_dependent(layer dependent);
```

**Description**:

<!-- TODO -->

**Parameters**:

<!-- TODO -->

**Fail conditions**:

<!-- TODO -->

### `add_influences`

```js 
L.add_influences(col_sel[] selections);
```

### `choose`

1.  ```js 
    L.choose(string asset_code);
    ```
    
    **Description**:
    * Assigns a choice layer to the choice matching the message `asset_code` (if it exists), or...
    * Assigns an asset choice layer to the asset choice with the code `asset_code` (if it exists)
    
    **Parameters**:
    * `asset_code` - The message or asset code of the choice layer or asset choice layer, respectively, that is being assigned
    
    **Fail conditions**:
    * `L.type != $TDSM.CHOICE_L && L.type != $TDSM.ACL`
    
2.  ```js 
    L.choose(int index);
    ```
    
    **Description**:
    * Assigns a choice layer or an asset choice layer to the (asset) choice at the index `index` among the layer's (asset) choices
    
    **Parameters**:
    * `index` - The index of the (asset) choice among the choice layer or asset choice layer's array of (asset) choices
    
    **Fail conditions**:
    * `L.type != $TDSM.CHOICE_L && L.type != $TDSM.ACL`
    * `index < 0`
    * `index >= L.num_choices()`

### `compose`

```js 
L.compose() -> (string -> image)
```

**Returns** a function that takes a sprite ID as input and returns this layer's corresponding image output.

### `get_choice`

```js 
L.get_choice() -> string
```

**Returns**:
* The currently selected choice message, if `L` is a choice layer
* The currently selected asset code, if `L` is an asset choice layer

**Throws error if**:
* `L.type != $TDSM.CHOICE_L && L.type != $TDSM.ACL`
* `L.type == $TDSM.ACL && L.is_none()`

### `get_choice_at`

**Precondition:** `L.type == $TDSM.ACL || L.type == $TDSM.CHOICE_L`

```js 
L.get_choice_at(int index) -> string
```

### `get_choice_index`

**Precondition:** `L.type == $TDSM.ACL || L.type == $TDSM.CHOICE_L`

```js 
L.get_choice_index() -> int
```

### `get_col_sels`

**Precondition:** `L.type == $TDSM.COL_SEL_L`

```js
L.get_col_sels() -> col_sel[]
```

### `get_decision`

**Precondition:** `L.type == $TDSM.DECISION_L`

```js
L.get_decision() -> layer
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

### `naive_mask_logic`

**Precondition:** `L.type == $TDSM.ACL || L.type == $TDSM.DEPENDENT_L`

```js 
L.naive_mask_logic((string -> image) asset_fetcher_func) -> (string -> image)
```

### `none`

**Precondition:** `L.type == $TDSM.ACL && L.get_no_choice().valid`

```js
L.none();
```

### `num_choices`

**Precondition:** `L.type == $TDSM.ACL || L.type == $TDSM.CHOICE_L`

```js
L.num_choices() -> int
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