# Prompt Templates

Fill in `<placeholders>`. Send completed prompt to the corresponding pane.

---

## Worker Prompt (Pane 0)

```
Follow the spec-kit workflow to implement this task:
1. Run /speckit.specify with the task description below
2. Run /speckit.plan with the constraints below
3. Run /speckit.tasks to break down the work
4. Run /speckit.implement to execute

## Task
<what to build — clear, one paragraph>

## Acceptance Criteria
- <testable criterion 1>
- <testable criterion 2>

## Constraints
- Follow existing architecture: <pattern>
- DI: <framework>
- Target directory: <path>
- Clean code: small functions, meaningful names, SRP
- Testability: inject dependencies, no static state
- UI tasks: use framelink MCP for implementation reference

## Context
- Worktree: ../worktrees/<task-slug>
- Branch: feature/<task-slug>
- Key files: <2-5 files to read first>

Use multiple subagents and parallel execution wherever possible to move fast.
Stage only. Do NOT commit.
```

---

## Tester Prompt (Pane 1)

```
Use /phone-driver to test this implementation:

## What Was Built
<1-2 sentence summary>

## Scenarios
1. Happy path: <main flow>
2. Edge case: <boundary>
3. Error state: <failure handling>
4. UI/UX: <visual check>

## Steps
- Build: ./gradlew assembleDebug
- Use /phone-driver to navigate to <screen>
- Execute each scenario, capture screenshots

## Output → TEST_REPORT.md
| # | Scenario | Status | Notes |
|---|----------|--------|-------|

For failures: steps to reproduce, expected vs actual, screenshots.
Save to: ../worktrees/<task-slug>/TEST_REPORT.md
Use subagents to run independent test scenarios in parallel where possible.
```

---

## Reviewer Prompt (Pane 2)

```
Review changes using /clean-code principles:

## Scope
- Branch: feature/<task-slug>
- Files: run `git diff --name-only`
- Feature: <one line summary>

## Check
- Names reveal intent, functions do one thing (5-20 lines)
- No duplication, no magic numbers, proper error handling
- Architecture conventions followed, deps injected
- No main thread blocking, no memory leaks, lifecycle-safe
- Scales to 10x data, forward-compatible contracts

## Output → REVIEW_REPORT.md
Rating: 🟢 Ship It / 🟡 Minor Changes / 🔴 Rework
Issues table: severity, file, issue, suggestion
Save to: ../worktrees/<task-slug>/REVIEW_REPORT.md
Use subagents to review different files in parallel where possible.
```

---

## Fix Prompt (Worker ← Failures)

```
Use /speckit.plan and /speckit.implement to fix these issues:

## Issues
1. <from TEST_REPORT.md or REVIEW_REPORT.md>

## Requirements
- Fix each issue
- No regressions on passing tests
- Use subagents to fix independent issues in parallel
- Stage only. Do NOT commit.
```
