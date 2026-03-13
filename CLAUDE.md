# Pocket App - Project Notes

> **IMPORTANT**: Keep this file updated when making significant changes to the codebase. This file serves as persistent memory between Claude Code sessions.

## Overview
Personal note/item storage system. Users organize content into named "pockets" (containers) and manage individual items within them. Supports optional per-item encryption and full change history.

## Key Architecture

### Entities

#### Pocket
- `id: UUID`, `name: String` (3-100, unique per owner)
- `creationDate`, `modificationDate`, `deleted: Boolean`
- `pocketItems: Set<PocketItem>` (OneToMany), `history: Set<HistoryPocket>`
- `getPocketSize()` — count of non-deleted items

#### PocketItem
- `id: UUID`, `summary: String` (3-100), `content: String` (max 5MB, WYSIWYG)
- `orderInPocket: Integer`, `pocket: Pocket` (ManyToOne)
- `encrypted: Boolean`, `deleted: Boolean`, `creationDate`, `modificationDate`
- `history: Set<HistoryPocketItem>`

### Services

#### PocketService
- `save(Pocket)`, `delete(Pocket)` — cascades to all items
- `load(Pageable)` — paginated, owner-filtered
- `loadByName(String)`, `loadHistory()`

#### PocketItemService
- `save(PocketItem)` — auto-encrypts if `encrypted=true`
- `encryptContent(PocketItem)` — uses user's data cipher password
- `decryptContent(PocketItem, String password)`
- `loadByPocketName(String)`, `loadHistory()`

### Views

#### AbstractPocketView
- Route: `pocket-app/pockets`
- Grid: name, creation/modification dates, size (item count)

#### AbstractAllPocketItemsView
- Route: `pocket-app/all-pocket-items`
- ComboBox for pocket selection; URL query param: `?pocket-name=MyPocket`
- Grid: summary, content, encrypted status

## Configuration
- `src/main/resources/autoconfig/Pocket.yml` — name field (3-100 chars)
- `src/main/resources/autoconfig/PocketItem.yml` — summary (3-100), content (wysiwyg: true, max 5MB)

## Important Notes
1. Optional per-item encryption using `user.getDataCipherPassword()`
2. Cascading soft deletes: deleting a pocket soft-deletes all its items
3. History/audit trail via `@HistorySupported` annotation
4. Multi-tenancy via `BervanOwnedBaseEntity`; `@PostFilter` for access control
5. WYSIWYG editor for content field
