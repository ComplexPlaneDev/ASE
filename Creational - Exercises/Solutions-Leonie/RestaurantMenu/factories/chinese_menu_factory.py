from factories.menu_factory_interface import MenuFactoryInterface
from courses.chinese_appetizer import ChineseAppetizer
from courses.chinese_main_course import ChineseMainCourse

class ChineseMenuFactory(MenuFactoryInterface):
    def create_appetizer(self):
        return ChineseAppetizer()

    def create_main_course(self):
        return ChineseMainCourse()
