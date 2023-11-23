package o1.adventure
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map

/** The class 'Prison_Escape' represents text adventure games. A prison escape consists of a prisoner,
  * who is the main player, 7 rooms divided into 3 rounds, some items and 2 non-player characters who
  * have relationship with the prisoner. This provides methods for playing the game one
  * turn at a time and for checking the state of the game. */

class Prison_Escape:

  /** The name of the game */
  private val title = "Amnesiac Justice: Unraveling the Forgotten Crime"

  /** The number of successfully chooseRoom-command that have passed since the start of the game. */
  var chooseCount = 0
  
  /** The number of successfully elaborate-command that have passed since the start of the game. */
  var elaborateCount = 0
  

  /** starting area */
  private val prison      = Room("Prison - Round 0", "You are in the prison. Luckily, you have the chance to escape from it.")

  /** round 1 - choose between 3 rooms, each contains 1 item */
  private val room_1      = Room("Room number 1", "You have entered the room, which contains your personal belongings.")
  private val room_2      = Room("Room number 2", "You have entered the room, which contains a woman's belongings.")
  private val room_3      = Room("Room number 3", "You have entered the room, which contains a man's belongings.")

  /** round 2 - choose between 2 rooms, the place the prisoner come before or after commiting crime */
  private val room_4      = Room("Room number 4", "Before preparing lunch for your director, you went to the pharmacy." +
                                  "\nAt the afternoon, you needed to welcome some V.I.P to the museum.")
  private val room_5      = Room("Room number 5", "You went to the meeting of several art museum directors of the city." +
                                 "\nThis place also hosted for the art picture auction")

 /** round 3 */
  private val room_6      = Room("Room number 6", "You have entered the room. There is a man sitting in front of you")
  private val room_7      = Room("Room number 7", "You have entered the room. There is a woman sitting in front of you")
  private val world       = Room("Outside", "You got outside. Well done !")

  /** The character that the player controls in the game. */
  val player = Player(prison)

  /** Add accessible rooms. A player in rooms in round 1 can only get access to rooms in round 2, and similarly */
  this.prison.addAccessibleRooms(Buffer[Room](this.room_1, this.room_2, this.room_3))
  this.room_1.addAccessibleRooms(Buffer[Room](this.room_4, this.room_5))
  this.room_2.addAccessibleRooms(Buffer[Room](this.room_4, this.room_5))
  this.room_3.addAccessibleRooms(Buffer[Room](this.room_4, this.room_5))
  this.room_4.addAccessibleRooms(Buffer[Room](this.room_6, this.room_7))
  this.room_5.addAccessibleRooms(Buffer[Room](this.room_6, this.room_7))
  this.room_6.addAccessibleRooms(Buffer[Room](this.world, this.prison))
  this.room_7.addAccessibleRooms(Buffer[Room](this.world, this.prison))

  /** Add items to rooms in round 1 */
  this.room_1.addItem(Item("Phone", "This is your phone. You can get access to it to see the album"))
  this.room_2.addItem(Item("Envelop", "This is the envelop you gave to this woman, when you travelled abroad for business"))
  this.room_3.addItem(Item("Audio", "This man always has a pen voice recorder with him, there is a conversation between you and him."))

  /** Add NPCs and their information to room in round 3 */
  this.room_6.addNPC(NPC("Thomas", "This is your colleague at the art museum."))
  this.room_6.addInformationOfNPC("Personality", "At work, you are an ambitious employee, who has genuine passion for the museum's success"
      + "\nand your dream of being rich. In love, you seem to be over protective, masking insecurity or jealousy.")
  this.room_6.addInformationOfNPC("Relationship", "Your relationship with your girlfriend is filled with ups and downs. You used to tell me that"
      + "\nyou cannot afford to her wasteful consupmtion anymore. You seem to respect and admire the director professionally." +
        "\nYou may have learned a lot from him.")
  this.room_6.addInformationOfNPC("Past","You had a challenging upbringing, excelled academically, and fell in love with Julia during your university years." +
    "\nYou pursued a career in art history and secured a position as a secretary at a prestigious museum under the mentorship of John - the director, whom you admired yet envied. " +
    "\nYour relationship with Julia became more complex as she developed a close friendship with John")


  this.room_7.addNPC(NPC("Julia", "This is your girlfriend. You have been in love with her for 3 years."))
  this.room_7.addInformationOfNPC("Personality", "You are a caring and protective partner who is supportive, dedicated, and hardworking."
   + "\nHe is also sometimes possessive and introverted, which can lead to moments of tension.")
  this.room_7.addInformationOfNPC("Relationship", "Our relationship is marked by complexity and moments of both deep love and betrayal."
   + "\nYour connection with director John and feelings of envy contribute to our relationship's intricacies.")
  this.room_7.addInformationOfNPC("Past","You had a difficult upbringing and achieved academic success. " +
    "\nWe met during our university years, and our relationship was deeply affected by financial struggles." +
    "\nYou worked hard to secure a job at a prestigious museum, where you were mentored by your director - John." +
    "\nDespite your admiration for John's success, you harbored feelings of envy. Over time, our relationship became more complex," +
    "\nwith moments of intense love and doubt, particularly as I developed a close friendship with Richard")



  /** Add behind story to each room, which acts as a hint for the main player to guess his
    * forgotten crime */

  this.room_1.addBehindStory("You see a picture of a man, your girlfriend and yourself at an art museum. " +
    "\nYou have a name tag as a secretary, the other man seems to be your director. \nYour girlfriend wear the same shirt " +
    "as the other man")

  this.room_2.addBehindStory("The envelop:\n\n" + "Dear Julia," + "\nI have exprienced many new things as a secretary with director John. " +
    "\nAlthough he works as a leader of an art museum, he has broad knowledge in technology. \nThanks you for introducing me to him. " +
    "See you soon, honey." + "\n\nLove you\nYour boyfriend")

  this.room_3.addBehindStory("The recorded message on the device appears to be a snippet of a conversation." +
    "\nThe voices are somewhat distorted, but you can hear your voice and another man:" + "\n" + "bzzzzzzzzzzzzzzz" + "\n" +
    """Unknown: "Secretary John, go and make some good tea. We have some important guests this afternoon"""" + "\n" +
    """Unknown: "Oh! And invite your girlfriend, she seemed to like our art museum when she visited last time"""" + "\n" +
    """You    : "Yes, Director !"""")

  this.room_4.addBehindStory("You bought Kali Cyanide. This is a colorless salt, highly soluble in water, very toxic that \ncan kill nearly any human with small amount." +
    " This is also used for jewellery in chemical gilding and buffing.")

  this.room_5.addBehindStory("Although your hand had some white things looked like sugar, you shaked hand with everyone" +
    "\nBut wait...Why did you come to the meeting for 'directors' ?")

  this.room_6.addBehindStory("What do you wanna know about yourself? - said Thomas" + "\nYou have 3 choices: personality, relationship, past")

  this.room_7.addBehindStory("What do you wanna know about yourself? - said Julia" + "\nYou have 3 choices: personality, relationship, past")


  /** This is used to deal with the player's command. If the player is in round 0 (chooseCount = 0),
    * then his chooseRoom command must include number 1, 2 or 3. And similarly. */
  val RoundsAndRooms = Map[Int, Vector[String]](0 -> Vector("1", "2", "3"),
                                             1 -> Vector("4", "5"),
                                             2 -> Vector("6", "7"))

  /** Determines if the adventure is complete, that is, if the player has won. */
  def isWon = this.player.location == this.world

  def isBeforeLastRound = this.chooseCount < 3

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "Welcome to 'Amnesiac Justice: Unraveling the Forgotten Crime'!" +
    "\nYou find yourself in a dark and enigmatic world, a prisoner who has lost not only their freedom but also their memories." +
    "\nAs you embark on this journey of self-discovery, you will play each round to receive hints about the crime you committed." +
    "\nAt the final round, if you memorize your forgotten crime, you will be free!."


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage =
    if this.isWon then
      "Your answer is correct!\n" + this.player.location.description
    else 
      "Your answer is incorrect!\nGame over!\n" + "You got back in the prison !"


  /** Executing the given in-game command, which is to choose the room the player wants to move in.
    * This returns a string of successfully moving in or not. */
  def chooseRooms(command: String) =
    var output = ""
    val outcomeReport = Action(command).executeRooms(this.player)
    outcomeReport match
      case Some(report) =>
        output = report
        this.chooseCount += 1
      case None =>
        output = s"""Unknown command: "$command"."""
    output

  /** Executing the given in-game command. If the player wants to elaborate the current room,
    * this returns the behind story. If not, this returns that the player don't want to. */
  def chooseToElaborate(command: String) =
    var output = ""
    val outcomeReport = Action(command).executeToElaborate(this.player)
    outcomeReport match
      case Some(report) =>
        output = report
        this.elaborateCount += 1
      case None =>
        output = s"""Unknown command: "$command"."""
    output

  def location = this.player.location
  
  /** Final answer, which is the whole story of the game, appears at the end after the player's answer
    * whether the player wins or loses */
  val finalAnswer = ""
  
  

end Prison_Escape


