package o1.adventure

/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command of choosing */
class Action(input: String):

  private val commandText = input.trim.toLowerCase

  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as “You go west.”). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def executeRooms(actor: Player) = 
    val chosenRoom = commandText.dropWhile( _.toString.toIntOption == None )(0).toString.toIntOption
    chosenRoom match
      case Some(number)  => Some(actor.choose(number.toString))
      case other         => None
    
  def executeToElaborate(actor: Player) =
    if this.commandText.contains("yes") then 
      Some(actor.elaborate)
    else if this.commandText.contains("no") then 
      Some("You don't want to elaborate")
    else 
      None

end Action

