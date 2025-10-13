from factories.menu_factory_interface import MenuFactoryInterface
from courses.italian_appetizer import ItalianAppetizer
from courses.italian_main_course import ItalianMainCourse

class ItalianMenuFactory(MenuFactoryInterface):
    def create_appetizer(self):
        return ItalianAppetizer()

    def create_main_course(self):
        return ItalianMainCourse()
