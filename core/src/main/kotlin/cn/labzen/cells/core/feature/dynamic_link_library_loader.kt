package cn.labzen.cells.core.feature

import cn.labzen.cells.core.exception.DynamicLinkLibraryLoadException
import cn.labzen.cells.core.kotlin.runIf
import cn.labzen.cells.core.kotlin.throwRuntimeIf
import cn.labzen.cells.core.utils.Strings
import cn.labzen.logger.kernel.enums.Status
import cn.labzen.logger.kotlin.logger
import java.io.File
import java.nio.file.Paths
import java.util.function.Consumer

private const val EXTENSION_OF_FILE_DLL = ".dll"
private const val EXTENSION_OF_FILE_SO = ".so"

class DynamicLinkLibraryLoader private constructor(private val libraries: List<File>) {

  private var loaded = false
  private val report = DynamicLinkLibraryLoadReport()
  private var callback: Consumer<DynamicLinkLibraryLoadReport> = Consumer<DynamicLinkLibraryLoadReport> { report ->
    val loadedFilePaths = report.loadedFilePaths()
    val failureFilePaths = report.failureFilePaths()
    logger.info().scene(LOG_SCENE).status(Status.DONE)
      .log(
        "加载信息统计：共处理了 {} 个动态链接库文件 [成功加载: {}; 加载失败: {}].",
        report.totalNumber(),
        loadedFilePaths.size,
        failureFilePaths.size
      )

    loadedFilePaths.forEach {
      logger.info().scene(LOG_SCENE).status(Status.SUCCESS).log("loaded file: $it")
    }

    failureFilePaths.forEach {
      logger.warn().scene(LOG_SCENE).status(Status.WRONG).log("failed file: ${it.key}, cause ${it.value}")
    }
  }

  fun whenLoaded(consumer: Consumer<DynamicLinkLibraryLoadReport>) =
    this.apply { callback = consumer }

  fun load() {
    loaded.throwRuntimeIf { DynamicLinkLibraryLoadException("憋反复加载，有意思？") }
    logger.info().scene(LOG_SCENE).status(Status.STARTING).log("开始加载动态链接库文件..")

    var pending = libraries
    var times = 0
    while (pending.isNotEmpty() && times < MAX_RETRY_TIMES) {
      pending.forEach(::internalLoad)
      pending = report.failureFiles()
      times++
    }

    loaded = true
    callback.accept(report)
    logger.info().scene(LOG_SCENE).status(Status.COMPLETED).log("动态链接库文件加载成功.")
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
    private const val LOG_SCENE = "Library"

    private val logger = logger { }

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
        logger.warn().scene(LOG_SCENE).log("动态链接库路径找不到：{}", file)
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
