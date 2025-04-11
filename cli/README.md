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

## Commands

The following is an exhaustive list of the commands that can be run in the *TDSM* CLI.

### `help`

```shell
help                          # Basic; lists commands and help categories
help <category>               # Specific; explains topic in detail
```

**Description:**

Prints a help guide.

`<category>` options:
* `--code` - Information related to *DeltaScript* code
* `--settings` - Information related to settings

### `check`

```shell
check <setting>
```

**Description:**

Prints the value of `<setting>`

**See:**
* [Settings](#settings)

### `def`

```shell
def <func>
```

**Description:**

Defines a *DeltaScript* function `<func>`. The function can then be invoked.

**See:**
* [Functions in the *DeltaScript* language specification](https://github.com/jbunke/deltascript/blob/master/docs/ls-6-func.md)

### `eval`

```shell
eval <expr>
```

**Description:**

Evaluates a *DeltaScript* expression `<expr>` and prints its value.

**See:**
* [Expressions in the *DeltaScript* language specification](https://github.com/jbunke/deltascript/blob/master/docs/ls-4-expr.md)

### `quit`

```shell
quit
```

**Description:**

Closes the command-line interface

### `reset`

```shell
reset
```

**Description:**

Resets the *DeltaScript* symbol table, wiping all functions and variables. This action is irreversible.

### `run`

```shell
run <path>
```

**Description:**

Attempts to interpret and run the file at `<path>`. The file's contents must be a valid [*DeltaScript* script](../spec/README.md).

### `script`

```shell
script <path>
```

**Description:**

Runs the file at `<path>` as a shell script.

### `set`

```shell
set <setting> <value>
```

**Description:**

Sets `<setting>` to `<value>` if `<value>` is valid for `<setting>`

**See:**
* [Settings](#settings)

### *Execute statement*

```shell
<stat>
```

**Description:**

Executes a *DeltaScript* statement `<stat>`.

**See:**
* [Statements in the *DeltaScript* language specification](https://github.com/jbunke/deltascript/blob/master/docs/ls-5-stat.md)

## Settings

The following settings can be used as arguments for the [`check`](#check) and [`set`](#set) commands.

| Setting |  Type  |                Description                |
|:-------:|:------:|:-----------------------------------------:|
| `ansi`  | `bool` | Whether CLI has text styling and coloring |

|  Type  | `<value>` options |
|:------:|:-----------------:|
| `bool` |  `true`, `false`  |

### Special `<value>` options
* `default` - Default value of the setting
