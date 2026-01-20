[***< Contents***](./README.md)

# `script`

| Represents             | Class in *TDSM* source code                                                                                                                                                                                     |
|:-----------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| A compiled `.tds` file | [`com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode`](https://github.com/jbunke/delta-time/blob/master/script/src/com/jordanbunke/delta_time/scripting/ast/nodes/function/HeadFuncNode.java) |

> **Note:**
>
> The specification uses `S` to represent an arbitrary `script` instance in property and function definitions.

## Properties

### *`path`*

```js 
S.path -> string
```

**Description:**

The absolute file path of the source file associated with this script object

## Functions

### `run`

1.  ```js
    S.run(?);
    ```
    
    **Description:**

    Executes the void script `S`.

    **Terminates with error if:**
    * Invoked with arguments that do not match the number or types of parameters specified by `S`

2.  ```js 
    S.run(?) -> ?
    ```
    
    **Returns** a value or object of an unknown type, which is the result of the execution of a value-returning script `S`.

    **Terminates with error if:**
    * Invoked with arguments that do not match the number or types of parameters specified by `S`
