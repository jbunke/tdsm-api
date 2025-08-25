[***< Theory***](./README.md)

# Sprite ID

**Sprite IDs** are used to distinguish particular sprites within a sprite sheet.

They are strings that take the form `"<direction>-<animation-id>-<animation-frame>"`.

For example:
* `"n-walk-0"`
* `"s-run-2"`

> **Note:**
> 
> Because sprite IDs are formatted with hyphens (-), animation IDs should ***NEVER*** include hyphens, as this will break the sprite ID component extraction functions in [`$Util`](../util.md).
