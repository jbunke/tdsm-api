[***< Contents***](./README.md)

# `anim`

| Represents                                                        | Class in *TDSM* source code                                                                                                      |
|:------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------|
| An animation of a particular sprite style ([`style`](./style.md)) | [`com.jordanbunke.tdsm.data.Animation`](https://github.com/jbunke/tdsm/blob/master/src/com/jordanbunke/tdsm/data/Animation.java) |

> **Note:**
> 
> The specification uses `A` to represent an arbitrary `anim` instance in property and function definitions.

## Properties

### *`id`*

```js
A.id -> string
```

**Description**:

The identification code of an animation. This code should be unique among all the animations of a [`style`](./style.md). Animation IDs mustn't contain hyphens (`-`), as this would break the parsing of sprite IDs.

## Functions

### `get_frame_count`

```js
A.get_frame_count() -> int
```

**Returns** the number of frames in the animation.

> **Note:**
> 
> An animation with a four-frame cycle, but only three visually unique frames (e.g. walk: step 1, middle, step 2) will return 3 instead of 4 if it is defined as an oscillating (ping-pong) animation rather than a looping animation.

---

###  See Also

**`anim` constructor**:

* [`$Init::anim`](./init.md#anim)
