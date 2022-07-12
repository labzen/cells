package cn.labzen.cells.network.http.meta

enum class Header(val value: String) {

  /**
   * 能够接受的回应内容类型（Content-Types）。参见内容协商。
   */
  ACCEPT("Accept"),

  /**
   * 能够接受的字符集
   */
  ACCEPT_CHARSET("Accept-Charset"),

  /**
   * 能够接受的编码方式列表。参考HTTP压缩。
   */
  ACCEPT_ENCODING("Accept-Encoding"),

  /**
   * 能够接受的回应内容的自然语言列表。参考 内容协商 。
   */
  ACCEPT_LANGUAGE("Accept-Language"),

  /**
   * 能够接受的按照时间来表示的版本
   */
  ACCEPT_DATETIME("Accept-Datetime"),

  /**
   * 用于超文本传输协议的认证的认证信息
   */
  AUTHORIZATION("Authorization"),

  /**
   * 请求字段： 用来指定在这次的请求/响应链中的所有缓存机制 都必须 遵守的指令
   *
   * 回应字段： 向从服务器直到客户端在内的所有缓存机制告知，它们是否可以缓存这个对象。其单位为秒
   */
  CACHE_CONTROL("Cache-Control"),

  /**
   * 请求字段： 该浏览器想要优先使用的连接类型
   *
   * 回应字段： 针对该连接所预期的选项
   */
  CONNECTION("Connection"),

  /**
   * 之前由服务器通过 Set- Cookie （下文详述）发送的一个 超文本传输协议Cookie
   */
  COOKIE("Cookie"),

  /**
   * 以 八位字节数组 （8位的字节）表示的请求体的长度
   */
  CONTENT_LENGTH("Content-Length"),

  /**
   * 请求体的内容的二进制 MD5 散列值，以 Base64 编码的结果
   */
  CONTENT_MD5("Content-MD5"),

  /**
   * MIME类型 （用于POST和PUT请求中）
   */
  CONTENT_TYPE("Content-Type"),

  /**
   * 请求字段： 发送该消息的日期和时间(按照 RFC 7231 中定义的"超文本传输协议日期"格式来发送)
   *
   * 回应字段： 此条消息被发送时的日期和时间(按照 RFC 7231 中定义的“超文本传输协议日期”格式来表示)
   */
  DATE("Date"),

  /**
   * 表明客户端要求服务器做出特定的行为
   */
  EXPECT("Expect"),

  /**
   * 发起此请求的用户的邮件地址
   */
  FROM("From"),

  /**
   * 服务器的域名(用于虚拟主机 )，以及服务器所监听的传输控制协议端口号。如果所请求的端口是对应的服务的标准端口，则端口号可被省略。
   */
  HOST("Host"),

  /**
   * 仅当客户端提供的实体与服务器上对应的实体相匹配时，才进行对应的操作。主要作用时，用作像 PUT 这样的方法中，仅当从用户上次更新某个资源以来，该资源未被修改的情况下，才更新该资源。
   */
  IF_MATCH("If-Match"),

  /**
   * 允许在对应的内容未被修改的情况下返回304未修改（ 304 Not Modified ）
   */
  IF_MODIFIED_SINCE("If-Modified-Since"),

  /**
   * 允许在对应的内容未被修改的情况下返回304未修改（ 304 Not Modified ），参考 超文本传输协议 的实体标记
   */
  IF_NONE_MATCH("If-None-Match"),

  /**
   * 如果该实体未被修改过，则向我发送我所缺少的那一个或多个部分；否则，发送整个新的实体
   */
  IF_RANGE("If-Range"),

  /**
   * 仅当该实体自某个特定时间已来未被修改的情况下，才发送回应。
   */
  IF_UNMODIFIED_SINCE("If-Unmodified-Since"),

  /**
   * 限制该消息可被代理及网关转发的次数。
   */
  MAX_FORWARDS("Max-Forwards"),

  /**
   * 发起一个针对 跨来源资源共享 的请求（要求服务器在回应中加入一个‘访问控制-允许来源’（'Access-Control-Allow-Origin'）字段）。
   */
  ORIGIN("Origin"),

  /**
   * 与具体的实现相关，这些字段可能在请求/回应链中的任何时候产生多种效果。
   */
  PRAGMA("Pragma"),

  /**
   * 用来向代理进行认证的认证信息。
   */
  PROXY_AUTHORIZATION("Proxy-Authorization"),

  /**
   * 仅请求某个实体的一部分。字节偏移以0开始。参见字节服务。
   */
  RANGE("Range"),

  /**
   * 表示浏览器所访问的前一个页面，正是那个页面上的某个链接将浏览器带到了当前所请求的这个页面。
   */
  REFERER("Referer"),

  /**
   * 浏览器预期接受的传输编码方式：可使用回应协议头 Transfer-Encoding 字段中的值；另外还可用"trailers"（与"分块 "传输方式相关）这个值来表明浏览器希望在最后一个尺寸为0的块之后还接收到一些额外的字段。
   */
  TE("TE"),

  /**
   * 浏览器的浏览器身份标识字符串
   */
  USER_AGENT("User-Agent"),

  /**
   * 请求字段： 要求服务器升级到另一个协议。
   *
   * 回应字段： 要求客户端升级到另一个协议。
   */
  UPGRADE("Upgrade"),

  /**
   * 请求字段： 向服务器告知，这个请求是由哪些代理发出的。
   *
   * 回应字段： 告知代理服务器的客户端，当前回应是通过什么途径发送的。
   */
  VIA("Via"),

  /**
   * 一般性的警告，告知在实体内容体中可能存在错误。
   */
  WARNING("Warning"),

  // ----

  /**
   * 主要用于标识 Ajax 及可扩展标记语言 请求。大部分的JavaScript框架会发送这个字段，且将其值设置为 XMLHttpRequest
   */
  X_REQUESTED_WITH("X-Requested-With"),

  /**
   * 请求某个网页应用程序停止跟踪某个用户。在火狐浏览器中，相当于X-Do-Not-Track协议头字段（自 Firefox/4.0 Beta 11 版开始支持）。Safari 和 Internet Explorer 9 也支持这个字段。2011年3月7日，草案提交IETF。 万维网协会 的跟踪保护工作组正在就此制作一项规范。
   */
  DNT("DNT"),

  /**
   * 一个事实标准 ，用于标识某个通过超文本传输协议代理或负载均衡连接到某个网页服务器的客户端的原始互联网地址
   */
  X_FORWARDED_FOR("X-Forwarded-For"),

  /**
   * 一个事实标准 ，用于识别客户端原本发出的 Host 请求头部。
   */
  X_FORWARDED_HOST("X-Forwarded-Host"),

  /**
   * 一个事实标准，用于标识某个超文本传输协议请求最初所使用的协议。
   */
  X_FORWARDED_PROTO("X-Forwarded-Proto"),

  /**
   * 被微软的服务器和负载均衡器所使用的非标准头部字段。
   */
  FRONT_END_HTTPS("Front-End-Https"),

  /**
   * 请求某个网页应用程序使用该协议头字段中指定的方法（一般是PUT或DELETE）来覆盖掉在请求中所指定的方法（一般是POST）。当某个浏览器或防火墙阻止直接发送PUT 或DELETE 方法时（注意，这可能是因为软件中的某个漏洞，因而需要修复，也可能是因为某个配置选项就是如此要求的，因而不应当设法绕过），可使用这种方式。
   */
  X_HTTP_METHOD_OVERRIDE("X-Http-Method-Override"),

  /**
   * 使服务器更容易解读AT&T设备User-Agent字段中常见的设备型号、固件信息。
   */
  X_ATT_DEVICEID("X-ATT-DeviceId"),

  /**
   * 链接到互联网上的一个XML文件，其完整、仔细地描述了正在连接的设备。右侧以为AT&T Samsung Galaxy S2提供的XML文件为例。
   */
  X_WAP_PROFILE("X-Wap-Profile"),

  /**
   * 该字段源于早期超文本传输协议版本实现中的错误。与标准的连接（Connection）字段的功能完全相同。
   */
  PROXY_CONNECTION("Proxy-Connection"),

  /**
   * 用于防止 跨站请求伪造。 辅助用的头部有 X-CSRFToken 或 X-XSRF-TOKEN
   */
  X_CSRF_TOKEN("X-Csrf-Token"),

  // -----

  /**
   * 指定哪些网站可参与到跨来源资源共享过程中
   */
  ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),

  ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method"),

  /**
   * 指定服务器支持的文件格式类型。
   */
  ACCEPT_PATCH("Accept-Patch"),

  /**
   * 这个服务器支持哪些种类的部分内容范围
   */
  ACCEPT_RANGES("Accept-Ranges"),

  /**
   * 这个对象在代理缓存中存在的时间，以秒为单位
   */
  AGE("Age"),

  /**
   * 对于特定资源有效的动作。针对HTTP/405这一错误代码而使用
   */
  ALLOW("Allow"),

  /**
   * 一个可以让客户端下载文件并建议文件名的头部。文件名需要用双引号包裹。
   */
  CONTENT_DISPOSITION("Content-Disposition"),

  /**
   * 在数据上使用的编码类型。参考 超文本传输协议压缩 。
   */
  CONTENT_ENCODING("Content-Encoding"),

  /**
   * 内容所使用的语言
   */
  CONTENT_LANGUAGE("Content-Language"),

  /**
   * 所返回的数据的一个候选位置
   */
  CONTENT_LOCATION("Content-Location"),

  /**
   * 这条部分消息是属于某条完整消息的哪个部分
   */
  CONTENT_RANGE("Content-Range"),

  /**
   * 对于某个资源的某个特定版本的一个标识符，通常是一个 消息散列
   */
  ETAG("ETag"),

  /**
   * 指定一个日期/时间，超过该时间则认为此回应已经过期
   */
  EXPIRES("Expires"),

  /**
   * 所请求的对象的最后修改日期(按照 RFC 7231 中定义的“超文本传输协议日期”格式来表示)
   */
  LAST_MODIFIED("Last-Modified"),

  /**
   * 用来表达与另一个资源之间的类型关系，此处所说的类型关系是在 RFC 5988 中定义的
   */
  LINK("Link"),

  /**
   * 用来 进行重定向，或者在创建了某个新资源时使用。
   */
  LOCATION("Location"),

  /**
   * 用于支持设置P3P策略，标准格式为“P3P:CP="your_compact_policy"”。然而P3P规范并不成功，大部分现代浏览器没有完整实现该功能，而大量网站也将该值设为假值，从而足以用来欺骗浏览器的P3P插件功能并授权给第三方Cookies。
   */
  P3P("P3P"),

  /**
   * 要求在访问代理时提供身份认证信息。
   */
  PROXY_AUTHENTICATE("Proxy-Authenticate"),

  /**
   * 用于缓解中间人攻击，声明网站认证使用的传输层安全协议证书的散列值
   */
  PUBLIC_KEY_PINS("Public-Key-Pins"),

  /**
   * 用于设定可定时的重定向跳转。右边例子设定了5秒后跳转至“http://www.w3.org/pub/WWW/People.html”。
   */
  REFRESH("Refresh"),

  /**
   * 如果某个实体临时不可用，则，此协议头用来告知客户端日后重试。其值可以是一个特定的时间段(以秒为单位)或一个超文本传输协议日期。
   */
  RETRY_AFTER("Retry-After"),

  /**
   * 服务器的名字
   */
  SERVER("Server"),

  /**
   * HTTP cookie
   */
  SET_COOKIE("Set-Cookie"),

  /**
   * 通用网关接口 协议头字段，用来说明当前这个超文本传输协议回应的 状态 。普通的超文本传输协议回应，会使用单独的“状态行”（"Status-Line"）作为替代，这一点是在 RFC 7230 中定义的。
   */
  STATUS("Status"),

  /**
   * HTTP 严格传输安全这一头部告知客户端缓存这一强制 HTTPS 策略的时间，以及这一策略是否适用于其子域名。
   */
  STRICT_TRANSPORT_SECURITY("Strict-Transport-Security"),

  /**
   * 这个头部数值指示了在这一系列头部信息由由分块传输编码编码。
   */
  TRAILER("Trailer"),

  /**
   * 用来将实体安全地传输给用户的编码形式。当前定义的方法包括：分块（chunked）、compress、deflate、gzip和identity。
   */
  TRANSFER_ENCODING("Transfer-Encoding"),

  /**
   * 告知下游的代理服务器，应当如何对未来的请求协议头进行匹配，以决定是否可使用已缓存的回应内容而不是重新从原始服务器请求新的内容。
   */
  VARY("Vary"),

  /**
   * 表明在请求获取这个实体时应当使用的认证模式。
   */
  WWW_AUTHENTICATE("WWW-Authenticate"),

  /**
   * "点击劫持保护：
   * - deny：该页面不允许在 frame 中展示，即使是同域名内。
   * - sameorigin：该页面允许同域名内在 frame 中展示。
   * - allow-from uri：该页面允许在指定uri的 frame 中展示。
   * - allowall：允许任意位置的frame显示，非标准值。
   */
  X_FRAME_OPTIONS("X-Frame-Options"),

  // -----

  /**
   * 跨站脚本攻击 （XSS）过滤器
   */
  X_XSS_PROTECTION("X-XSS-Protection"),

  /**
   * 内容安全策略定义。
   */
  CONTENT_SECURITY_POLICY("Content-Security-Policy"),

  /**
   * 内容安全策略定义。
   */
  X_CONTENT_SECURITY_POLICY("X-Content-Security-Policy"),

  /**
   * 内容安全策略定义。
   */
  X_WEBKIT_CSP("X-WebKit-CSP"),

  /**
   * 唯一允许的数值为"nosniff"，防止 Internet Explorer 对文件进行MIME类型嗅探。这也对 Google Chrome 下载扩展时适用。
   */
  X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options"),

  /**
   * 表明用于支持当前网页应用程序的技术（例如：PHP）（版本号细节通常放置在 X-Runtime 或 X-Version 中）
   */
  X_POWERED_BY("X-Powered-By"),

  /**
   * 推荐指定的渲染引擎（通常是向后兼容模式）来显示内容。也用于激活 Internet Explorer 中的 Chrome Frame。
   */
  X_UA_COMPATIBLE("X-UA-Compatible"),

  /**
   * 指出音视频的长度，单位为秒。只受Gecko内核浏览器支持。
   */
  X_CONTENT_DURATION("X-Content-Duration"),

  /**
   * 管控特定应用程序接口
   */
  FEATURE_POLICY("Feature-Policy"),

  /**
   * 管控特定应用程序接口为W3C标准 替代Feature-Policy
   */
  PERMISSIONS_POLICY("Permissions-Policy"),

  /**
   * Flash的跨网站攻击防御
   */
  X_PERMITTED_CROSS_DOMAIN_POLICIES("X-Permitted-Cross-Domain-Policies"),

  /**
   * 保护信息泄漏
   */
  REFERRER_POLICY("Referrer-Policy"),

  /**
   * 防止欺骗 SSL，单位为秒
   */
  EXPECT_CT("Expect-CT"),

}
