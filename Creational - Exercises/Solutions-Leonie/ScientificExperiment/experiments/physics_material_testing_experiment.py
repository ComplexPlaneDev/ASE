from experiments.experiment_interface import ExperimentInterface

class PhysicsMaterialTestingExperiment(ExperimentInterface):
    def __init__(self, material="Steel", stress_level=100.0, environment="Room Temp", measurement_interval=10):
        self.material = material
        self.stress_level = stress_level  # in MPa
        self.environment = environment
        self.measurement_interval = measurement_interval  # in seconds

    def clone(self):
        # create a copy of this experiment
        return PhysicsMaterialTestingExperiment(
            material=self.material,
            stress_level=self.stress_level,
            environment=self.environment,
            measurement_interval=self.measurement_interval
        )

    def run(self):
        print(f"Running physics test: {self.get_description()}")

    def get_description(self):
        return (f"Material: {self.material}, Stress: {self.stress_level} MPa, "
                f"Environment: {self.environment}, Interval: {self.measurement_interval}s")
