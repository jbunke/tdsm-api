[***< Contents***](./README.md)

# `replacement`

| Represents              | Class in *TDSM* source code                                                                                                          |
|:------------------------|:-------------------------------------------------------------------------------------------------------------------------------------|
| Color replacement logic | [`com.jordanbunke.tdsm.data.Replacement`](https://github.com/jbunke/tdsm/blob/master/src/com/jordanbunke/tdsm/data/Replacement.java) |

> **Note:**
>
> The specification uses `R` to represent an arbitrary `replacement` instance in property and function definitions.

## Properties

### *`index`*

```js
R.index -> int
```

**Description**:

The index of the color to be fed into `R`'s replacement function, from among the asset choice or asset layer's influencing color selections.

### *`func`*

```js 
R.func -> (color -> color)
```

**Description**:

The color replacement function associated with `R`.

<hr>

###  See Also

**`replacement` constructor**:

* [`$Init::replacement`](./init.md#replacement)

**Theory**:

* [Color replacement]() <!-- TODO -->
