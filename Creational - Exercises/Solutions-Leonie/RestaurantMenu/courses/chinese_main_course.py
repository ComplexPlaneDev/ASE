from courses.main_course_interface import MainCourseInterface

class ChineseMainCourse(MainCourseInterface):
    def prepare(self):
        print("Cook Chinese Stir-Fried Noodles with vegetables and soy sauce.")

    def serve(self):
        print("Serve Stir-Fried Noodles hot with sesame seeds on top.")

    def get_description(self):
        return "Chinese Main Course: Stir-Fried Noodles with vegetables and light soy sauce."
