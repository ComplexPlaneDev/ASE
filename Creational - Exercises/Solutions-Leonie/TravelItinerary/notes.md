### Design pattern: Builder Pattern?
- Itinerary should consist of multiple optional pieces and different versions (adventure, business, family)
- builder seperates what's built (structure of itinerary and different components) from how it's built (different steps for each version)
- step by step structuring allows for flexible combinations

### Idea
Client --> Itinerary Planner (builds the final itinerary) --> Itinerary Builder Interface (which is implemented by Adventure, Business and Family classes) --> Itinerary (classes for TripInfo, flights, accommodations, transportation, documents)

#### class Itinerary
- show(): displays complete itinerary
- add_component(): adds a component to the itinerary (f.ex. flight, accommodation, ...)

#### class ItineraryBuilderInterface
- functions for each component, will be implemented in concrete builders
    - add_trip_info()
    - add_flights()
    - add_accommodations()
    - add_transportation()
    - add_documents()
- get_result(): return itinerary

#### class AdventureItineraryBuilder, BusinessItineraryBuilder, FamilyItineraryBuilder
- each class implements the functions from the Interface

#### class ItineraryPlanner
- build_itinerary(): adds components to itinerary and returns resulting complete itinerary