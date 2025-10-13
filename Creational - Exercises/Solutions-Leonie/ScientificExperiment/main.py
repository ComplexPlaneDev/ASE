from experiments.chemical_analysis_experiment import ChemicalAnalysisExperiment
from experiments.biology_culture_experiment import BiologyCultureExperiment
from experiments.physics_material_testing_experiment import PhysicsMaterialTestingExperiment

def main():
    # create initial experiments
    chemical_proto = ChemicalAnalysisExperiment(compounds=["H2O", "NaCl"], reaction_temperature=50.0)
    biology_proto = BiologyCultureExperiment(organism="Yeast", growth_medium="YPD")
    physics_proto = PhysicsMaterialTestingExperiment(material="Aluminum", stress_level=150.0)

    # clone and modify for new runs
    chem_exp1 = chemical_proto.clone()
    chem_exp1.ph_level = 8.0

    bio_exp1 = biology_proto.clone()
    bio_exp1.observation_period = 48

    phys_exp1 = physics_proto.clone()
    phys_exp1.environment = "High Temp"

    # run experiments
    chem_exp1.run()
    bio_exp1.run()
    phys_exp1.run()

if __name__ == "__main__":
    main()
