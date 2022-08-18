package com.cioffi.soslight.helpers

class CharToMorse {


    //convert Char
    companion object {
        fun convertCharToMors(c: Char): MutableList<Char> {
            var morsCode: MutableList<Char> = arrayListOf()
            val morseArr = convertCharToMorse(c)
            morsCode = morseArr?.toMutableList()!!

            return morsCode
        }

        private fun convertCharToMorse(c: Char) = when (c.lowercaseChar()) {
            'a' -> ".-"
            'b' -> "-..."
            'c' -> "-.-."
            'd' -> "-.."
            'e' -> "."
            'f' -> "..-."
            'g' -> "--."
            'h' -> "...."
            'i' -> ".."
            'j' -> ".---"
            'k' -> "-.-"
            'l' -> ".-.."
            'm' -> "--"
            'n' -> "-."
            'o' -> "---"
            'p' -> ".--."
            'q' -> "--.-"
            'r' -> ".-."
            's' -> "..."
            't' -> "-"
            'u' -> "..-"
            'v' -> "...-"
            'w' -> ".--"
            'x' -> "-..-"
            'y' -> "-.--"
            'z' -> "--.."
            '1' -> ".----"
            '2' -> "..---"
            '3' -> "...--"
            '4' -> "....-"
            '5' -> "....."
            '6' -> "-...."
            '7' -> "--..."
            '8' -> "---.."
            '9' -> "----."
            '0' -> "-----"
            ' ' -> "/"
            else -> throw IllegalArgumentException("Not all characters are valid")
        }
    }


}