### Design pattern: Abstract Factory Pattern
- menu should include groups of related objects (dishes of the same kitchen)
- only objects of the same kitchen can be combined
- menu should be easily expandable without changing the code
- Abstract facory pattern: each kitchen is a factory, each factory creates their own dishes

### Idea
Client --> Abstract Factory Interface (implemented by Menu Factories of each kitchen, f.ex. ItialianMenuFactory) --> Abstract Course Interface (implemented by dishes, f.ex. Appetizer, MainCourse) --> Conctrete Dishes (f.ex. ItalianAppetizer, ChineseMainCourse)

#### class Menu Factory Interface
- functions for each course, will be implemented in concrete menus
    - createAppetizer()
    - createMainCourse()

#### class ItalianMenuFactory, ChineseMenuFactory
- each class implements the functions from the Menu Interface

#### class AdventureItineraryBuilder, BusinessItineraryBuilder, FamilyItineraryBuilder
- each class implements the functions from the Interface

#### class AppetizerInterface, MainCourseInterface
- functions for each dish, implemented by concrete courses
    - prepare(): describes preparation instructions for the dish
    - serve(): describes how the dish is served
    - get_description(): returns a description of the dish including key ingredients

#### class ItalianMainCourse, ItalianAppetizer, ChineseMainCourse, ChineseAppetizer
- each class implements the functions from the Interface