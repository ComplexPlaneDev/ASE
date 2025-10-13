from builders.itinerary_builder_interface import ItineraryBuilderInterface

class ItineraryPlanner:
    def __init__(self, builder: ItineraryBuilderInterface):
        self.builder = builder
    
    def build_itinerary(self):
        self.builder.add_trip_info()
        self.builder.add_flights()
        self.builder.add_accommodations()
        self.builder.add_transportation()
        self.builder.add_documents()

        return self.builder.get_result()