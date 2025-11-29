[***< Theory***](./README.md)

# Asset choices

> For the API type page, see [`asset_choice`](../asset_choice.md). For the constructor, see [`$Init::asset_choice`](../init.md#asset_choice).

An **asset choice** is one of an [asset choice layer](./t_layer.md#asset-choice-layer)'s selection options.

## Asset choice templates

When an asset choice (see [`asset_choice`](../asset_choice.md)) is defined via [`$Init::asset_choice`](../init.md#asset_choice), it has not yet been associated with an asset choice layer. In this state, it is known as an **asset choice template**.

Asset choice templates consist of the asset choice ID, [color replacement](./t_replacement.md) function, and an array of influencing [color selections](./t_col_sel.md).

## Realization

The process by which an asset choice template is associated with an asset choice layer and becomes a full-fledged asset choice is known as **realization**.

When an asset choice template is **realized**, it is passed a reference to its parent asset choice layer. Using the layer's asset fetcher function and the asset choice ID from the template, the asset choice then retrieves its image asset.

## Implementation

The `asset_choice` type represents immutable asset choice objects. It can represent both asset choice templates and realized asset choices. When an `asset_choice` object is created with `$Init::asset_choice`, it is an **asset choice template**. Even after this object is passed to `$Init:asset_choice_layer` as one of its asset choices, the object remains an asset choice template.
 
<!-- TODO -->
