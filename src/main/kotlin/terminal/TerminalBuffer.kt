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

    // Pen: current colors/styles for new text
    var currentAttributes: Style = Style.DEFAULT

    // CURSOR OPERATIONS

    // Set the cursor to a specific position
    fun setCursorPosition(col: Int, row: Int) {
        // coerceIn(min, max) forces the value to stay inside that range
        cursorCol = col.coerceIn(0, width - 1)
        cursorRow = row.coerceIn(0, height - 1)
    }

    fun moveCursorUp(n: Int) {
        // Moving "Up" means decreasing the row index (0 is top)
        setCursorPosition(cursorCol, cursorRow - n)
    }

    fun moveCursorDown(n: Int) {
        // Moving "Down" means increasing the row index
        setCursorPosition(cursorCol, cursorRow + n)
    }

    fun moveCursorLeft(n: Int) {
        setCursorPosition(cursorCol - n, cursorRow)
    }

    fun moveCursorRight(n: Int) {
        setCursorPosition(cursorCol + n, cursorRow)
    }

    // ATTRIBUTE OPERATIONS

    // Update the "Pen" style for future writes
    fun setAttributes(fgColor: Int, bgColor: Int, bold: Boolean, italic: Boolean, underline: Boolean) {
        currentAttributes = Style(
            fgColor = fgColor,
            bgColor = bgColor,
            isBold = bold,
            isItalic = italic,
            isUnderline = underline
        )
    }

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
    // Helper to reset attributes to default
    fun resetAttributes() {
        currentAttributes = Style.DEFAULT
    }
    // for testing
    override fun toString(): String{
        return "Buffer(${width}x${height}), Cursor: $cursorCol,$cursorRow)"
    }
}