from .itinerary_builder_interface import ItineraryBuilderInterface
from itinerary import Itinerary

class AdventureItineraryBuilder(ItineraryBuilderInterface):
    def __init__(self):
        self.itinerary = Itinerary()

    def add_trip_info(self):
        self.itinerary.add_component("Trip Info: 10-day Rainforest adventure trip")
    def add_flights(self):
        self.itinerary.add_component("Flights: October 10th, 7am, Frankfurt + October 20th, 5am, Singapoor")
    def add_accommodations(self):
        self.itinerary.add_component("Accommodations: 3-star Mountain lodge")
    def add_transportation(self):
        self.itinerary.add_component("Transportation: 4x4 rental car")
    def add_documents(self):
        self.itinerary.add_component("Documents: Travel insurance, vaccination certificate and emergency contacts")

    def get_result(self):
        return self.itinerary