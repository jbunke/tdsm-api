[***< Contents***](./README.md)

# `no_choice`

| Represents                                                                                                                                                                           | Class in *TDSM* source code                                                                                                                                          |
|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| The rules governing how (and if) an [`AssetChoiceLayer`](https://github.com/jbunke/tdsm/blob/master/src/com/jordanbunke/tdsm/data/layer/AssetChoiceLayer.java) can make no selection | [`com.jordanbunke.tdsm.data.layer.support.NoAssetChoice`](https://github.com/jbunke/tdsm/blob/master/src/com/jordanbunke/tdsm/data/layer/support/NoAssetChoice.java) |

> **Note:**
>
> The specification uses `NC` to represent an arbitrary `no_choice` instance in property and function definitions.

<!-- TODO - descriptions -->

## Properties

### *`valid`*

```js
NC.valid -> bool
```

### `equal`

```js
NC.equal -> bool
```

## Functions

### `prob`

**Precondition:** `NC.valid && !NC.equal`

```js
NC.prob() -> float
```

<!-- TODO -->