from courses.appetizer_interface import AppetizerInterface

class ChineseAppetizer(AppetizerInterface):
    def prepare(self):
        print("Prepare Chinese Spring Rolls with vegetables and soy sauce.")

    def serve(self):
        print("Serve Spring Rolls with sweet chili dipping sauce.")

    def get_description(self):
        return "Chinese Appetizer: Crispy Spring Rolls filled with cabbage and carrots."
