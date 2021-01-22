import io.netty.buffer.{ByteBuf, Unpooled}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 15:47 2021/1/20
 * @Modified By:
 */
class MystiqueSerializer[T](wrapFunc: List[Any] => T, deconstructFunc: T => List[Any]) {

  def serialize(obj: T): Array[Byte] = {
    val byteBuf: ByteBuf = Unpooled.buffer()
    val argList: List[Any] = deconstructFunc(obj)
    BaseSerializerWithTypeId.write(byteBuf, argList)
//    argList.foreach(arg => BaseSerializerWithTypeId.write(byteBuf, arg))
    val bytes = BaseSerializerWithTypeId.export(byteBuf)
    byteBuf.release()
    bytes
  }

  def deserialize(bytes: Array[Byte]): T = {
    val byteBuf = Unpooled.wrappedBuffer(bytes)
    val argList: List[Any] = BaseSerializerWithTypeId.readValue(byteBuf).asInstanceOf[List[Any]]
    wrapFunc(argList)
  }
}