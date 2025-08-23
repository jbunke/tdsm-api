[***< Theory***](./README.md)

# Layers

> For the API type page, see [`layer`](../layer.md).

**Layers**, along with [animations](./t_anim.md) and [directions](./t_dir.md), are the fundamental building blocks of a sprite style in *Top Down Sprite Maker*. Sprite styles are designed to compose layers in a paper doll system to generate sprites.

<details open>
    <summary><b>Contents</b></summary>

* [Types of layers](#types-of-layers)
  * [Asset choice layer](#asset-choice-layer)
  * [Asset layer](#asset-layer)
  * [Color selection layer](#color-selection-layer)
  * [Composed layer](#composed-layer)
  * [Decision layer](#decision-layer)
  * [Dependent layer](#dependent-layer)
  * [Group layer](#group-layer)
  * [Mask layer](#mask-layer)
  * [Math layer](#math-layer)
* [Assembly layers vs. customization layers](#assembly-layers-vs-customization-layers)
  * [Assembly layers](#assembly-layers)
  * [Customization layers](#customization-layers)
</details>

<!-- TODO -->

## Types of layers

There are several **types of layers**. Each type is distinct in its functionality and uses.

A layer type is either *trivial* or *non-trivial*. Trivial layers do not have customizable or adjustable values, whereas non-trivial layers do.

Similarly, a layer type is either *rendered* or *non-rendered*. Rendered layers produce an image and can be defined as assembly layers as part of the sprite generation pipeline, while non-rendered layers cannot.

### Asset choice layer

| Assembly eligibility | Customization eligibility |
|:--------------------:|:-------------------------:|
|    Yes (rendered)    |     Yes (non-trivial)     |

**Constructor:** [`$Init::asset_choice_layer`](../init.md#asset_choice_layer)

![](./assets/asset-choice-layer.png)

### Asset layer

| Assembly eligibility | Customization eligibility |
|:--------------------:|:-------------------------:|
|    Yes (rendered)    |       No (trivial)        |

**Constructor:** [`$Init::asset_layer`](../init.md#asset_layer)

### Choice layer

| Assembly eligibility | Customization eligibility |
|:--------------------:|:-------------------------:|
|  No (non-rendered)   |     Yes (non-trivial)     |

**Constructor:** [`$Init::choice_layer`](../init.md#choice_layer)

![](./assets/choice-layer.png)

### Color selection layer

| Assembly eligibility | Customization eligibility |
|:--------------------:|:-------------------------:|
|  No (non-rendered)   |     Yes (non-trivial)     |

**Constructor:** [`$Init::col_sel_layer`](../init.md#col_sel_layer)

![](./assets/col-sel-layer.png)

### Composed layer

| Assembly eligibility | Customization eligibility |
|:--------------------:|:-------------------------:|
|    Yes (rendered)    |       No (trivial)        |

**Constructor:** [`$Init::composed_layer`](../init.md#composed_layer)

### Decision layer

| Assembly eligibility | Customization eligibility |
|:--------------------:|:-------------------------:|
| *Depends on output*  |    *Depends on output*    |

**Constructor:** [`$Init::decision_layer`](../init.md#decision_layer)

### Dependent layer

| Assembly eligibility | Customization eligibility |
|:--------------------:|:-------------------------:|
|    Yes (rendered)    |       No (trivial)        |

**Constructor:** [`$Init::dependent_layer`](../init.md#dependent_layer)

### Group layer

| Assembly eligibility | Customization eligibility |
|:--------------------:|:-------------------------:|
| *Depends on output*  |    *Depends on output*    |

**Constructor:** [`$Init::group_layer`](../init.md#group_layer)

### Mask layer

| Assembly eligibility | Customization eligibility |
|:--------------------:|:-------------------------:|
|  No (non-rendered)   |       No (trivial)        |

**Constructor:** [`$Init::mask_layer`](../init.md#mask_layer)

### Math layer

| Assembly eligibility | Customization eligibility |
|:--------------------:|:-------------------------:|
|  No (non-rendered)   |     Yes (non-trivial)     |

**Constructor:** [`$Init::math_layer`](../init.md#math_layer)

![](./assets/math-layer.png)

## Assembly layers vs. customization layers

Layers in *TDSM* have two purposes: [***assembly***](#assembly-layers) and [***customization***](#customization-layers). Layers that have a visual component and constitute a part of the sprite composition are known as *assembly layers*, while layers that have a choice for users to make are known as *customization layers*. These categories are not mutually exclusive. Certain layer types, such as [*asset choice layers*](#asset-choice-layer), for example, can be both assembly and customization layers.

### Assembly layers

### Customization layers

![](./assets/customization-layers.png)
