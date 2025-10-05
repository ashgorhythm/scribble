package com.ashgorhythm.scribble.domain

sealed class Category(val name: String) {
    object Personal : Category("Personal")
    object Work : Category("Work")
    object Study : Category("Study")
    object Other : Category("Other")
    companion object {
        val allCategories = listOf(Personal, Work, Study, Other)
    }
}
