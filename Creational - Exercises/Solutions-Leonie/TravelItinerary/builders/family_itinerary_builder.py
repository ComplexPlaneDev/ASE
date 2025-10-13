from .itinerary_builder_interface import ItineraryBuilderInterface
from itinerary import Itinerary

class FamilyItineraryBuilder(ItineraryBuilderInterface):
    def __init__(self):
        self.itinerary = Itinerary()

    def add_trip_info(self):
        self.itinerary.add_component("Trip Info: 7-day holiday trip to Croatia")
    def add_flights(self):
        self.itinerary.add_component("Flights: October 10th, 7am, Frankfurt + October 17th, 5pm, Dubrovnik")
    def add_accommodations(self):
        self.itinerary.add_component("Accommodations: 3-bedroom vacation home")
    def add_transportation(self):
        self.itinerary.add_component("Transportation: none booked")
    def add_documents(self):
        self.itinerary.add_component("Documents: Bus and train tickets for the duration of the stay, passports")

    def get_result(self):
        return self.itinerary