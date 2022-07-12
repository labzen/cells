package cn.labzen.cells.network.http.server

interface Server {

  fun init(configuration: ServerConfiguration)

  @Throws(IllegalStateException::class)
  fun start()

  @Throws(IllegalStateException::class)
  fun stop()

  fun isNotRunning(): Boolean
}
