[***< Theory***](./README.md)

# Scripting overview

This page is an attempt to provide a crash course to the *DeltaScript* language for the purpose of writing scripts -- especially `manifest.tds` files, which define [sprite styles](./t_style.md) -- for *Top Down Sprite Maker*.

<details open>
    <summary><b>Contents</b></summary>

* [Introduction](#introduction)
* [Learning resources](#learning-resources)
  * [Language specification](#language-specification)
  * [Other sprite styles](#other-sprite-styles)
* [Script layout](#script-layout)
* [`manifest.tds`](#manifesttds)
  * [Folder structure](#folder-structure)
  * [Signature](#signature)
* [Invoking a script within a script](#invoking-a-script-within-a-script)
* [Global variables](#global-variables)
* [Quirks of *DeltaScript*](#quirks-of-deltascript)
  * [Collection syntax](#collection-syntax)
  * [Length operator (`#|`)](#length-operator-)
  * [Functional types](#functional-types)
  * [Function references (`::`)](#function-references-)
</details>

## Introduction

*Top Down Sprite Maker* uses [*DeltaScript*](https://github.com/jbunke/deltascript) as its scripting language. I designed *DeltaScript* as an easy to write, highly restrictive, and easily extensible scripting language that could be used in application-specific contexts like this.

This page covers the essential language concepts that should be enough for experienced programmers to get up and running writing scripts for *TDSM*.

## Learning resources

If you want a deeper dive into the language or more practical examples of *DeltaScript* code written for *TDSM*, consider the following.

### Language specification

The [*DeltaScript* language specification](https://github.com/jbunke/deltascript/blob/master/docs/lang-spec.md) is a formal document describing the syntax and semantics of the *DeltaScript* base language.

> **Note:** <!-- TODO -->
>
> As of *Top Down Sprite Maker* v1.2.0, the language specification is outdated. Certain sections do not reflect the [current language implementation](https://github.com/jbunke/delta-time/tree/dev/script), while other implementation changes and additions have not yet been documented.
> 
> Notable inaccuracies include:
> * [`when` statement semantics](https://github.com/jbunke/deltascript/blob/master/docs/ls-5-stat.md#562--when-statements) (*control expression is now only evaluated once before case checks*)
> * [Global variables](#global-variables) (*implemented; not yet documented*)
> 
> Despite these inaccuracies, the specification is still a great learning resource. It will be updated soon to reflect the current language implementation.

### Other sprite styles

For more practical *Top Down Sprite Maker* script examples, consider downloading the freely available [approved sprite styles](https://itch.io/c/5834066/top-down-sprite-maker-approved-sprite-styles) and having a look at their `manifest.tds` files. The *Pokémon* sprite styles make especially effective use of a range of API functions and *DeltaScript* programming techniques.

<!-- TODO - link to sprite style development guide -->

## Script layout

*DeltaScript* scripts consist of a nameless ***header function***, which may be followed by named ***helper functions***. The header function is the entry point of the script's execution.

### Example 1

```js 
() {
    color c = random_color();
    print("The color " + c + " has a lightness of " + 
            percent(lightness(c)) + ".");
}

percent(float f -> string) -> (f * 100) + "%"

lightness(color c -> float) {
    ~ int channel_sum = c.r + c.g + c.b;
    ~ int WHITE = 0xff * 3;
    
    return channel_sum / (float) WHITE;
}

random_color(-> color) {
    int[] channels = new int[3];
    
    for (int i = 0; i < #| channels; i++)
        channels[i] = rand(0, 0x100);
    
    return rgb(channels[0], channels[1], channels[2]);
}
```

**Description:**

Chooses a random RGB color, determines its lightness as a percentage -- where black is 0% and white is 100% -- and prints the color and lightness to the console.

**Header function signature:** no parameters, returns nothing

**Helper functions:**
* `percent`
* `lightness`
* `random_color`

### Example 2

```js 
(bool<> class_attendance -> bool) {
    for (student in class_attendance)
        if (!student) return false;
    
    return true;
}
```

**Description:**

Returns `true` if and only if the class has perfect attendance, i.e. every student is present; `false` otherwise.

**Header function signature:**
* **Parameters:** `class_attendance`
* **Returns:** `bool`

**Helper functions:** none

## `manifest.tds`

`manifest.tds` is a special script file that defines a sprite style that can be loaded into *Top Down Sprite Maker*.

### Folder structure

*Top Down Sprite Maker* sprite styles are distributed as ZIP file archives. `manifest.tds` ***must be at root level*** of the ZIP file archive to be recognized by *TDSM*.

For some sprite style `my-style.zip`:

``` 
my-style.zip
├─── manifest.tds
└─── // additional contents
```

### Signature

The [header function](#script-layout) of `manifest.tds` must:
* return a [`style`](../style.md)
* take no parameters

```js 
(-> style) {
    // code here
}

// optional helper functions
```

The returned `style` should always be created by the `style` constructor: [`$Init::style`](../init.md#style).

## Invoking a script within a script

Besides [`manifest.tds`](#manifesttds), you can write other script files, which can be invoked from within another script.

Assume we have two script files `A` and `B`. `A` is being executed.

We have the following folder structure:

```
D:
├── dir-1
│   ├── subdir-1-1
│   │   └── A.tds
│   └── subdir-1-2
└── dir-2
    └── B.tds
```

To invoke script `B` from `A`:
1. Use the constructor [`$Init::script`](../init.md#script) with the absolute path of `B`, or the relative path from `A` to `B`.
2. Invoke the [`script`](../script.md) object with the function [`script::run`](../script.md#run), either as an expression or statement, depending on whether the script in question returns a value or not.

<details open>
    <summary><b>Example of <code>A.tds</code></b></summary>

```js
() {
    print("Invoking script \"B.tds\"...");
    
    ~ script b = $Init.script("../../dir-2/B.tds"); // alternatively $Init.script("D:/dir-2/B.tds");
    ~ int output = b.run(10, 4);
    print("Output of \"B.tds\": " + output);
}
```
</details>

<details open>
    <summary><b>Example of <code>B.tds</code></b></summary>

```js
// more concisely: (~ int op_a, ~ int op_b -> int) -> op_a + op_b
(~ int op_a, ~ int op_b -> int) {
    return op_a + op_b;
}
```
</details>

## Global variables

> **Note:** <!-- TODO -->
>
> Global variables have been implemented since the language specification was last updated; thus, their semantics are not yet formally described.

Variables declared in the outermost scope of the header function are global and can be referenced in helper functions.

<details>
  <summary><b>Example:</b></summary>

```js 
() {
    int a = 0;
    
    if (flip_coin()) {
        int b = 1;
    }
    
    some_helper();
    
    int c = 2;
}

some_helper() {
    print(a);       // Valid
    print(b);       // Invalid; "b" is not declared in outermost scope of header
    print(c);       // Invalid; "c" is declared after some_helper() is called
}
```
</details>

## Quirks of *DeltaScript*

Though *DeltaScript*'s syntax and conventions were designed to be familiar to programmers of C-style languages (C, C++, Java, C#, etc.) the language has a number of quirks that are worth explaining.

### Collection syntax

*DeltaScript* uses different types of brackets to refer to each type of collection supported by the language.

| Collection type | Brackets |       Example        |           Natural language            |
|:---------------:|:--------:|:--------------------:|:-------------------------------------:|
|      Array      |   `[]`   | `(color -> color)[]` |   Array of color to color functions   |
|      List       |   `<>`   |     `string[]<>`     |       List of arrays of strings       |
|       Set       |   `{}`   |     `col_sel{}`      |        Set of color selections        |
|       Map       |  `{:}`   |   `{int : int[]}`    | Map of integers to arrays of integers |

> **Note:**
> 
> For the differences between collection types and their semantics, please consult [the *DeltaScript* language specification](https://github.com/jbunke/deltascript/blob/master/docs/ls-2-types.md#23--collection-types).

### Length operator (`#|`)

`#|` (hash followed by vertical bar/pipe) is a unary operator in *DeltaScript* for ***length*** or ***size***.

It can be used on expressions of the following types:
* `string`
* array (`T[]`)
* list (`T<>`)
* set (`T{}`)
* map (`{K:V}`)

<details>
  <summary><b>Example:</b></summary>

```js 
print(#|"some string");                           // Prints "11"
print(#| [ 6, 7, 8, 9, 9, 9, 8, 2, 1, 2 ]);       // Prints "10"
```
</details>

### Functional types

*DeltaScript*'s type system supports functional types and function references. Thus, functions can be stored as variables and passed as function arguments.

Functional types are simply a function's type signature. Note that only value-returning functions are valid functional types.

<details>
  <summary><b>Example:</b></summary>

```js
(color[] cs -> color[]<>) {
    color[]<> output_list = <>;
    
    (color -> color)[] algorithms = [
        ::r_only, ::g_only, ::b_only, ::complement
    ];
    
    for (f in algorithms)
        output.add(process_colors(cs, f));
    
    return output_list;
}

r_only(color c -> color) -> rgb(c.r, 0, 0)

g_only(color c -> color) -> rgb(0, c.g, 0)

b_only(color c -> color) -> rgb(0, 0, c.b)

complement(color c -> color) {
    ~ int RGB_MAX = 0xff;
    return rgb(RGB_MAX - c.r, RGB_MAX - c.g, RGB_MAX - c.b);
}

process_colors(color[] cs, (color -> color) f -> color[]) {
    color[] res = new color[#|cs];
    
    for (int i = 0; i < #| cs; i++)
        res[i] = f.call(cs[i]);
    
    return res;
}
```

**Description:**

The second parameter of `::process_colors` expects a functional type with the signature `(color -> color)`, i.e. a color to color function. The script also contains the helper functions `::r_only`,`::g_only`,`::b_only` and `::complement`, all of which are of type `(color -> color)`. These functions are populated into an array in the header function and then iterated over to produce a list of output, where an initial array of colors is transformed by each `(color -> color)` function.
</details>

### Function references (`::`)

> **Note:** <!-- TODO -->
> 
> As of *Top Down Sprite Maker* v1.2.0, function references are only supported for value-returning helper functions. API functions, *DeltaScript* global functions and type member functions cannot be referenced, though this will likely change in a future language version and then be implemented in the interpreter with a *TDSM* update to follow.

Value-returning helper functions can be referenced by prepending the scope resolution operator (`::`) to their function name. The resulting expression will be of a functional type corresponding to the helper function's signature. Note that, partially for this reason, helper functions should all have unique names.

<details>
  <summary><b>Example:</b></summary>

Take the following helper function:

```js 
unique_chars(string s -> int) {
    ~ char{} char_set = {};
    
    for (c in s)
        char_set.add(c);
    
    return #| char_set;
}
```

The expression `::unique_chars` is a reference to this helper function and has the type `(string -> int)`.
</details>

---

###  See Also

* [Help](./help.md)
