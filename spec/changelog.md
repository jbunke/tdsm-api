[***< Contents***](./README.md)

# *Top Down Sprite Maker* | API Changelog

> **Note:**
> 
> Release versions refer to the version of *Top Down Sprite Maker* where the listed API changes were introduced. The API **does not** have a separate versioning system.

## v1.3.0

*Released 2026-01-19* <!-- TODO -->

### Added:

* `script` type
  * Constructor: `$Init::script`
* `asset_choice` properties and functions
  * Property `id`
  * `asset_choice::get_col_sels`
  * `asset_choice::randomize`
* `layer` functions
  * `layer::get_asset_choice`
  * `layer::get_asset_choice_at`
* `style` functions
  * `style::reset_custom`

### Fixed:

* [#75](https://github.com/jbunke/tdsm/issues/75) - `col_sel::randomize` invocation throws compile error
* [#78](https://github.com/jbunke/tdsm/issues/78) - `col_sel::set_from_swatch` invocation leads to runtime error

## v1.2.0

*Released 2025-07-16*

### Added:

* Introduced scripting API

---

###  See Also

* [Full *Top Down Sprite Maker* changelog](https://github.com/jbunke/tdsm/blob/master/res/text/changelog.txt)
