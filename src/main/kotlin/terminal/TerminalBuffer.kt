package terminal

import java.util.ArrayDeque

class TerminalBuffer (
    val width: Int,
    val height: Int,
    val maxScrollback: Int
) {
    // STATE: Cursor & Style
    var cursorCol: Int = 0
        private set
    var cursorRow: Int = 0
        private set

    // current colors/styles for new text
    var currentAttributes: Style = Style.DEFAULT

    // STORAGE
    // active screen: list of rows where each row is a list of Cells
    // initialized immediately with empty cells
    private val screen: MutableList<MutableList<Cell>> = MutableList(height) {
        generateEmptyRow()
    }
    // History - double-ended queue
    // push to the end/bottom and pop from the start/top when full
    private val scrollback: ArrayDeque<List<Cell>> = ArrayDeque()

    // HELPERS
    // creates new row of empty cells
    private fun generateEmptyRow(): MutableList<Cell> {
        return MutableList(width) { Cell() }
    }
    // for testing
    override fun toString(): String{
        return "Buffer(${width}x${height}), Cursor: $cursorCol,$cursorRow)"
    }
}