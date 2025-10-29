from experiments.experiment_interface import ExperimentInterface

class ChemicalAnalysisExperiment(ExperimentInterface):
    def __init__(self, compounds=None, reaction_temperature=25.0, ph_level=7.0, duration=60):
        self.compounds = compounds or ["H2O"]
        self.reaction_temperature = reaction_temperature
        self.ph_level = ph_level
        self.duration = duration

    def clone(self):
        # create copy of this experiment
        return ChemicalAnalysisExperiment(
            compounds=self.compounds[:],
            reaction_temperature=self.reaction_temperature,
            ph_level=self.ph_level,
            duration=self.duration
        )

    def run(self):
        print(f"Running chemical analysis: {self.get_description()}")

    def get_description(self):
        return (f"Compounds: {self.compounds}, Temp: {self.reaction_temperature}°C, "
                f"pH: {self.ph_level}, Duration: {self.duration} min")
