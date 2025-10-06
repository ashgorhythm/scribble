package com.ashgorhythm.scribble.domain

sealed class NoteCategory(val name: String, val displayName: String) {

    // Special category for filtering - shows all notes
    object All : NoteCategory("ALL", "All")

    // Selectable categories for notes
    object Personal : NoteCategory("PERSONAL", "Personal")
    object Work : NoteCategory("WORK", "Work")
    object Shopping : NoteCategory("SHOPPING", "Shopping")
    object Ideas : NoteCategory("IDEAS", "Ideas")
    object Other : NoteCategory("OTHER", "Other")

    companion object {
        /**
         * Get all category instances
         * Used for displaying all options
         */
        fun getAllCategories(): List<NoteCategory> = listOf(
            All, Personal, Work, Shopping, Ideas, Other
        )

        /**
         * Get selectable categories (excludes All)
         * Used in note creation/edit screen
         */
        fun getSelectableCategories(): List<NoteCategory> = listOf(
            Personal, Work, Shopping, Ideas, Other
        )

        /**
         * Convert string name back to category object
         * Used when reading from database
         */
        fun fromName(name: String): NoteCategory {
            return when (name) {
                All.name -> All
                Personal.name -> Personal
                Work.name -> Work
                Shopping.name -> Shopping
                Ideas.name -> Ideas
                Other.name -> Other
                else -> Other // Default fallback
            }
        }

        /**
         * Get category from display name
         */
        fun fromDisplayName(displayName: String): NoteCategory? {
            return getAllCategories().find {
                it.displayName.equals(displayName, ignoreCase = true)
            }
        }
    }

    /**
     * Override equals to compare by name
     * Important for StateFlow comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NoteCategory) return false
        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}