@file:Suppress("DEPRECATION")

package cn.labzen.cells.network.http.server.bean

import java.util.*
import javax.servlet.ServletContext
import javax.servlet.http.HttpSession
import javax.servlet.http.HttpSessionContext

class LynxHttpSession : HttpSession {

  override fun getCreationTime(): Long {
    TODO("Not yet implemented")
  }

  override fun getId(): String {
    TODO("Not yet implemented")
  }

  override fun getLastAccessedTime(): Long {
    TODO("Not yet implemented")
  }

  override fun getServletContext(): ServletContext {
    TODO("Not yet implemented")
  }

  override fun setMaxInactiveInterval(interval: Int) {
    TODO("Not yet implemented")
  }

  override fun getMaxInactiveInterval(): Int {
    TODO("Not yet implemented")
  }

  override fun getSessionContext(): HttpSessionContext {
    TODO("Not yet implemented")
  }

  override fun getAttribute(name: String?): Any {
    TODO("Not yet implemented")
  }

  override fun getValue(name: String?): Any {
    TODO("Not yet implemented")
  }

  override fun getAttributeNames(): Enumeration<String> {
    TODO("Not yet implemented")
  }

  override fun getValueNames(): Array<String> {
    TODO("Not yet implemented")
  }

  override fun setAttribute(name: String?, value: Any?) {
    TODO("Not yet implemented")
  }

  override fun putValue(name: String?, value: Any?) {
    TODO("Not yet implemented")
  }

  override fun removeAttribute(name: String?) {
    TODO("Not yet implemented")
  }

  override fun removeValue(name: String?) {
    TODO("Not yet implemented")
  }

  override fun invalidate() {
    TODO("Not yet implemented")
  }

  override fun isNew(): Boolean {
    TODO("Not yet implemented")
  }
}
