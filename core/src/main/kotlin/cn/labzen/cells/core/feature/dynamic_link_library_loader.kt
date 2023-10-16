package cn.labzen.cells.core.feature

import cn.labzen.cells.core.exception.DynamicLinkLibraryLoadException
import cn.labzen.cells.core.kotlin.runIf
import cn.labzen.cells.core.kotlin.throwRuntimeIf
import cn.labzen.cells.core.utils.Strings
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Paths
import java.util.function.Consumer

private const val EXTENSION_OF_FILE_DLL = ".dll"
private const val EXTENSION_OF_FILE_SO = ".so"

class DynamicLinkLibraryLoader private constructor(private val libraries: List<File>) {

  private val logger = LoggerFactory.getLogger(DynamicLinkLibraryLoader::class.java)

  private var loaded = false
  private val report = DynamicLinkLibraryLoadReport()
  private var callback: Consumer<DynamicLinkLibraryLoadReport> = defaultCallback

  fun whenLoaded(consumer: Consumer<DynamicLinkLibraryLoadReport>) =
    this.apply { callback = consumer }

  fun load() {
    loaded.throwRuntimeIf { DynamicLinkLibraryLoadException("憋反复加载，有意思？") }
    logger.info("Starting load library files into JVM")

    var pending = libraries
    var times = 0
    while (pending.isNotEmpty() && times < MAX_RETRY_TIMES) {
      pending.forEach(::internalLoad)
      pending = report.failureFiles()
      times++
    }

    loaded = true
    callback.accept(report)
    logger.info("Library files loaded successfully")
  }

  private fun internalLoad(file: File) {
    val files = mutableListOf<File>()
    foundFiles(file, files)

    files.forEach(::internalLoadIn)
  }

  private fun internalLoadIn(file: File) =
    try {
      System.load(file.path)
      report.loaded(file)
      // true
    } catch (t: Throwable) {
      report.failure(file, t)
      // false
    }

  private fun foundFiles(file: File, files: MutableList<File>) {
    if (file.isDirectory) {
      val subFiles: Array<File>? = file.listFiles()
      subFiles?.forEach { foundFiles(it, files) }
    } else {
      Strings.endsWith(file.name, false, EXTENSION_OF_FILE_DLL, EXTENSION_OF_FILE_SO).runIf {
        files.add(file)
      }
    }
  }

  companion object {
    private const val MAX_RETRY_TIMES = 5

    private val logger = LoggerFactory.getLogger(DynamicLinkLibraryLoader::class.java)

    private val projectRootPath by lazy {
      val systemResource = ClassLoader.getSystemResource("")
      if (systemResource != null) {
        systemResource.path
      } else {
        val resource = Thread.currentThread().contextClassLoader.getResource("")
        if (resource != null) {
          resource.path
        } else {
          ""
        }
      }
    }

    private val defaultCallback = Consumer<DynamicLinkLibraryLoadReport> { report ->
      logger.info("Libraries load statistics reporting ...")
      logger.info("A total of {} library files were processed", report.totalNumber())
      val loadedFilePaths = report.loadedFilePaths()
      logger.info("Loaded {} library files successfully", loadedFilePaths.size)
      loadedFilePaths.forEach { logger.info("√√√ $it loaded") }
      val failureFilePaths = report.failureFilePaths()
      logger.info("And {} library files can not load into jvm", failureFilePaths.size)
      failureFilePaths.forEach { logger.info("××× ${it.key} failure. cause: ${it.value}") }
    }

    @JvmStatic
    fun from(vararg files: String): DynamicLinkLibraryLoader {
      val libraryRoots = files.mapNotNull { toDirectory(it) }
      return DynamicLinkLibraryLoader(libraryRoots)
    }

    private fun toDirectory(path: String): File? {
      val file = path.takeIf { it.startsWith("./") }?.let {
        Paths.get(projectRootPath, it).toFile()
      } ?: File(path)

      return if (file.exists()) {
        file
      } else {
        logger.warn("动态链接库路径找不到：{}", file)
        null
      }
    }
  }
}

class DynamicLinkLibraryLoadReport {

  private val files = mutableMapOf<String, DynamicLinkLibraryLoadStatus>()

  internal fun loaded(file: File) {
    files.getOrPut(file.absolutePath) {
      DynamicLinkLibraryLoadStatus(file)
    }.apply {
      loaded = true
      failedCause = ""
    }
  }

  internal fun failure(file: File, t: Throwable) {
    files.getOrPut(file.absolutePath) {
      DynamicLinkLibraryLoadStatus(file)
    }.apply {
      loaded = false
      failedCause = t.message ?: "unknown cause"
    }
  }

  fun totalNumber() =
    files.size

  fun loadedFilePaths(): List<String> =
    files.filter { it.value.loaded }.map { it.key }

  fun failureFilePaths(): Map<String, String> =
    files.filter { !it.value.loaded }.mapValues { it.value.failedCause }

  internal fun failureFiles(): List<File> =
    files.filter { !it.value.loaded }.values.map { it.file }

}

data class DynamicLinkLibraryLoadStatus(val file: File) {

  var loaded: Boolean = false
  var failedCause: String = ""
}
