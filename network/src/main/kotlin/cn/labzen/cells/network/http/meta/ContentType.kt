package cn.labzen.cells.network.http.meta

enum class ContentType(val value: String) {

  ALL("*/*"),

  /**
   *  标准表单编码，当action为get时候，浏览器用x-www-form-urlencoded的编码方式把form数据转换成一个字串（name1=value1&name2=value2…）
   */
  FORM_URLENCODED("application/x-www-form-urlencoded"),

  /**
   *  文件上传编码，浏览器会把整个表单以控件为单位分割，并为每个部分加上Content-Disposition，并加上分割符(boundary)
   */
  MULTIPART("multipart/form-data"),

  /**
   * 任意类型的二进制流数据
   */
  STREAM("application/octet-stream"),

  /**
   *  Rest请求JSON编码
   */
  JSON("application/json"),
  JSON_UTF8("application/json;charset=UTF-8"),

  /**
   *  Rest请求XML编码
   */
  XML("application/xml"),
  XML_UTF8("application/xml;charset=UTF-8"),

  /**
   *  Rest请求text/xml编码
   */
  TEXT_XML("text/xml"),
  TEXT_XML_UTF8("text/xml;charset=UTF-8"),

  TXT_HTML("text/html"),
  TXT_HTML_UTF8("text/html;charset=UTF-8"),

  XHTML("application/xhtml+xml"),
  XHTML_UTF8("application/xhtml+xml;charset=UTF-8")
}
