# Problem: Gaming Character

## Pattern Pick + Reasoning 

**My pick:** The Abstract Factory Pattern

**Reasoning:** 

I'm designing a roleplaying game that supports different themes (Medieval and SciFi). Each theme has its own versions of characters like Warrior or Mage, but the game mechanics stay the same. When a player picks a theme, all characters created should belong to that same theme, for example, a Medieval Warrior with a Medieval Mage, or a SciFi Warrior with a SciFi Mage.

That fits really well with the abstract factory pattern. In this pattern, the client picks a factory that knows how to create a family of related objects - in this case, a specific theme. The Medieval factory creates medieval characters, and the SciFi factory creates scifi characters. The client doesn’t need to know which exact classes are used, it only communicates with the factory interface.

This keeps the game consistent, since all characters come from the same factory, and it also makes the system easy to extend later by adding new factories for other themes (like Fantasy, Cyberpunk) or new character types (like Archer, Healer) without changing existing code.