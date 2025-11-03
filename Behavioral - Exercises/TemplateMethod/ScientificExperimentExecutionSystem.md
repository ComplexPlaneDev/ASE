# Scientific Experiment Execution System

## Problem Statement

You are developing a system for a research laboratory that needs to execute different types of scientific experiments. Each experiment follows a standardized procedure, but the specific implementation of certain steps varies depending on the experiment type.

## Requirements

The laboratory conducts three main types of experiments:
1. **Chemistry Experiments** - involve mixing chemicals and measuring reactions
2. **Physics Experiments** - involve measuring physical properties and forces
3. **Biology Experiments** - involve observing biological samples and recording changes

## Common Execution Flow

All experiments must follow this standardized procedure:

1. **Initialize** the experiment (log start time, prepare workspace)
2. **Setup Equipment** (experiment-specific equipment preparation)
3. **Calibrate** instruments (optional step - some experiments need it, others don't)
4. **Execute** the main experiment procedure (experiment-specific)
5. **Record Results** (experiment-specific data collection and formatting)
6. **Validate** results (optional step - some experiments need validation, others don't)
7. **Cleanup** (experiment-specific cleanup procedures)
8. **Finalize** the experiment (log end time, archive results)

## Specific Requirements

### Common Steps (same for all experiments):
- **Initialize**: Always log "Starting experiment at [current time]" and "Workspace prepared"
- **Finalize**: Always log "Experiment completed at [current time]" and "Results archived"

### Experiment-Specific Steps:
- **Setup Equipment**: Each experiment type requires different equipment
- **Execute**: Each experiment has its own procedure
- **Record Results**: Each experiment records different types of data
- **Cleanup**: Each experiment has different cleanup requirements

### Optional Steps:
- **Calibrate**: Only Chemistry and Physics experiments need calibration
- **Validate**: Only Physics and Biology experiments need result validation
