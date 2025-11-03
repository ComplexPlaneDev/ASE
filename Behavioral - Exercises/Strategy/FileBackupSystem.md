# File Backup System

## Problem Statement

You are tasked with designing a file backup system for a small software company. The system needs to handle backup operations for critical files, but the company has different requirements based on the situation:

1. **Full Backup**: Create a complete backup of all files (used for weekly backups)
2. **Incremental Backup**: Only backup files that have changed since the last backup (used for daily backups)
3. **Differential Backup**: Backup files that have changed since the last full backup (used for mid-week backups)

## Requirements

The system should be able to:
- Accept a list of files to backup
- Apply the appropriate backup algorithm based on the backup type needed
- Return a backup report indicating what was processed
