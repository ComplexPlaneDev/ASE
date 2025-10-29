from courses.main_course_interface import MainCourseInterface

class ItalianMainCourse(MainCourseInterface):
    def prepare(self):
        print("Cook Italian Spaghetti Carbonara with pancetta and egg sauce.")

    def serve(self):
        print("Serve Spaghetti Carbonara with Parmesan cheese on top.")

    def get_description(self):
        return "Italian Main Course: Spaghetti Carbonara made with eggs, cheese, and pancetta."
