package cn.labzen.cells.network.http.server.undertow

import io.undertow.server.handlers.resource.ClassPathResourceManager
import io.undertow.server.handlers.resource.Resource
import io.undertow.server.handlers.resource.ResourceChangeListener
import io.undertow.server.handlers.resource.ResourceManager

class CompositeResourceManager : ResourceManager {

  internal val managers = mutableListOf<ResourceManager>()

  override fun close() {
    managers.forEach(ResourceManager::close)
    managers.clear()
  }

  override fun getResource(path: String?): Resource? {
    path ?: return null
    if (path == "/") {
      return null
    }

    return try {
      managers.firstNotNullOf { it.getResource(path) }
    } catch (e: NoSuchElementException) {
      null
    }
  }

  override fun isResourceChangeListenerSupported(): Boolean =
    managers.any { it.isResourceChangeListenerSupported }

  override fun registerResourceChangeListener(listener: ResourceChangeListener) {
    managers.forEach {
      if (it.isResourceChangeListenerSupported) {
        it.registerResourceChangeListener(listener)
      }
    }
  }

  override fun removeResourceChangeListener(listener: ResourceChangeListener) {
    managers.forEach {
      if (it.isResourceChangeListenerSupported) {
        it.removeResourceChangeListener(listener)
      }
    }
  }
}
