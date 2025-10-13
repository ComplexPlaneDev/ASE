from experiments.experiment_interface import ExperimentInterface

class BiologyCultureExperiment(ExperimentInterface):
    def __init__(self, organism="E.coli", growth_medium="LB", incubation_temperature=37.0, observation_period=24):
        self.organism = organism
        self.growth_medium = growth_medium
        self.incubation_temperature = incubation_temperature
        self.observation_period = observation_period

    def clone(self):
        # create a copy of this experiment
        return BiologyCultureExperiment(
            organism=self.organism,
            growth_medium=self.growth_medium,
            incubation_temperature=self.incubation_temperature,
            observation_period=self.observation_period
        )

    def run(self):
        print(f"Running biology culture: {self.get_description()}")

    def get_description(self) -> str:
        return (f"Organism: {self.organism}, Medium: {self.growth_medium}, "
                f"Incubation Temp: {self.incubation_temperature}°C, Observation: {self.observation_period}h")
