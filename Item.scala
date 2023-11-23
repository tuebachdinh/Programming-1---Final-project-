package o1.adventure

/** The class `Item` represents items in a text adventure game. Each item has a name
  * and a longer description. Items only appears in the first round of the game. Each
  * item represent a hint about the crime of the player. An item can be used only once
  * to elaborate for more information
  *
  * @param name         the item’s name
  * @param description  the item’s description */

class Item(val name: String, val description: String):

  /** Returns a short textual representation of the item (its name, that is). */
  override def toString = this.name
  
end Item

