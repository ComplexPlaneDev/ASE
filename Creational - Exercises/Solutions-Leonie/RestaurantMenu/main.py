from factories.italian_menu_factory import ItalianMenuFactory
from factories.chinese_menu_factory import ChineseMenuFactory

def create_menu(factory):
    appetizer = factory.create_appetizer()
    main_course = factory.create_main_course()

    print("\n--- MENU PREVIEW ---")
    print(appetizer.get_description())
    print(main_course.get_description())

    print("\n--- PREPARATION ---")
    appetizer.prepare()
    main_course.prepare()

    print("\n--- SERVING ---")
    appetizer.serve()
    main_course.serve()
    print("--------------------\n")


if __name__ == "__main__":
    # Create and display an Italian Menu
    print("ITALIAN MENU:")
    create_menu(ItalianMenuFactory())

    # Create and display a Chinese Menu
    print("CHINESE MENU:")
    create_menu(ChineseMenuFactory())
