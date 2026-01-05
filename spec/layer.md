[***< Contents***](./README.md)

# `layer`

| Represents                                                                      | Class in *TDSM* source code                                                                                                                                    |
|:--------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| A customization and/or assembly layer used in the composition of a sprite sheet | [`com.jordanbunke.tdsm.data.layer.CustomizationLayer`](https://github.com/jbunke/tdsm/blob/master/src/com/jordanbunke/tdsm/data/layer/CustomizationLayer.java) |

> **Note:**
>
> The specification uses `L` to represent an arbitrary `layer` instance in property and function definitions.

## Properties

### *`id`*

```js
L.id -> string
```

**Description:**

The identification code of a layer.

### `type`

```js
L.type -> int
```

**Description:**

The type of layer represented by this object, as an integer matching one of the [layer type constants](./global.md#layer-types).

## Functions

<!-- TODO - more ACL-specific functionality -->

### `add_dependent`

```js 
L.add_dependent(layer dependent);
```

**Description:**

Adds `dependent` to the list of layers dependent on `L`. These layers will be updated whenever the value of `L` changes.

**Parameters:**
* `dependent` - The layer to be made dependent on `L`

**Fails if:**
* `dependent == L`

### `add_influences`

```js 
L.add_influences(col_sel[] selections);
```

**Description:**

Adds each element in `selections` as an influencing color selection to this layer `L`.

**Parameters:**
* `selections` - Array of color selections to be added as influencing selections to layer `L`. Should contain no duplicate elements. Selections are added in array order.

### `choose`

1.  ```js 
    L.choose(string asset_code);
    ```
    
    **Description:**
    * Assigns a choice layer to the choice matching the message `asset_code` (if it exists), or...
    * Assigns an asset choice layer to the asset choice with the code `asset_code` (if it exists)
    
    **Parameters:**
    * `asset_code` - The message or asset code of the choice layer or asset choice layer, respectively, that is being assigned
    
    **Fails if:**
    * `L.type != $TDSM.CHOICE_L && L.type != $TDSM.ACL`
    
2.  ```js 
    L.choose(int index);
    ```
    
    **Description:**
    * Assigns a choice layer or an asset choice layer to the (asset) choice at the index `index` among the layer's (asset) choices
    
    **Parameters:**
    * `index` - The index of the (asset) choice among the choice layer or asset choice layer's array of (asset) choices
    
    **Fails if:**
    * `L.type != $TDSM.CHOICE_L && L.type != $TDSM.ACL`
    * `index < 0`
    * `index >= L.num_choices()`

### `compose`

```js 
L.compose() -> (string -> image)
```

**Returns** a function that takes a sprite ID as input and returns this layer's corresponding image output.

### `get_asset_choice`

```js
L.get_asset_choice() -> asset_choice
```

<!-- TODO -->

### `get_asset_choice_at`

```js
L.get_asset_choice_at(int index) -> asset_choice
```

<!-- TODO -->

### `get_choice`

```js 
L.get_choice() -> string
```

**Returns:**
* The currently selected choice message, if `L` is a choice layer
* The currently selected asset code, if `L` is an asset choice layer

**Terminates with error if:**
* `L.type != $TDSM.CHOICE_L && L.type != $TDSM.ACL`
* `L.type == $TDSM.ACL && L.is_none()`

### `get_choice_at`

```js 
L.get_choice_at(int index) -> string
```

**Returns** the choice message or asset code at `index` in layer `L`

**Parameters:**
* `index` - The index of the (asset) choice among the choice layer or asset choice layer's array of (asset) choices

**Terminates with error if:**
* `L.type != $TDSM.CHOICE_L && L.type != $TDSM.ACL`
* `index < 0`
* `index >= L.num_choices()`

### `get_choice_index`

```js 
L.get_choice_index() -> int
```

**Returns:**
* The index of the currently selected choice in the (asset) choice layer
* `-1`, if `L` is an asset choice layer and no choice is currently selected

**Terminates with error if:**
* `L.type != $TDSM.CHOICE_L && L.type != $TDSM.ACL`

### `get_col_sels`

```js
L.get_col_sels() -> col_sel[]
```

**Returns** the color selections comprising this color selection layer as an array

**Terminates with error if:**
* `L.type != $TDSM.COL_SEL_L`

### `get_decision`

```js
L.get_decision() -> layer
```

**Returns** the layer outputted by the decision layer `L`'s logic function execution

**Terminates with error if:**
* `L.type != $TDSM.DECISION_L`

### `get_no_choice`

```js
L.get_no_choice() -> no_choice
```

**Returns** the asset choice layer `L`'s no choice configuration

**Terminates with error if:**
* `L.type != $TDSM.ACL`

### `get_value`

```js
L.get_value() -> int
```

**Returns** the current integer value of the math layer `L`

**Terminates with error if:**
* `L.type != $TDSM.MATH_L`

### `is_locked`

```js
L.is_locked() -> bool
```

**Returns** `true` if the layer `L` is locked; `false` otherwise.

### `is_none`

```js
L.is_none() -> bool
```

**Returns** `true` if the asset choice layer `L` currently has no selected choice; `false` otherwise.

**Terminates with error if:**
* `L.type != $TDSM.ACL`

### `lock`

```js
L.lock();
```

**Description:**

Locks the layer `L`. A locked layer is **exempted** from style-level randomization (see [`style::randomize`](./style.md#randomize)).

### `max_value`

```js
L.max_value() -> int
```

**Returns** the maximum integer value of the math layer `L`.

**Terminates with error if:**
* `L.type != $TDSM.MATH_L`

### `min_value`

```js
L.min_value() -> int
```

**Returns** the minimum integer value of the math layer `L`.

**Terminates with error if:**
* `L.type != $TDSM.MATH_L`

### `naive_mask_logic`

```js 
L.naive_mask_logic((string -> image) asset_fetcher_func) -> (string -> image)
```

**Returns** a mask layer logic function that uses `L` as the basis for the mask logic. `L` being the basis for the mask logic means that the mask logic inherits the current asset code, dimensions, and composer from `L`. The resulting function takes as input the [sprite ID](./theory/t_sprite_id.md) and returns the mask data as an image, where every non-transparent pixel is marked to be erased. The result of this function can be passed as the argument to the `logic` parameter in [`$Init::mask_layer`](./init.md#mask_layer).

**Parameters:**
* `asset_fetcher_func` - A function that takes as input an asset code and returns the source image for that asset code for the mask logic. Images should be the same dimensions and sprite layout as those returned by `L`'s asset fetcher function (see [`$Init::asset_choice_layer`](./init.md#asset_choice_layer) and [`$Init::dependent_layer`](./init.md#dependent_layer)).

**Terminates with error if:**
* `L.type != $TDSM.ACL && L.type != $TDSM.DEPENDENT_L`

### `none`

```js
L.none();
```

**Description:**

Sets the asset choice selection of the asset choice layer `L` to no selection.

**Fails if:**
* `!(L.type == $TDSM.ACL && L.get_no_choice().valid)`

### `num_choices`

**Precondition:** `L.type == $TDSM.ACL || L.type == $TDSM.CHOICE_L`

```js
L.num_choices() -> int
```

**Returns** the number of (asset) choices of this (asset) choice layer

**Terminates with error if:**
* `L.type != $TDSM.ACL && L.type != $TDSM.CHOICE_L`

### `randomize`

```js
L.randomize();
```

**Description:**

Randomizes the value of `L`. The effect of randomization depends on the type of layer that `L` is:

|                             Layer type                             |                                                                                                                                      Effect                                                                                                                                      |
|:------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|    [Asset choice layer](./theory/t_layer.md#asset-choice-layer)    | Randomly sets the asset choice layer to one of its asset choices, or no choice, depending on its no choice configuration. If it chooses an asset choice, the asset choice's color selections are randomized (see [`col_sel::randomize`](./col_sel.md#randomize)), if it has any. |
|          [Choice layer](./theory/t_layer.md#choice-layer)          |                                                                                                           Randomly sets the choice layer to one of its choice messages                                                                                                           |
| [Color selection layer](./theory/t_layer.md#color-selection-layer) |                                                                                   Randomizes each color selection that comprises the CSL (see [`col_sel::randomize`](./col_sel.md#randomize))                                                                                    |
|        [Decision layer](./theory/t_layer.md#decision-layer)        |                                                                                                              Computes its output layer and attempts to randomize it                                                                                                              |
|           [Group layer](./theory/t_layer.md#group-layer)           |                                                                                                                 Randomizes each member layer of the group layer                                                                                                                  |
|            [Math layer](./theory/t_layer.md#math-layer)            |                                                                                  Randomly assigns the math layer an integer value within the inclusive bounds of its minimum and maximum values                                                                                  |
|                         Other layer types                          |                                                                                                                                    No effect                                                                                                                                     |

### `set_value`

```js
L.set_value(int value);
```

**Description:**

Assigns the math layer `L` a value of `value`. `value` will be [clamped](https://en.wikipedia.org/wiki/Clamp_(function)) by `L`'s minimum and maximum if it is out of bounds.

**Parameters:**
* `value` - The integer to be set as the value of `L`

**Fails if:**
* `L.type != $TDSM.MATH_L`

### `unlock`

```js
L.unlock();
```

**Description:**

Unlocks the layer `L`. A locked layer is **included** in style-level randomization (see [`style::randomize`](./style.md#randomize)).

---

###  See Also

**`layer` constructors:**

* [`$Init::asset_choice_layer`](./init.md#asset_choice_layer)
* [`$Init::asset_layer`](./init.md#asset_layer)
* [`$Init::choice_layer`](./init.md#choice_layer)
* [`$Init::col_sel_layer`](./init.md#col_sel_layer)
* [`$Init::composed_layer`](./init.md#composed_layer)
* [`$Init::decision_layer`](./init.md#decision_layer)
* [`$Init::dependent_layer`](./init.md#dependent_layer)
* [`$Init::group_layer`](./init.md#group_layer)
* [`$Init::mask_layer`](./init.md#mask_layer)
* [`$Init::math_layer`](./init.md#math_layer)
