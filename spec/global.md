[***< Contents***](./README.md)

# Global namespace (`$TDSM`)

The global namespace is essentially a utility class containing **constants** and **functions not bound to an object**.

## Constants

> **Note:**
> 
> Constants are bound to primitive values of type `bool`, `int`, or `string`. These **can** be avoided and replaced where appropriate by the corresponding literal, but **should** be used to make scripts more **readable** and **maintainable**, in case constant values are reassigned in future updates.

### Layer types

| Constant            | Type  | Value | Represents            |
|:--------------------|:-----:|:-----:|:----------------------|
| `$TDSM.ACL`         | `int` |  `0`  | Asset choice layer    |
| `$TDSM.COL_SEL_L`   | `int` |  `1`  | Color selection layer |
| `$TDSM.DECISION_L`  | `int` |  `2`  | Decision layer        |
| `$TDSM.MATH_L`      | `int` |  `3`  | Math layer            |
| `$TDSM.CHOICE_L`    | `int` |  `4`  | Choice layer          |
| `$TDSM.DEPENDENT_L` | `int` |  `5`  | Dependent layer       |
| `$TDSM.OTHER_L`     | `int` | `-1`  | Other types of layers |

<!-- TODO - other layer types: group, mask -->

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

| Constant       | Type  | Value | Represents                                                |
|:---------------|:-----:|:-----:|:----------------------------------------------------------|
| `$TDSM.X`      | `int` |  `0`  | The X coordinate of an X,Y coordinate pair, and/or width  |
| `$TDSM.Y`      | `int` |  `1`  | The Y coordinate of an X,Y coordinate pair, and/or height |
| `$TDSM.LEFT`   | `int` |  `0`  | The left edge of a sprite/image                           |
| `$TDSM.RIGHT`  | `int` |  `1`  | The right edge of a sprite/image                          |
| `$TDSM.TOP`    | `int` |  `2`  | The top edge of a sprite/image                            |
| `$TDSM.BOTTOM` | `int` |  `3`  | The bottom edge of a sprite/image                         |

### Orientation

| Constant     |  Type  |  Value  | Represents                           |
|:-------------|:------:|:-------:|:-------------------------------------|
| `$TDSM.HORZ` | `bool` | `true`  | Horizontal (L to R) frame sequencing |
| `$TDSM.VERT` | `bool` | `false` | Vertical (T to B) frame sequencing   |

## Functions

### `export`

```js
$TDSM.export(style s, string folder, string base_name);
```

**Description**:

Exports the current sprite sheet customized and configured for the sprite style `s`. The exported contents are saved to the directory `folder`.

For some valid `base_name`, this function exports:
* `base_name.png` - Sprite sheet as PNG image
* `base_name.json` - Sprite sheet metadata (if [`$TDSM.is_json()`](#is_json))
* `base_name.stip` - Layer-separated sprite sheet (if [`$TDSM.is_stip()`](#is_stip))

**Parameters:**
* `s` - Sprite style whose current customized and configured sprite is to be exported
* `folder` - The directory where the export contents are to be saved
* `base_name` - The file name of the exported contents, excluding file extensions

**Fails if**:
* [`!s.has_output()`](./style.md#has_output)
* `folder` is an invalid file path
* `folder` does not exist, is not a directory, or is inaccessible
* `base_name` is not a valid file name

### `get_style`

```js
$TDSM.get_style(string id) -> style
```

**Returns** the [`style`](./style.md) matching `id`.

**Parameters:**
* `id` - A unique identifier code associated with a particular sprite style

**Terminates with error if**:
* Program instance contains no sprite style with the ID `id`

### `is_json`

```js
$TDSM.is_json() -> bool
```

**Returns** `true` if the *Export sprite sheet metadata as JSON* flag is turned on; `false` otherwise. When turned on, exporting (see [`$TDSM::export`](#export)) will save the sprite sheet metadata as a JSON file in addition to the PNG sprite sheet.

### `is_stip`

```js
$TDSM.is_stip() -> bool
```

**Returns** `true` if the *Export as Stipple Effect project* flag is turned on; `false` otherwise. When turned on, exporting (see [`$TDSM::export`](#export)) will save a version of the sprite sheet with each assembly layer separated as a [*Stipple Effect*](https://stipple-effect.github.io) project file (`.stip`), in addition to the PNG sprite sheet.

### `load_from_json`

```js
$TDSM.load_from_json(string json);
```

**Description**:

Attempts to set the customization and configuration data of a particular sprite style based on `json`.

**Parameters:**
* `json` - The JSON-formatted text contents corresponding to a previous export operation.

> **Note:**
> 
> `json` should be of the form:
>   ```json
>   {
>       "style_id": /* style id */,
>       "customization": { /* ... */ },
>       "config": {
>           "directions": [ /* ... */ ], 
>           "animations": { /* ... */ },
>           "padding": { /* ... */ }, 
>           "layout": { /* ... */ }
>       },
>       "size": { /* ... */ },
>       "frames": [ /* ... */ ]
>   }
>   ```
> 
> `json` will typically be derived by reading the contents of a file with the `read_file(string path) -> string`<!-- TODO - 1) update language specification, 2) rewrite with qualified name ::read_file and 3) link --> function from the *DeltaScript* standard library.

**Fails if**:
* `json` cannot be parsed as a JSON file
* Program instance contains no sprite style with the ID specified by `json`

> **Note**:
> 
> Individual load operations may fail due to discrepancies between `json` and the actual definition of the sprite style it is targeting (e.g. attempting to set the selection of an asset choice layer to the code `"tunic"` when the only choice codes are `["shirt", "vest", "sweater"]`).

### `set_json`

```js
$TDSM.set_json(bool export_json);
```

**Description**:

Sets the value of the *Export sprite sheet metadata as JSON* flag.

**Parameters**:
* `export_json` - Whether to export JSON metadata along with the sprite sheet

### `set_stip`

```js
$TDSM.set_stip(bool export_stip);
```

**Description**:

Sets the value of the *Export as Stipple Effect project* flag.

**Parameters**:
* `export_stip` - Whether to export a layer-separated *Stipple Effect* project version of the sprite sheet along with the PNG version

### `upload_style`

```js 
$TDSM.upload_style(string archive_path);
```

**Description**:

Upload the sprite style archive specified by the file path `archive_path` to the program instance.

> **Note**:
>
> This function should **only** be invoked when running the *TDSM* **command-line interface**, not while running the program with its graphical user interface (via `manifest.tds` in a sprite style archive).

**Parameters**:
* `archive_path` - The file path of the sprite style archive to be uploaded. This should correspond to a ZIP archive (though its extension needn't be `.zip`) that contains a file `manifest.tds` in its **root** (outermost scope, not within a subdirectory inside the archive).

**Fails if**:
* `archive_path` doesn't exist, isn't a file, or isn't accessible
* Archive doesn't contain the file `manifest.tds` in its root
* `manifest.tds` contains a syntax error and cannot be interpreted as valid *DeltaScript* code
* Execution of `manifest.tds` does not return a [`style`](./style.md)
* Execution of `manifest.tds` terminates prematurely with an error
