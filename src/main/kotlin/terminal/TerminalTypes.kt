package terminal

// Visual style of a single cell
data class Style(
    val fgColor: Int = -1, // Default
    val bgColor: Int = -1,
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
    val isUnderline: Boolean = false
) {
    companion object {
        val DEFAULT = Style()
    }
}

// Cell: One grid on the terminal screen
data class Cell(
    var char: Char = ' ', //empty
    var style: Style = Style.DEFAULT
) {
    // Helper to reset a cell to empty default
    fun reset() {
        char = ' '
        style = Style.DEFAULT
    }

    // Helper to copy data from another cell (for scrolling/shifting)
    fun copyFrom(other: Cell) {
        this.char = other.char
        this.style = other.style
    }
}