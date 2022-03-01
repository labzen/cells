package cn.labzen.cells.network.http.meta

enum class Status(val value: Int) {

  /**
   * HTTP Status-Code 100: Continue.
   */
  HTTP_CONTINUE(100),

  /**
   * HTTP Status-Code 101: Switching Protocols.
   */
  HTTP_SWITCHING_PROTOCOLS(101),

  /**
   * HTTP Status-Code 102: Processing.
   */
  HTTP_PROCESSING(102),

  /**
   * HTTP Status-Code 200: OK.
   */
  HTTP_OK(200),

  /**
   * HTTP Status-Code 201: Created.
   */
  HTTP_CREATED(201),

  /**
   * HTTP Status-Code 202: Accepted.
   */
  HTTP_ACCEPTED(202),

  /**
   * HTTP Status-Code 203: Non-Authoritative Information.
   */
  HTTP_NOT_AUTHORITATIVE(203),

  /**
   * HTTP Status-Code 204: No Content.
   */
  HTTP_NO_CONTENT(204),

  /**
   * HTTP Status-Code 205: Reset Content.
   */
  HTTP_RESET(205),

  /**
   * HTTP Status-Code 206: Partial Content.
   */
  HTTP_PARTIAL(206),

  /**
   * HTTP Status-Code 207: Multi-Status.
   */
  HTTP_MULTI_STATUS(207),

  /**
   * HTTP Status-Code 208: Already Reported.
   */
  HTTP_ALREADY_REPORTED(208),

  /**
   * HTTP Status-Code 226: IM Used.
   */
  HTTP_IM_USED(226),

  /**
   * HTTP Status-Code 300: Multiple Choices.
   */
  HTTP_MULTI_CHOICE(300),

  /**
   * HTTP Status-Code 301: Moved Permanently.
   */
  HTTP_MOVED_PERM(301),

  /**
   * HTTP Status-Code 302: Temporary Redirect.
   */
  HTTP_MOVED_TEMP(302),

  /**
   * HTTP Status-Code 303: See Other.
   */
  HTTP_SEE_OTHER(303),

  /**
   * HTTP Status-Code 304: Not Modified.
   */
  HTTP_NOT_MODIFIED(304),

  /**
   * HTTP Status-Code 305: Use Proxy.
   */
  HTTP_USE_PROXY(305),

  /**
   * HTTP Status-Code 306: Switch Proxy.
   */
  HTTP_SWITCH_PROXY(306),

  /**
   * HTTP Status-Code 307: Temporary Redirect.
   */
  HTTP_TEMPORARY_REDIRECT(307),

  /**
   * HTTP Status-Code 308: Permanent Redirect.
   */
  HTTP_PERMANENT_REDIRECT(308),

  /**
   * HTTP Status-Code 400: Bad Request.
   */
  HTTP_BAD_REQUEST(400),

  /**
   * HTTP Status-Code 401: Unauthorized.
   */
  HTTP_UNAUTHORIZED(401),

  /**
   * HTTP Status-Code 402: Payment Required.
   */
  HTTP_PAYMENT_REQUIRED(402),

  /**
   * HTTP Status-Code 403: Forbidden.
   */
  HTTP_FORBIDDEN(403),

  /**
   * HTTP Status-Code 404: Not Found.
   */
  HTTP_NOT_FOUND(404),

  /**
   * HTTP Status-Code 405: Method Not Allowed.
   */
  HTTP_BAD_METHOD(405),

  /**
   * HTTP Status-Code 406: Not Acceptable.
   */
  HTTP_NOT_ACCEPTABLE(406),

  /**
   * HTTP Status-Code 407: Proxy Authentication Required.
   */
  HTTP_PROXY_AUTH(407),

  /**
   * HTTP Status-Code 408: Request Time-Out.
   */
  HTTP_CLIENT_TIMEOUT(408),

  /**
   * HTTP Status-Code 409: Conflict.
   */
  HTTP_CONFLICT(409),

  /**
   * HTTP Status-Code 410: Gone.
   */
  HTTP_GONE(410),

  /**
   * HTTP Status-Code 411: Length Required.
   */
  HTTP_LENGTH_REQUIRED(411),

  /**
   * HTTP Status-Code 412: Precondition Failed.
   */
  HTTP_PRECON_FAILED(412),

  /**
   * HTTP Status-Code 413: Request Entity Too Large.
   */
  HTTP_ENTITY_TOO_LARGE(413),

  /**
   * HTTP Status-Code 414: Request-URI Too Large.
   */
  HTTP_REQ_TOO_LONG(414),

  /**
   * HTTP Status-Code 415: Unsupported Media Type.
   */
  HTTP_UNSUPPORTED_TYPE(415),

  /**
   * HTTP Status-Code 416: Range Not Satisfiable
   */
  HTTP_RANGE_NOT_SATISFIABLE(416),

  /**
   * HTTP Status-Code 417: Expectation Failed
   */
  HTTP_EXPECTATION_FAILED(417),

  /**
   * HTTP Status-Code 418: Im a teapot
   */
  HTTP_IM_A_TEAPOT(418),

  /**
   * HTTP Status-Code 421: Misdirected Request
   */
  HTTP_MISDIRECTED_REQUEST(421),

  /**
   * HTTP Status-Code 422: Unprocessable Entity
   */
  HTTP_UNPROCESSABLE_ENTITY(422),

  /**
   * HTTP Status-Code 423: Locked
   */
  HTTP_LOCKED(423),

  /**
   * HTTP Status-Code 424: Failed Dependency
   */
  HTTP_FAILED_DEPENDENCY(424),

  /**
   * HTTP Status-Code 426: Upgrade Required
   */
  HTTP_UPGRADE_REQUIRED(426),

  /**
   * HTTP Status-Code 428: Precondition Required
   */
  HTTP_PRECONDITION_REQUIRED(428),

  /**
   * HTTP Status-Code 429: Too Many Requests
   */
  HTTP_TOO_MANY_REQUESTS(429),

  /**
   * HTTP Status-Code 431: Request Header Fields Too Large
   */
  HTTP_REQUEST_HEADER_FIELDS_TOO_LARGE(431),

  /**
   * HTTP Status-Code 451: Unavailable For Legal Reasons
   */
  HTTP_UNAVAILABLE_FOR_LEGAL_REASONS(451),

  /**
   * HTTP Status-Code 500: Internal Server Error.
   */
  HTTP_INTERNAL_ERROR(500),

  /**
   * HTTP Status-Code 501: Not Implemented.
   */
  HTTP_NOT_IMPLEMENTED(501),

  /**
   * HTTP Status-Code 502: Bad Gateway.
   */
  HTTP_BAD_GATEWAY(502),

  /**
   * HTTP Status-Code 503: Service Unavailable.
   */
  HTTP_UNAVAILABLE(503),

  /**
   * HTTP Status-Code 504: Gateway Timeout.
   */
  HTTP_GATEWAY_TIMEOUT(504),

  /**
   * HTTP Status-Code 505: HTTP Version Not Supported.
   */
  HTTP_VERSION(505),

  /**
   * HTTP Status-Code 506: Variant Also Negotiates
   */
  HTTP_VARIANT_ALSO_NEGOTIATES(506),

  /**
   * HTTP Status-Code 507: Insufficient Storage
   */
  HTTP_INSUFFICIENT_STORAGE(507),

  /**
   * HTTP Status-Code 508: Loop Detected
   */
  HTTP_LOOP_DETECTED(508),

  /**
   * HTTP Status-Code 510: Not Extended
   */
  HTTP_NOT_EXTENDED(510),

  /**
   * HTTP Status-Code 511: Network Authentication Required
   */
  HTTP_NETWORK_AUTHENTICATION_REQUIRED(511),
}
