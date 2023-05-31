import java.util.*

class Screen(private val screenInterface: ScreenInterface) {

    private var scanner = Scanner(System.`in`)
    private var archivesMap: MutableMap<Int, Archive> = mutableMapOf()
    private var archiveVariable: Int = 0
    private var noteVariable: Int = 0
    private var idArchive: Int = 0
    private var idNote: Int = 0

    fun chooseArchive() {

        println("0. Создать архив")

        archivesMap.forEach { (id, archive) ->
            println("$id. Выбрать архив ${archive.name}" + " (количество заметок: ${archive.notesMap?.size})")
        }

        println("${archivesMap.size + 1}. Выход")

        when (val archiveIds = checkInput(archivesMap.size + 1)) {
            0 -> screenInterface.action(E_State.ARCHIVE_CREATE)
            in 1..archivesMap.size -> {
                archiveVariable = archiveIds
                screenInterface.action(E_State.ARCHIVE_OPEN)
            }
            else -> screenInterface.exit()
        }
    }

    fun openArchive() {

        println("Добавить заметку")

        val notes: MutableMap<Int, Notes> = archivesMap[archiveVariable]?.notesMap ?: mutableMapOf()
        notes.forEach {
                (id, note) -> println("$id. Выбрать заметку ${note.name}")
        }

        println("${notes.size + 1}. Выйти")

        when (val noteIds = checkInput(notes.size + 1)) {
            0 -> screenInterface.action(E_State.NOTE_CREATE)
            in 1..notes.size -> {
                noteVariable = noteIds
                screenInterface.action(E_State.NOTE_OPEN)
            }
            else -> screenInterface.action(E_State.ARCHIVE_CHOOSE)
        }
    }

    fun createArchive() {

        println("Введите название архива:")

        val name = readInput()
        idArchive += 1
        val id = idArchive
        archivesMap.put(id, Archive(name, mutableMapOf()))
        println("Архив: $id. ${archivesMap[id]?.name} создан")
        screenInterface.action(E_State.ARCHIVE_CHOOSE)
    }

    fun openNote() {

        println("Заметка ${archivesMap[archiveVariable]?.notesMap?.get(noteVariable)?.name}: " +
                "${archivesMap[archiveVariable]?.notesMap?.get(noteVariable)?.text}")

        println("Введите цифру 1 для выхода")

        when (scanner.nextLine()) {
            "1" -> screenInterface.action(E_State.ARCHIVE_OPEN)
            else -> println("Такой команды нет")
        }
    }

    fun createNote() {
        println("Введите название:")
        val name = readInput()
        println("Введите текст:")
        val text = readInput()
        idNote += 1
        val id = idNote
        archivesMap[archiveVariable]?.notesMap?.put(id, Notes(name, text, archiveVariable))
        screenInterface.action(E_State.ARCHIVE_OPEN)
    }

    private fun checkInput(length: Int) : Int {
        var idCommand = checkingForCommand(length)

        while (idCommand == -1) {
            idCommand = checkingForCommand(length)
        }
        return idCommand
    }

    private fun checkingForCommand(length: Int) : Int {
        val input = scanner.nextLine()
        if (input.toIntOrNull() == null) {

            println("Такой цифры нет, попробуйте снова")

            return -1
        }
        if (input.toInt() > length) {

            println("Такой цифры нет, попробуйте снова")

            return -1
        }
        return input.toInt()
    }

    private fun readInput() : String {
        return scanner.nextLine().replaceFirstChar { it.titlecase(Locale.getDefault()) }
    }
}