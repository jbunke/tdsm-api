[***< Contents***](./README.md)

# `asset_choice`

| Represents                                             | Class in *TDSM* source code                                                                                                                                        |
|:-------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| An asset choice used to populate an asset choice layer | [`com.jordanbunke.tdsm_api.util.AssetChoiceConstruct`](https://github.com/jbunke/tdsm-api/blob/master/src/com/jordanbunke/tdsm_api/util/AssetChoiceConstruct.java) |

> **Note:**
>
> The specification uses `AC` to represent an arbitrary `asset_choice` instance in property and function definitions.

## Properties

### *`id`*

```js
AC.id -> string
```

**Description:**

The identification code of an asset choice. Each asset choice in an asset choice layer must have a unique ID.

## Functions

### `get_col_sels`

```js
AC.get_col_sels() -> col_sel[]
```

**Returns** the influencing color selections defined as part of `AC`.

### `randomize`

```js
AC.randomize();
```

**Description:**

Randomizes each color selection that is defined as part of `AC`.

> **Related material:**
> * [`col_sel::randomize`](./col_sel.md#randomize)

---

###  See Also

**`asset_choice` constructor:**

* [`$Init::asset_choice`](./init.md#asset_choice)