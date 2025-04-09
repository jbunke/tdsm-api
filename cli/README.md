# *Top Down Sprite Maker* | CLI Specification

This is the specification of the **command-line interface** (CLI) of *Top Down Sprite Maker*, which users can run to interact with the program's [API](../spec/README.md).

## Format and Syntax

### `<>`

Chevrons `<>` surrounding a keyword in a command definition indicate a placeholder that must be substituted. Once substituted, concrete commands should not include chevrons.

**Example:**

```shell
# Definition
set <setting> <value>
# Substitution
set ansi true
```

### Multi-line commands

To extend a command to the next line, terminate the line with an underscore (`_`).

**Example:**

```shell
def random_color(-> color) {_
    ~ int MAX = 0x100;_
    return rgb(rand(0, MAX), rand(0, MAX), rand(0, MAX));_
}
```

These four lines will be processed as a single command, because each line except the last line terminates with `_`.

<!-- TODO -->

<!-- TODO - multi-line commands end with _ -->

## Commands

<!-- TODO -->

### `help`

```shell
help                          # Basic; lists commands and help categories
help <category>               # Specific; explains topic in detail
```

<!-- TODO -->

`<category>` options:
* `--here` - Description

### `check`

```shell
get <setting>
```

**Description:** Prints the value of `<setting>`

**See:**
* [Settings](#settings)

### `def`

```shell
def <func>
```

<!-- TODO -->

### `eval`

```shell
eval <expr>
```

<!-- TODO -->

### `reset`

```shell
reset
```

<!-- TODO -->

### `run`

```shell
run <script_path>
```

<!-- TODO -->

### `set`

```shell
set <setting> <value>
```

**Description:** Sets `<setting>` to `<value>` if `<value>` is valid for `<setting>`

**See:**
* [Settings](#settings)

### *Execute statement*

```shell
<stat>
```

<!-- TODO -->

## Settings

The following settings can be used as arguments for the [`check`](#check) and [`set`](#set) commands.

| Setting |  Type  |                Description                |
|:-------:|:------:|:-----------------------------------------:|
| `ansi`  | `bool` | Whether CLI has text styling and coloring |

|  Type  | `<value>` options |
|:------:|:-----------------:|
| `bool` |  `true`, `false`  |
