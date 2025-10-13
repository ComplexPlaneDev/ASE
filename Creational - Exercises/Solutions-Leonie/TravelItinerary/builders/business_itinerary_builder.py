from .itinerary_builder_interface import ItineraryBuilderInterface
from itinerary import Itinerary

class BusinessItineraryBuilder(ItineraryBuilderInterface):
    def __init__(self):
        self.itinerary = Itinerary()

    def add_trip_info(self):
        self.itinerary.add_component("Trip Info: 2-day business trip to Berlin")
    def add_flights(self):
        self.itinerary.add_component("Flights: October 10th, 7am, Frankfurt + October 13th, 5pm, Berlin")
    def add_accommodations(self):
        self.itinerary.add_component("Accommodations: 4-star suite at the Hilton")
    def add_transportation(self):
        self.itinerary.add_component("Transportation: private limousine")
    def add_documents(self):
        self.itinerary.add_component("Documents: Guest list invite for the president's private party")

    def get_result(self):
        return self.itinerary