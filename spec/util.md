[***< Contents***](./README.md)

# `$Util`

The `$Util` namespace contains miscellaneous and utility functions.

## Functions

### `extract_anim_id`

```js 
$Util.extract_anim_id(string sprite_id) -> string
```

**Returns** the animation ID component of `sprite_id`, or an empty string if `sprite_id` is improperly formatted.

**Parameters**:
* `sprite_id` - A string consisting of a direction, animation ID, and animation frame number, separated by hyphens (e.g. `"nw-walk-2"`).

### `extract_direction`

```js 
$Util.extract_direction(string sprite_id) -> string
```

**Returns** the direction component of `sprite_id`, or `"invalid"` if `sprite_id` is improperly formatted.

**Parameters**:
* `sprite_id` - A string consisting of a direction, animation ID, and animation frame number, separated by hyphens (e.g. `"nw-walk-2"`).

### `extract_frame`

```js 
$Util.extract_frame(string sprite_id) -> int
```

**Returns** the 0-based frame number component of `sprite-id`.

**Parameters**:
* `sprite_id` - A string consisting of a direction, animation ID, and animation frame number, separated by hyphens (e.g. `"nw-walk-2"`).

**Terminates with error if**:
* Frame number `sprite_id` component cannot be parsed as an integer

### `sync_choices`

1.  ```js 
    $Util.sync_choices(layer a, layer b);
    ```
    
    **Description**:

    Syncs the asset choices of two asset choice layers `a` and `b`. That way, when the asset choice of either layer is updated, the remaining layer is updated automatically to match the choice ID.

    **Parameters**:
    * `a` - First of two asset choice layers to be synced
    * `b` - Second of two asset choice layers to be synced
    
    **Fails if**:
    * `a == b`
    * `a.type != $TDSM.ACL || b.type != $TDSM.ACL`

2.  ```js 
    $Util.sync_choices(layer[] layers);
    ```

    **Description**:

    Syncs the asset choices for all the asset choice layers in `layers`. That way, when a selection is made in any asset choice layer, the remaining synced layers are automatically updated to match the choice ID.

    **Parameters**:
    * `layers` - Array of asset choice layers to sync

    > **Related material**:
    > * [`layer::choose`](./layer.md#choose)
