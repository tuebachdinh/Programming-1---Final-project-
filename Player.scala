package o1.adventure

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.
  *
  * @param startingRoom  the player’s initial location */

class Player(startingRoom: Room):

  private var currentLocation = startingRoom        
  private var quitCommandGiven = false            
  
  def moveOutside =
    this.currentLocation = this.currentLocation.accessibleRooms(0)

  def moveBackToPrison =
    this.currentLocation = this.currentLocation.accessibleRooms(1)

  def choose(room: String): String =
    val chosenRoom = room.toInt
    var result = s"You choose room number $chosenRoom"

    if chosenRoom == 1 then
      this.currentLocation = this.currentLocation.accessibleRooms(0)
    else if chosenRoom == 2 then
      this.currentLocation = this.currentLocation.accessibleRooms(1)
    else if chosenRoom == 3 then
      this.currentLocation = this.currentLocation.accessibleRooms(2)
   
    else if chosenRoom == 4 then
      this.currentLocation = this.currentLocation.accessibleRooms(0)
    else if chosenRoom == 5 then
      this.currentLocation = this.currentLocation.accessibleRooms(1)

    else if chosenRoom == 6 then
      this.currentLocation = this.currentLocation.accessibleRooms(0)
    else if chosenRoom == 7 then
      this.currentLocation = this.currentLocation.accessibleRooms(1)

    result
        

  def elaborate: String = this.currentLocation.behindStory

  /** Returns the player’s current location. */
  def location = this.currentLocation


  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

end Player

