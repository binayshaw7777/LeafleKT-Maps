# Krid Rules

## Product Rules
- LeafleKT is a Compose-first library for rendering maps with all features of LeafletJS (its a wrapper).
- Sample app screens should expose customization controls so the sdk/library can be tuned without code edits.

## Engineering Rules
- `:LeafleKT` is a library module and must stay reusable.
- `:app` is a sample/demo app and may depend on `:LeafleKT`, but not the other way around.
- Prefer immutable config objects and small composables over huge parameter lists where possible.
- Add logs only where they help debug layout/config/state transitions. Avoid noisy logs during normal rendering.
- Prefer safe defaults and deterministic sample data.
- Avoid unnecessary external dependencies; justify each one.
- Use Compose best practices: state hoisting, stable models, previews, and clear separation between data shaping and rendering.

## Collaboration Rules
- Check `.agents/PLAN_CHECKLIST.md` before starting work to avoid overlap.
- Record major assumptions in `Plan.md`.
- If parallel agents are used later, assign disjoint ownership by file/module.
- Do not rewrite unrelated user changes.
- Never open or print populated local secret files such as `gradle.publish.properties`, `publish.properties`, `secrets.properties`, or real `.env` files. Only verify their existence or use them indirectly through tooling.

## Quality Rules
- Build before marking a task done when code changed.
- Add tests for layout/data transformation logic when behavior is non-trivial.
- Keep library naming, package structure, and Gradle setup ready for later publishing.
