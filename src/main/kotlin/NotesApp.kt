class NotesApp: ScreenInterface {

    private var state = E_State.ARCHIVE_CHOOSE


    fun start() {

        println("Здесь вы можете оставить свою заметку")

        val screen = Screen(this)

        while (true) {
            when (state) {
                E_State.ARCHIVE_CHOOSE -> screen.chooseArchive()
                E_State.ARCHIVE_OPEN -> screen.openArchive()
                E_State.ARCHIVE_CREATE -> screen.createArchive()
                E_State.NOTE_OPEN -> screen.openNote()
                E_State.NOTE_CREATE -> screen.createNote()
                E_State.EXIT -> break
            }
        }
    }

    override fun action(state: E_State) {
        this.state = state
    }

    override fun exit() {
        this.state = E_State.EXIT
    }
}