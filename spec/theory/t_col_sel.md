[***< Theory***](./README.md)

# Color selections

> For the API type page, see [`col_sel`](../col_sel.md). For the constructor, see [`$Init::col_sel`](../init.md#col_sel).

**Color selections** are used to assign colors to customizable color components, such as skin color, hair color, or the color of an item of clothing.

## Swatches

Color selections can provide a pre-determined set of colors -- **swatches** -- to choose from. A color selection should provide no more than 12 swatches.

### Any color?

Color selections can be defined as permitting any color. Such color selections can be assigned any RGB color. If not, they are limited to their swatches.

![](./assets/any-color.gif)

### Default swatches

Providing no swatches will automatically populate a color selection with *TDSM*'s default swatches.

These are:

* [Black (`#000000`)](https://www.colorhexa.com/000000)
* [White (`#ffffff`)](https://www.colorhexa.com/ffffff)
* [Grey (`#808080`)](https://www.colorhexa.com/808080)
* [Red (`#e02020`)](https://www.colorhexa.com/e02020)
* [Green (`#20e020`)](https://www.colorhexa.com/20e020)
* [Blue (`#2020e0`)](https://www.colorhexa.com/2020e0)
* [Orange (`#c06000`)](https://www.colorhexa.com/c06000)
* [Brown (`#603010`)](https://www.colorhexa.com/603010)
* [Yellow (`#e0c000`)](https://www.colorhexa.com/e0c000)
* [Purple (`#b000b0`)](https://www.colorhexa.com/b000b0)
* [Pink (`#ff80c0`)](https://www.colorhexa.com/ff80c0)
* [Cyan (`#00b0b0`)](https://www.colorhexa.com/00b0b0)
