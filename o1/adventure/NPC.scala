package o1.adventure

/** The class "NPC" represent non-player characters in the text adventure games. 
  * Each NPC has a name, a discription, and relationship with main player 
  * This only appears in the third round of the game */

class NPC(val name: String, val description: String):

  override def toString = this.name

