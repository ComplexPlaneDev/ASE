### Design pattern: Prototype Pattern
- experiments often share a base configuration with minor variations
- new experiment is created by cloning an existing one and only modifying necessary parameters without recalculating everything

### Idea
Client --> Prototype Interface (ExperimentInterface) --> Concrete Experiments (ChemicalAnalysisExperiment, BiologyCultureExperiment, PhysicsMaterialTesting)

#### class ExperimentInterface
- clone(): returns a copy of the experiment
- run(): executes the experiment (prints description/results)
- get_description(): returns details about the experiment parameters

#### class ChemicalAnalysisExperiment, BiologyCultureExperiment, PhysicsMaterialTesting
- each class implements the functions from the interface