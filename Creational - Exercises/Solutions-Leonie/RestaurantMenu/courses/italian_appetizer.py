from courses.appetizer_interface import AppetizerInterface

class ItalianAppetizer(AppetizerInterface):
    def prepare(self):
        print("Prepare Italian Bruschetta with fresh tomatoes and basil.")

    def serve(self):
        print("Serve Bruschetta on toasted bread with olive oil drizzle.")

    def get_description(self):
        return "Italian Appetizer: Bruschetta with tomatoes, garlic, and basil."
