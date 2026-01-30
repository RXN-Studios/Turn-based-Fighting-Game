# Mini Turn-Based Fighter

**Overview:**  
A small Java turn-based combat prototype demonstrating core combat mechanics and a simple adaptive enemy AI.

**How to run:**  
1. Open Terminal: Navigate to your project folder.
2. Compile the Source:
javac Action.java Player.java Game.java
3. Run the Game:
java Game

**Controls:**  
Choose action each turn: 1 = Attack, 2 = Reflect, 3 = Heal

**Design notes:**  
- Actions: Attack (deal damage), Reflect (counter), Heal (restore HP).  
- Enemy AI: prioritizes healing at low health, reacts to your last action, and attacks when advantageous.

**Future improvements:**  
- Add GUI (JavaFX / LibGDX) and animations  
- Add multiple enemy types and sound effects  
- Add unit tests and CI, polish UX

**Author:** B.Rakshan
