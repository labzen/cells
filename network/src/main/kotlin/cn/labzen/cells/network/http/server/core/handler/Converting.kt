package cn.labzen.cells.network.http.server.core.handler

import org.springframework.core.convert.converter.Converter

interface Converting<S, T> : Converter<S, T> {

}
