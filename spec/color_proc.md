[***< Contents***](./README.md)

# `$ColorProc`

`$ColorProc` is a utility namespace for color and image processing.

## Functions

### `alpha_mask`

```js 
$ColorProc.alpha_mask(image source, image mask) -> image
```

**Returns** a modified copy of the image `source`, where, for every pixel at position `(x, y)` in `source`, the pixel is excluded from the output if the pixel at `(x, y)` in `mask` is **not transparent** (has an alpha value greater than 0). `source` and `mask` must have the same dimensions.

**Parameters**:
* `source` - The original image
* `mask` - The mask image. The coordinate of every non-transparent pixel in `mask` will be erased from the copy of `source` for the output.

**Fails if**:
* `source.w != mask.w || source.h != mask.h`

### `hsv`

1.  ```js 
    $ColorProc.hsv(float hue, float sat, float val, int alpha) -> color
    ```
    
    **Returns** a color, defined by its hue, saturation, value, and alpha channel components.

    **Parameters**:
    * `hue` - Hue, represented as a floating-point number ranging from `0.0` to `1.0`
    * `sat` - Saturation, represented as a floating-point number ranging from `0.0` to `1.0`
    * `val` - [Value](https://en.wikipedia.org/wiki/Lightness), represented as a floating-point number ranging from `0.0` to `1.0`
    * `alpha` - Alpha (opacity), represented as an integer ranging from `0` to `255`
    
    **Fails if**: <!-- TODO - implementation -->
    * `hue < 0.0 || hue > 1.0`
    * `sat < 0.0 || sat > 1.0`
    * `val < 0.0 || val > 1.0`
    * `alpha < 0 || alpha > 255`

    > **Note**:
    > 
    > Colors can also be defined in *DeltaScript* by their RGB(A) components with the standard library functions [`::rgb`](https://github.com/jbunke/deltascript/blob/master/docs/functions-sl.md#rgb) and [`::rgba`](https://github.com/jbunke/deltascript/blob/master/docs/functions-sl.md#rgba), or as 6- or 8-digit hexcode literals like `#ff0000` (red) or `#80800080` (semi-transparent dark yellow).

2.  ```js
    $ColorProc.hsv(float hue, float sat, float val) -> color
    ```

    **Returns** a color, defined by its hue, saturation, and value components. Assumes an alpha value of `255`/`0xff` -- i.e. opaque.

    > **Note**:
    >
    > `$ColorProc.hsv(hue, sat, val)` is semantically equivalent to `$ColorProc.hsv(hue, sat, val, 255)`.

    **Parameters**:
    * `hue` - Hue, represented as a floating-point number ranging from `0.0` to `1.0`
    * `sat` - Saturation, represented as a floating-point number ranging from `0.0` to `1.0`
    * `val` - Value, represented as a floating-point number ranging from `0.0` to `1.0`

    **Fails if**: <!-- TODO - implementation -->
    * `hue < 0.0 || hue > 1.0`
    * `sat < 0.0 || sat > 1.0`
    * `val < 0.0 || val > 1.0`

### `normalize_hue`

```js 
$ColorProc.normalize_hue(float hue) -> float
```

**Returns** a normalized representation of `hue`; that is, between `0.0` and `1.0`.

**Parameters**:
* `hue` - The initial hue, which may be beyond the normal bounds of `0.0` and `1.0`.

---

###  See Also

**Color replacement type**:
* [`replacement`](./replacement.md)

***DeltaScript* standard library `color` constructors**:
* [`::rgb`](https://github.com/jbunke/deltascript/blob/master/docs/functions-sl.md#rgb)
* [`::rgba`](https://github.com/jbunke/deltascript/blob/master/docs/functions-sl.md#rgba)