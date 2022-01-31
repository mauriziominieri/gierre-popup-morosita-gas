package com.kotlin.gierreprojectwebservice.deprecated

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.util.logging.Logger
import java.util.stream.Stream


class FileStorageHelper {

    companion object {
        private val root = Path.of("src//main//resources//static//uploads")
        @JvmStatic private val logger = Logger.getLogger(FileStorageHelper::class.java.name)

    }

    fun init() {
        try {
            Files.createDirectory(root)
        } catch (e: IOException) {
            throw RuntimeException("Could not initialize folder for upload!")
        }
    }

    fun save(file: MultipartFile, name: String) {
        try {
            Files.copy(file.inputStream, root.resolve(name))
        } catch (e: Exception) {
            logger.severe(e.printStackTrace().toString())
            throw RuntimeException("Could not store the file. Error: " + e.message)
        }
    }


    fun load(filename: String): Resource? {
        return try {
            val file = root.resolve(filename)
            val resource: Resource = UrlResource(file.toUri())

            if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw RuntimeException("Could not read the file!")
            }
        } catch (e: MalformedURLException) {
            throw RuntimeException("Error: " + e.message)
        }
    }



    fun delete(filename: String): Boolean {
        val file = root.resolve(filename)
        val resource = File(file.toUri())
        return resource.delete()
    }



    fun deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile())
    }



    fun loadAll(): Stream<Path>? {
        return try {
            Files.walk(root, 1).filter { path: Path -> path != root }.map { path: Path? ->
                root.relativize(
                    path
                )
            }
        } catch (e: IOException) {
            throw RuntimeException("Could not load the files!")
        }
    }
}