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

```js 
L.add_dependent(layer dependent);
```

**Description**:

Adds `dependent` to the list of layers dependent on `L`. These layers will be updated whenever the value of `L` changes.

**Parameters**:
* `dependent` - The layer to be made dependent on `L`

**Fails if**:
* `dependent == L`

### `add_influences`

```js 
L.add_influences(col_sel[] selections);
```

**Description**:

Adds each element in `selections` as an influencing color selection to this layer `L`.

**Parameters**:
* `selections` - Array of color selections to be added as influencing selections to layer `L`. Should contain no duplicate elements. Selections are added in array order.

### `choose`

1.  ```js 
    L.choose(string asset_code);
    ```
    
    **Description**:
    * Assigns a choice layer to the choice matching the message `asset_code` (if it exists), or...
    * Assigns an asset choice layer to the asset choice with the code `asset_code` (if it exists)
    
    **Parameters**:
    * `asset_code` - The message or asset code of the choice layer or asset choice layer, respectively, that is being assigned
    
    **Fails if**:
    * `L.type != $TDSM.CHOICE_L && L.type != $TDSM.ACL`
    
2.  ```js 
    L.choose(int index);
    ```
    
    **Description**:
    * Assigns a choice layer or an asset choice layer to the (asset) choice at the index `index` among the layer's (asset) choices
    
    **Parameters**:
    * `index` - The index of the (asset) choice among the choice layer or asset choice layer's array of (asset) choices
    
    **Fails if**:
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

```js 
L.get_choice_at(int index) -> string
```

**Returns** the choice message or asset code at `index` in layer `L`

**Parameters**:
* `index` - The index of the (asset) choice among the choice layer or asset choice layer's array of (asset) choices

**Throws error if**:
* `L.type != $TDSM.CHOICE_L && L.type != $TDSM.ACL`
* `index < 0`
* `index >= L.num_choices()`

### `get_choice_index`

```js 
L.get_choice_index() -> int
```

**Returns**:
* The index of the currently selected choice in the (asset) choice layer
* `-1`, if `L` is an asset choice layer and no choice is currently selected

**Throws error if**:
* `L.type != $TDSM.CHOICE_L && L.type != $TDSM.ACL`

### `get_col_sels`

```js
L.get_col_sels() -> col_sel[]
```

**Returns** the color selections comprising this color selection layer as an array

**Throws error if**:
* `L.type != $TDSM.COL_SEL_L`

### `get_decision`

```js
L.get_decision() -> layer
```

**Returns** the layer outputted by the decision layer `L`'s logic function execution

**Throws error if**:
* `L.type != $TDSM.DECISION_L`

### `get_no_choice`

```js
L.get_no_choice() -> no_choice
```

**Returns** the asset choice layer `L`'s no choice configuration

**Throws error if**:
* `L.type != $TDSM.ACL`

### `get_value`

```js
L.get_value() -> int
```

**Returns** the current integer value of the math layer `L`

**Throws error if**:
* `L.type != $TDSM.MATH_L`

### `is_locked`

```js
L.is_locked() -> bool
```

**Returns** `true` if the layer `L` is locked, `false` otherwise

### `is_none`

**Precondition:** `L.type == $TDSM.ACL`

```js
L.is_none() -> bool
```

**Description**:

<!-- TODO -->

**Throws error if**:
<!-- TODO -->

### `lock`

```js
L.lock();
```

**Description**:

<!-- TODO -->

### `max_value`

**Precondition:** `L.type == $TDSM.MATH_L`

```js
L.max_value() -> int
```

**Description**:

<!-- TODO -->

**Throws error if**:
<!-- TODO -->

### `min_value`

**Precondition:** `L.type == $TDSM.MATH_L`

```js
L.min_value() -> int
```

**Description**:

<!-- TODO -->

**Throws error if**:
<!-- TODO -->

### `naive_mask_logic`

**Precondition:** `L.type == $TDSM.ACL || L.type == $TDSM.DEPENDENT_L`

```js 
L.naive_mask_logic((string -> image) asset_fetcher_func) -> (string -> image)
```

**Description**:

<!-- TODO -->

**Parameters**:
<!-- TODO -->

**Throws error if**:
<!-- TODO -->

### `none`

**Precondition:** `L.type == $TDSM.ACL && L.get_no_choice().valid`

```js
L.none();
```

**Description**:

<!-- TODO -->

**Throws error if**:
<!-- TODO -->

### `num_choices`

**Precondition:** `L.type == $TDSM.ACL || L.type == $TDSM.CHOICE_L`

```js
L.num_choices() -> int
```

**Description**:

<!-- TODO -->

**Throws error if**:
<!-- TODO -->

### `randomize`

```js
L.randomize();
```

**Description**:

<!-- TODO -->

### `set_value`

**Precondition:** `L.type == $TDSM.MATH_L`

```js
L.set_value(int value);
```

**Description**:

<!-- TODO -->

**Parameters**:
<!-- TODO -->

**Fails if**:
<!-- TODO -->

### `unlock`

```js
L.unlock();
```

**Description**:

<!-- TODO -->
