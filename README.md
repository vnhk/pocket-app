# pocket-app

Personal note/item storage system. Content is organized into named "pockets" (containers), with individual items supporting optional AES encryption, WYSIWYG editing, and full change history.

## Features

- **Pockets**: Named containers (unique per user) for grouping related items
- **Items**: Rich WYSIWYG content (up to 5 MB), ordered within a pocket
- **Encryption**: Per-item optional AES encryption using the user's cipher password
- **History**: Full audit trail on both pockets and items via `@HistorySupported`
- **Multi-tenancy**: Users see only their own pockets
- **Cascading deletes**: Deleting a pocket soft-deletes all its items

## Key Entities

| Entity | Description |
|--------|-------------|
| `Pocket` | Named container with item count |
| `PocketItem` | WYSIWYG content item with optional encryption and ordering |

## Routes (`pocket-app/`)

| Path | Purpose |
|------|---------|
| `pockets` | Pocket list with size and dates |
| `all-pocket-items?pocket-name={name}` | Items within a selected pocket |

## Architecture

- `PocketService` — CRUD with paginated loading and `@PostFilter` access control
- `PocketItemService` — saves with auto-encryption, decrypt via `decryptContent(item, password)`
- `EncryptionService` (from `common`) — AES encryption

## Build

```bash
mvn clean install -DskipTests
```

Part of the `my-tools` multi-module Maven project. Requires `common` to be built first.
