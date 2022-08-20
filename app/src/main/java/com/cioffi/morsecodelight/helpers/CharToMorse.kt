package com.cioffi.morsecodelight.helpers

class CharToMorse {


    //convert Char
    companion object {

        private  object MapCodeMors {
            val map = mapOf<Char, String>(
                'a' to ".-",
                'b' to "-...",
                'c' to "-.-.",
                'd' to "-..",
                'e' to ".",
                'f' to "..-.",
                'g' to "--.",
                'h' to "....",
                'i' to "..",
                'j' to ".---",
                'k' to "-.-",
                'l' to ".-..",
                'm' to "--",
                'n' to "-.",
                'o' to "---",
                'p' to ".--.",
                'q' to "--.-",
                'r' to ".-.",
                's' to "...",
                't' to "-",
                'u' to "..-",
                'v' to "...-",
                'w' to ".--",
                'x' to "-..-",
                'y' to "-.--",
                'z' to "--..",
                '1' to ".----",
                '2' to "..---",
                '3' to "...--",
                '4' to "....-",
                '5' to ".....",
                '6' to "-....",
                '7' to "--...",
                '8' to "---..",
                '9' to "----.",
                '0' to "-----",
                ' ' to "/"
            )
        }


        fun getMapMorsCode(): Map<Char, String> {
            return MapCodeMors.map
        }

        fun convertCharToMors(c: Char): MutableList<Char> {
            var morsCode: MutableList<Char> = arrayListOf()
            var morseArr = ""
            val mapCodeMors = MapCodeMors.map
            if(mapCodeMors.containsKey(c.lowercaseChar())){
                morseArr = mapCodeMors.get(c.lowercaseChar()).toString()
            }else{
                throw IllegalArgumentException("Not all characters are valid")
            }
            morsCode = morseArr?.toMutableList()!!
            return morsCode
        }

    }


}