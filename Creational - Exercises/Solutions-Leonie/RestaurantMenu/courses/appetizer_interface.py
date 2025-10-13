class AppetizerInterface:
    def prepare(self):
        raise NotImplementedError

    def serve(self):
        raise NotImplementedError

    def get_description(self):
        raise NotImplementedError
