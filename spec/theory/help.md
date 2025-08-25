[***< Theory***](./README.md)

# Help

This page explains the format, conventions, and terminology used throughout the API specification.

<details open>
    <summary><b>Contents</b></summary>

* [Constants, properties and functions](#constants-properties-and-functions)
  * [Constants](#constants)
  * [Properties](#properties)
  * [Functions](#functions)
* [Namespaces and types](#namespaces-and-types)
  * [Namespaces](#namespaces)
  * [Types](#types)
* [Definition syntax and conventions](#definition-syntax-and-conventions)
  * [Definition syntax](#definition-syntax)
  * [Failure and termination conditions](#failure-and-termination-conditions)
  * [Function links](#function-links)
</details>

## Constants, properties and functions

Namespace and type documentation pages will include the subheadings ***Constants***, ***Properties*** and/or ***Functions***.

### Constants

**Constants** are immutable values that are bound to a namespace.

### Properties

**Properties** are immutable values that are bound to an object/instance of a particular type.

### Functions

**Functions** defined by the API specification can be member functions of a particular type and be invoked on objects/instances of that type, or be member functions of a namespace.

There are two types of functions: ***void functions*** and ***value-returning functions***. As the name suggests, value-returning functions return a value, whereas void functions return nothing, but usually alter the program state.

## Namespaces and types

### Namespaces

**Namespaces** are containers for functions and/or constants of a particular behavioural niche. For example, the [`$Init`](../init.md) namespace defines constructor functions for the initialization of various types of objects. Namespaces are indicated by an initial dollar sign `$`.

### Types

[*DeltaScript*](https://github.com/jbunke/deltascript) has a fixed type system. Language extensions -- like this API -- may define additional types, but users cannot define new types via the creation of classes or something like that.

This API defines several new types to represent data types essential to *TDSM*, such as [`style`](../style.md) (sprite style) and [`col_sel`](../col_sel.md) (color selection). The interpreter maps these types directly to their implementing Java class in the *TDSM* source code or in a dependency. Implementing classes are linked in the table at the top of each type page.

## Definition syntax and conventions

Definition entries on namespace and type pages are grouped into categories (***Constants***, ***Properties***, ***Functions***) and listed alphabetically within those categories.

### Definition syntax

Property and function definitions include a code block that is meant to act as a formal definition of the property or function's type signature.

For properties and member functions of a particular type, definitions use an ***object placeholder*** to act as a stand-in for an arbitrary object/instance of that type that the property or function being defined is invoked upon. The object placeholder is specified in a note near the top of each type page. It is usually the capitalized first letter of the name of the type.

**Property definition format:**

```js 
<object-placeholder>.<property-name> -> <property-type>
```

**Property definition example:**

```js 
L.id -> string
```

[*[ Source ]*](../layer.md#id)

The syntax of function definitions varies whether functions belong to a namespace or type, and whether they are void or value-returning.

<details>
    <summary><b>Important:</b> API specification vs. <em>DeltaScript</em> declaration</summary>

> Function definition syntax in this API specification differs from actual *DeltaScript* function declaration syntax:
> 
> In this specification, parenthesis are closed *around a function's parameters*. The return arrow `->` and return type are outside the parenthesis. This convention is meant to highlight *function invocation*.
> 
> Conversely, value-returning functions in *DeltaScript* are declared with the return arrow and return type inside the same set of parenthesis as the function's parameters.
> 
> **Declaration examples:**
> ```js 
> // Two parameters: a, b
> sum(int a, int b -> int) {
>     return a + b;
> }
> 
> // No parameters
> random_digit(-> int) {
>     return rand(0, 10);
> }
> 
> // Shorthand style
> sum(int a, int b -> int) -> a + b
> random_digit(-> int) -> rand(0, 10)
> ```
</details>

**Void type member function format:**

```js 
<object-placeholder>.<function-name>(<params>?);
```

**Void type member function example:**

```js 
L.add_dependent(layer dependent);
```

[*[ Source ]*](../layer.md#add_dependent)

**Value-returning type member function format:**

```js 
<object-placeholder>.<function-name>(<params>?) -> <return-type>
```

**Value-returning type member function example:**

```js
S.has_layer(string id) -> bool
```

[*[ Source ]*](../style.md#has_layer)

**Void namespace member function format:**

```js 
$<namespace-name>.<function-name>(<params>?);
```

**Void namespace member function example:**

```js
$TDSM.load_from_json(string json);
```

[*[ Source ]*](../global.md#load_from_json)

**Value-returning namespace member function format:**

```js 
$<namespace-name>.<function-name>(<params>?) -> <return-type>
```

**Value-returning namespace member function example:**

```js 
$Init.col_sel_layer(string id, col_sel[] selections) -> layer
```

[*[ Source ]*](../init.md#col_sel_layer)

### Failure and termination conditions

*DeltaScript* does not support error handling; execution that encounters an error will invariably be suspended. Many function definitions include a section with one of the following headings:
* **Fails if:**
* **Terminates with error if:**

These headings apply to functions that whose parameters and/or receiver have to avoid specified conditions to produce a result or behave as intended. Rather than throwing an error, *void API functions* will simply ***fail***, and it will be as though the program execution simply skipped over the statement. On the other hand, *value-returning API functions* whose conditions are met will ***throw an error and execution will be suspended***.

A function will fail or terminate if **any** of its failure/termination conditions is met. Where possible, these are formulated as boolean expressions, and can usually be reformulated to ensure correct behaviour.

**Example:**

```js
L.min_value() -> int
```

[*[ Source ]*](../layer.md#min_value)

**Termination with error if:** `L.type != $TDSM.MATH_L`

**Safe access:**

```js 
layer_value_range(layer ml -> int) {
    if (ml.type == $MTDSM.MATH_L)
        return ml.max_value() - ml.min_value();
    else
        return 0;
}
```

### Function links

Links to function definitions can be found throughout the API specification. These are usually rendered `in a monospace font` and follow the pattern `$<namespace-name>::<function-name>` or `<type>::<function-name>`. The scope resolution operator `::` is used as a convention to disambiguate functions in distinct scopes with the same name, such as [`layer::randomize`](../layer.md#randomize), [`col_sel::randomize`](../col_sel.md#randomize) and [`style::randomize`](../style.md#randomize).

---

###  See Also

* [Scripting overview](./t_scripting.md)
