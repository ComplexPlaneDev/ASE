import copy

class ExperimentInterface:
    def clone(self):
        raise NotImplementedError

    def run(self):
        raise NotImplementedError

    def get_description(self):
        raise NotImplementedError

