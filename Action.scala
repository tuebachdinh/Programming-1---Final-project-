package o1.adventure

/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as “go east” or “rest” */
class Action(input: String):

  private val commandText         = input.trim.toLowerCase
  private val chosenRoom          = commandText.dropWhile( _.toString.toIntOption == None ).toIntOption
  private val chosenToElaborate   = commandText

  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as “You go west.”). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def executeRooms(actor: Player) = this.chosenRoom match
    case Some(number)  => Some(actor.choose(number.toString))
    case other         => None
    
  def executeToElaborate(actor: Player) =
    if this.chosenToElaborate.contains("yes") then 
      Some(actor.elaborate)
    else if this.chosenToElaborate.contains("no") then 
      Some("You don't want to elaborate")
    else 
      None
  

  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = s"$chosenRoom (elaborate: $chosenToElaborate)"

end Action

