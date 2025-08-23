[***< Contents***](./README.md)

# `no_choice`

| Represents                                                                                                                                                                           | Class in *TDSM* source code                                                                                                                                          |
|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| The rules governing how (and if) an [`AssetChoiceLayer`](https://github.com/jbunke/tdsm/blob/master/src/com/jordanbunke/tdsm/data/layer/AssetChoiceLayer.java) can make no selection | [`com.jordanbunke.tdsm.data.layer.support.NoAssetChoice`](https://github.com/jbunke/tdsm/blob/master/src/com/jordanbunke/tdsm/data/layer/support/NoAssetChoice.java) |

> **Note:**
>
> The specification uses `NC` to represent an arbitrary `no_choice` instance in property and function definitions.

## Properties

### *`valid`*

```js
NC.valid -> bool
```

**Description:**

Whether no choice is valid according to the configuration `NC`.

### `equal`

```js
NC.equal -> bool
```

**Description:**

Whether the probability of no choice being assigned via randomization is equal to that of any asset choice being assigned, according to the configuration `NC`.

## Functions

### `prob`

**Precondition:** `NC.valid && !NC.equal`

```js
NC.prob() -> float
```

**Returns:**
* The probability of no choice being assigned via randomization if `NC` was constructed with a custom probability (see [`$Init::no_choice_prob`](./init.md#no_choice_prob))
* `0.0` otherwise

---

###  See Also

**`no_choice` constructors:**

* [`$Init::no_choice_equal`](./init.md#no_choice_equal)
* [`$Init::no_choice_invalid`](./init.md#no_choice_invalid)
* [`$Init::no_choice_prob`](./init.md#no_choice_prob)

**Accessor:**

* [`layer::get_no_choice`](./layer.md#get_no_choice)
