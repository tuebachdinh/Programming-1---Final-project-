package o1.adventure.ui

import o1.adventure.*
import scala.io.StdIn.*


/** The singleton object `PrisonEscapeTextUI` represents a fully text-based version of the
  * Prison_Escape game application. The object serves as an entry point for the game, and
  * it can be run to start up a user interface that operates in the text console.
  * @see [[AdventureGUI]] */
object PrisonEscapseTextUI extends App:

  private val game = Prison_Escape()
  private val player = game.player
  private var checkLastRound = ""
  this.run()

  /** Runs the game. First, a welcome message is printed, then the player gets the chance to
    * play 3 rounds before the judgement session, which requires the player to say exactly his or her
    * crime, and finally a goodbye message is printed. */
  private def run() =
    println("\n" + this.game.welcomeMessage)

    while this.game.isBeforeLastRound do
      this.printCurrentLocation()
      this.printAskForDecision()
      this.printRoomInfo()
      this.printAskToElaborate()

    this.printConversationWithNPC()
    this.printJudgementSession()
    println("\n" + this.game.goodbyeMessage)
    println("\n"+ this.game.finalAnswer)

  /** Print the judgement session time after 3 rounds, the player has to answer 2 question to go outside. Either wrong answer
    * leads the player get back to the prison */
  private def printJudgementSession() =
    println()
    println("------- JUDGEMENT SESSION -------")
    val input = readLine("Now tell us what is the crime you committed ? : ").trim.toLowerCase
    if this.containString(input, Vector("kill", "murder", "killed", "murdered")) then
      println("Your answer is correct, but what ultimately drove you to take a life and commit this heinous act? " +
        "\nWas it a desperate pursuit of wealth, or a heartbreak that led to a moment of irreversible despair?")
      val lastAnswer = readLine("\nYour answer:  ").trim.toLowerCase
      val result = this.containString(lastAnswer, Vector("wealth", "money"))
      if result == true then
        this.player.moveOutside
      else this.player.moveBackToPrison
    else this.player.moveBackToPrison


  /** Print the conversation with the NPC at the last round. It can choose 1 topic for the NPC to elaborate  */
  private def printConversationWithNPC() =
    if this.checkLastRound != "You don't want to elaborate" then

      val input = LazyList.continually(readLine("Your choice: ").trim.toLowerCase)
      val checkedInput = input.dropWhile(!this.containString(_, Vector("personality", "relationship", "past"))).head

      if checkedInput.contains("personality") then
        println(this.player.location.informationOfNPC("Personality"))
      else if checkedInput.contains("relationship") then
        println(this.player.location.informationOfNPC("Relationship"))
      else if checkedInput.contains("past") then
        println(this.player.location.informationOfNPC("Past"))

    else println("This will be harder for you in the final round!")



  /** Print the player current location before choosing the room to get in.
    *  This is different from the printRoomInfo() functions */
  private def printCurrentLocation() =
    println()
    println("You are in " + this.player.location.name)
    println(s"You have ${this.player.location.accessibleRooms.size} rooms available to get access to:")
    this.player.location.accessibleRooms.foreach(room => println(room.name))
    if this.player.location.accessibleRooms.forall(_.hasItem) then
      println("Round 1 - In this round, each room contains 1 item that reveal the truth, the forgotten past that haunts you.")
    else if this.player.location.accessibleRooms.forall(_.hasNPCs) then
      println("Round 3 - In this round, you had a chance to talk to the person familiar with you")
    else
      println("Round 2 - In this round, the former room is the place you went to before committing crime," +
        "\nthe latter one is the place you went to after committing crime")


  /** At the beginning of each round, the player is asked to choose between some rooms */
  private def printAskForDecision() =
    val command = LazyList.continually(readLine("Which room do you choose: "))
    val checkedCommand = command.dropWhile(!this.containString(_, this.game.RoundsAndRooms(this.game.chooseCount))).head
    val turnReport = this.game.chooseRooms(checkedCommand)
    println("\n" + turnReport)


  /** Print out a description of the player character’s current location after choosing the room. */
  private def printRoomInfo() =
    val area = this.player.location
    println("\n\n" + area.name)
    println("-" * area.name.length)
    println(area.fullDescription + "\n")


  /** Requests a command from the player, the player can enter anything as long as it contains
    * "yes" or "no" */
  private def printAskToElaborate() =
    println()
    val command = LazyList.continually(readLine("Do you want to elaborate this room ?: ").trim.toLowerCase)
    val checkedCommand = command.dropWhile(!this.containString(_, Vector("yes", "no"))).head
    val turnReport = this.game.chooseToElaborate(checkedCommand)
    this.checkLastRound = turnReport
    println(turnReport)
 
  /** Use to check the user's input whether it contains keywords of a correct answer */
  private def containString(checked: String, check: Vector[String]) =
    check.exists(checked.contains(_)) 

  
end PrisonEscapseTextUI

