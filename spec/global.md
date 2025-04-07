[***< Contents***](./README.md)

# Global namespace (`$TDSM`)

The global namespace is essentially a utility class containing constants and functions not bound to an object.

## Constants

> **Note:**
> 
> Constants are bound to primitive values of type `bool`, `int`, or `string`. These mustn't necessarily be used and can be replaced where appropriate by the corresponding literal, but using the constants instead makes scripts more **readable** and especially **maintainable**, should constant values be reassigned in future updates.

### Layer types

| Constant           | Type  | Value | Represents                                |
|:-------------------|:-----:|:-----:|:------------------------------------------|
| `$TDSM.ACL`        | `int` |  `0`  | [`AssetChoiceLayer`]()                    |
| `$TDSM.COL_SEL_L`  | `int` |  `1`  | [`ColorSelectionLayer`]()                 |
| `$TDSM.DECISION_L` | `int` |  `2`  | [`DecisionLayer`]()                       |
| `$TDSM.MATH_L`     | `int` |  `3`  | [`MathLayer`]()                           |
| `$TDSM.CHOICE_L`   | `int` |  `4`  | [`ChoiceLayer`]()                         |
| `$TDSM.OTHER_L`    | `int` | `-1`  | Other [`CustomizationLayer`]() subclasses |

<!-- TODO - other layer types: group, mask, dependent component -->

### Directions

| Constant   |   Type   | Value  | Represents            |
|:-----------|:--------:|:------:|:----------------------|
| `$TDSM.N`  | `string` | `"N"`  | North- or up-facing   |
| `$TDSM.W`  | `string` | `"W"`  | West- or left-facing  |
| `$TDSM.S`  | `string` | `"S"`  | South- or down-facing |
| `$TDSM.E`  | `string` | `"E"`  | East- or right-facing |
| `$TDSM.NW` | `string` | `"NW"` | Northwest-facing      |
| `$TDSM.NE` | `string` | `"NE"` | Northeast-facing      |
| `$TDSM.SW` | `string` | `"SW"` | Southwest-facing      |
| `$TDSM.SE` | `string` | `"SE"` | Southeast-facing      |

### Coordinate

| Constant       | Type  | Value | Represents                                  |
|:---------------|:-----:|:-----:|:--------------------------------------------|
| `$TDSM.X`      | `int` |  `0`  | The X coordinate of an X,Y coordinate pair  |
| `$TDSM.Y`      | `int` |  `1`  | The Y coordinate of an X,Y coordinate pair  |
| `$TDSM.LEFT`   | `int` |  `0`  | The left edge of a sprite/image             |
| `$TDSM.RIGHT`  | `int` |  `1`  | The right edge of a sprite/image            |
| `$TDSM.TOP`    | `int` |  `2`  | The top edge of a sprite/image              |
| `$TDSM.BOTTOM` | `int` |  `3`  | The bottom edge of a sprite/image           |

### Orientation

| Constant     |  Type  |  Value  | Represents                           |
|:-------------|:------:|:-------:|:-------------------------------------|
| `$TDSM.HORZ` | `bool` | `false` | Horizontal (L to R) frame sequencing |
| `$TDSM.VERT` | `bool` | `true`  | Vertical (T to B) frame sequencing   |

## Functions

### `export`

```js
$TDSM.export(style s, string folder, string base_name);
```

Exports the current sprite sheet customized and configured for the sprite style `s`. The exported contents are saved to the directory `folder`.

For some valid `base_name`, this function exports:
* `base_name.png` - Sprite sheet as PNG image
* `base_name.json` - Sprite sheet metadata (if `$TDSM.is_json()`)
* `base_name.stip` - Layer-separated sprite sheet (if `$TDSM.is_stip()`)

**Parameters:**
* [`style`](./style.md) `s` - Sprite style whose current customized and configured sprite is to be exported
* `string` `folder` - The directory where the export contents are to be saved
* `string` `base_name` - The file name of the exported contents, barring file extensions

**Fails** if any of these conditions are not satisfied:
* [`s.has_output()`](./style.md#has_output)
* `folder` is a valid path to an existing and accessible directory in the file system
* `base_name` is a valid file name

### `get_style`

```js
$TDSM.get_style(string code) -> style
```

**Returns** the [`style`](./style.md) matching `code`.

**Parameters:**
* `string` `code` - A unique identifier code associated with a particular sprite style

<!-- TODO - list of supported sprite identifier codes -->

### `is_json`

```js
$TDSM.is_json() -> bool
```

**Returns** `true` if the *Export sprite sheet metadata as JSON* flag is turned on, `false` otherwise.

### `is_stip`

```js
$TDSM.is_stip() -> bool
```

**Returns** `true` if the *Export as Stipple Effect project* flag is turned on, `false` otherwise.

### `set_json`

```js
$TDSM.set_json(bool export_json);
```

Sets the value of the *Export sprite sheet metadata as JSON* flag.

### `set_stip`

```js
$TDSM.set_stip(bool export_stip);
```

Sets the value of the *Export as Stipple Effect project* flag.
