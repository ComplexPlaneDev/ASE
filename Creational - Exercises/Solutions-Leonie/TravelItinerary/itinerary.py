class Itinerary:
    def __init__(self):
        self.components = []
    
    # adds a component (flight, accommodation, ...) to the itinerary
    def add_component(self, component: str):
        self.components.append(component)

    def show(self):
        print("Travel Itinerary:")

        for component in self.components:
            print(f"- {component}")
