from builders.adventure_itinerary_builder import AdventureItineraryBuilder
from builders.business_itinerary_builder import BusinessItineraryBuilder
from builders.family_itinerary_builder import FamilyItineraryBuilder
from itinerary_planner import ItineraryPlanner

if __name__ == "__main__":
    # adventure itinerary
    adventure_builder = AdventureItineraryBuilder()
    planner = ItineraryPlanner(adventure_builder)
    itinerary = planner.build_itinerary()
    itinerary.show()

    # business itinerary
    business_builder = BusinessItineraryBuilder()
    planner = ItineraryPlanner(business_builder)
    itinerary = planner.build_itinerary()
    itinerary.show()

    # family itinerary
    family_builder = FamilyItineraryBuilder()
    planner = ItineraryPlanner(family_builder)
    itinerary = planner.build_itinerary()
    itinerary.show()
