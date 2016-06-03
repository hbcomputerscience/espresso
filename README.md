## Abandoned as of June 2nd 2016

We did the project, we got a good grade, and while we are convinced in the real-world fesability of the project, we accept that there are superior and easier to use alternatives in the open source world. 

If you have any questions or would like us to write some actual documentation, please open a github issue to [nilesr/espresso](https://github.com/nilesr/espresso)

## Programming Guidelines:

- Avoid accessing arrays by index. This is only acceptable if there is no other (clean) way to implement a feature, and if you can be absolutely certain that an array always has an element at that index.
- Functions may not return null or “special values“ to indicate failure. Use the Maybe class.
- Heterogenous lists and direct uses of the Object class are strictly forbidden.
- Functions that interact with the outside world should have their return values encapsulated in some type of functor.
- Variables may not be null or uninitialized at any time.

These rules boil down to the following:
- Keep type safety.
- Avoid depending on the result of IO actions.
- Avoid code that could in any way cause runtime errors.
