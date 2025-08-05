[***< Contents***](./README.md)

# `$Util`

The `$Util` namespace contains miscellaneous and utility functions.

## Functions

<!-- TODO - full documentation -->

### `extract_anim_id`

```js 
$Util.extract_anim_id(string sprite_id) -> string
```

**Returns** <!-- TODO -->

**Parameters**:
* `sprite_id` - A string consisting of a direction, animation ID, and animation frame number, separated by hyphens (e.g. `"nw-walk-2"`).

**Terminates with error if**:
*

### `extract_direction`

```js 
$Util.extract_direction(string sprite_id) -> string
```

**Returns** <!-- TODO -->

**Parameters**:
* `sprite_id` - A string consisting of a direction, animation ID, and animation frame number, separated by hyphens (e.g. `"nw-walk-2"`).

**Terminates with error if**:
*

### `extract_frame`

```js 
$Util.extract_frame(string sprite_id) -> int
```

**Returns** <!-- TODO -->

**Parameters**:
* `sprite_id` - A string consisting of a direction, animation ID, and animation frame number, separated by hyphens (e.g. `"nw-walk-2"`).

**Terminates with error if**:
*

### `sync_choices`

1.  ```js 
    $Util.sync_choices(layer a, layer b);
    ```
    
    **Returns** <!-- TODO -->

    **Parameters**:
    * `a` -
    * `b` -
    
    **Fails if**:
    * 

2.  ```js 
    $Util.sync_choices(layer[] layers);
    ```

    **Returns** <!-- TODO -->

    **Parameters**:
    * `layers` -

    **Fails if**:
    * 
