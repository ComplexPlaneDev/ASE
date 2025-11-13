class ItineraryBuilderInterface:
    # functions to add components
    def add_trip_info(self):
        raise NotImplementedError
    def add_flights(self):
        raise NotImplementedError
    def add_accommodations(self):
        raise NotImplementedError
    def add_transportation(self):
        raise NotImplementedError
    def add_documents(self):
        raise NotImplementedError
    
    # return resulting itinerary
    def get_result(self):
        raise NotImplementedError