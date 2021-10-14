package me.kaliber.combatstats.extensions

import java.io.File

fun File.createDirs(): File
{
    if (exists())
    {
        return this
    }

    return apply(File::mkdirs)
}

fun File.createFile(): File
{
    if (exists())
    {
        return this
    }

    return apply()
    {
        parentFile.mkdirs()
        createNewFile()
    }
}
