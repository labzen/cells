package cn.labzen.cells.core.meta

import cn.labzen.meta.component.LabzenComponent

class CellsCoreMeta : LabzenComponent {

  override fun mark(): String =
    "Labzen-Cells.Core"

  override fun packageBased(): String =
    "cn.labzen.cells.core"

  override fun description(): String =
    "核心包，定义最基本的包依赖集合，并提供普适基础工具与底层功能"
}
