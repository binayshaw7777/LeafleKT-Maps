# Tmux & Git Worktree Commands

Replace `<task-slug>` with the actual task name.

## Git Worktree

```bash
# Create (from HEAD)
git worktree add ../worktrees/<task-slug> -b feature/<task-slug>

# Create (from specific branch)
git worktree add ../worktrees/<task-slug> -b feature/<task-slug> origin/<base>

# List
git worktree list

# Cleanup after merge
git worktree remove ../worktrees/<task-slug>
git branch -d feature/<task-slug>
```

## Tmux — 3 Panes in Same Terminal

**HARD RULE: Split panes only. Never new windows, never new sessions for sub-tasks.**

```bash
# Create session in worktree dir
tmux new-session -d -s "<task-slug>" -c "../worktrees/<task-slug>"

# Split: Worker (top) | bottom
tmux split-window -v -t "<task-slug>" -c "../worktrees/<task-slug>"

# Split bottom: Tester (left) | Reviewer (right)
tmux split-window -h -t "<task-slug>:.1" -c "../worktrees/<task-slug>"

# Label
tmux select-pane -t "<task-slug>:.0" -T "WORKER"
tmux select-pane -t "<task-slug>:.1" -T "TESTER"
tmux select-pane -t "<task-slug>:.2" -T "REVIEWER"
```

```
┌─────────────────────────────┐
│       WORKER (Pane 0)       │
├──────────────┬──────────────┤
│ TESTER (1)   │ REVIEWER (2) │
└──────────────┴──────────────┘
```

## Send to Panes

```bash
tmux send-keys -t "<task-slug>:.0" "<command>" Enter   # Worker
tmux send-keys -t "<task-slug>:.1" "<command>" Enter   # Tester
tmux send-keys -t "<task-slug>:.2" "<command>" Enter   # Reviewer
```

## Read Pane Output

```bash
tmux capture-pane -t "<task-slug>:.0" -p
```
